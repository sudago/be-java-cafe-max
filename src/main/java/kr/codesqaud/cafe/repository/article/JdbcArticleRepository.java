package kr.codesqaud.cafe.repository.article;

import kr.codesqaud.cafe.controller.article.ArticleForm;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.util.Paging;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcArticleRepository implements ArticleRepository{

    private final JdbcTemplate jdbcTemplate;
    public JdbcArticleRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Article save(Article article) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("article").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("writer", article.getWriter());
        parameters.put("title", article.getTitle());
        parameters.put("contents", article.getContents());
        parameters.put("created_at", article.getCreatedAt());
        parameters.put("points", article.getPoints());
        parameters.put("deleted", article.getDeleted());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        article.setId(key.longValue());
        return article;
    }

    private RowMapper<Article> articleRowMapper(){
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setWriter(rs.getString("writer"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            if (rs.getTimestamp("modified_at") != null) {
                article.setModifiedAt(rs.getTimestamp("modified_at").toLocalDateTime());
            }
            article.setPoints(rs.getLong("points"));
            article.setDeleted(rs.getBoolean("deleted"));
            return article;
        };
    }

    @Override
    public Optional<Article> findById(Long id) {
        List<Article> result = jdbcTemplate.query("select * from article where id = ? and deleted = false", articleRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll(Paging paging) {
        return jdbcTemplate.query("select * from article where deleted = false order by created_at desc limit ?, ?"
                , articleRowMapper(), paging.getStart(), Paging.getCntPerPage());
    }

    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from article");
    }

    @Override
    public Optional<Article> update(Long id, ArticleForm form) {
        jdbcTemplate.update("update article set title = ?, contents = ?, modified_at = ?  where id = ?",
                form.getTitle(), form.getContents(), LocalDateTime.now(), id);
        return findById(id);
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("update article set deleted = ? where id = ?", true, id);
        jdbcTemplate.update("update reply set deleted = ? where article_id = ?", true, id);
        return id;
    }

    @Override
    public Long count(){
        return jdbcTemplate.queryForObject("select count(*) from article where deleted = false", Long.class);
    }
}
