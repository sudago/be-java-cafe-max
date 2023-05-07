package kr.codesqaud.cafe.util;

import java.util.ArrayList;
import java.util.List;

public class Paging {
    private static final int cntPerPage = 15; // 페이지 당 게시글 개수
    private static final int cntPage = 5; // 화면 당 페이지 개수
    private int nowPage; // 현재 페이지
    private int startPage; // 시작 페이지
    private int endPage; //  끝 페이지
    private int lastPage; // 마지막 페이지
    private int start; // sql 쿼리용
    private int end;

    // mustache 사용하기 위한 변수
    private int previousPage;
    private int nextPage;
    private Boolean hasPrevious; // 1 페이지가 아닐 때만 등장
    private Boolean hasNext; // 맨 마지막 페이지가 아닐 때만 등장
    private List<Integer> pageNumbers; // 현재 페이지에 접근 가능한 페이지 넘버들


    public Paging(){}
    public Paging(int nowPage, Long total){
        this.nowPage = nowPage;
        calcLastPage(total);
        calcStartEndPage(nowPage);
        calcStartEnd(nowPage);
        calcPageNumbers(startPage, endPage);
        this.previousPage = startPage-1;
        this.nextPage = endPage+1;
        this.hasPrevious = this.startPage != 1;
        this.hasNext = this.endPage != this.lastPage;
    }

    // 맨 끝 페이지 계산
    public void calcLastPage(Long total){
        lastPage = (int) Math.ceil((double) total / cntPerPage);
    }

    // 시작, 끝 페이지 계산
    public void calcStartEndPage(int nowPage) {
        endPage = (int) Math.ceil((double) nowPage / cntPage) * cntPage;
        if (lastPage < endPage) {
            endPage = lastPage;
        }

        startPage = ((nowPage-1) / cntPage) * cntPage + 1;
        if (startPage < 1) {
            startPage = 1;
        }

    }
    // DB 에서 사용 할 start 계산
    public void calcStartEnd(int nowPage) {
        end = nowPage * cntPerPage;
        start = end - cntPerPage;
    }

    private void calcPageNumbers(int startPage, int endPage){
        pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }
    }

    public int getNowPage() {
        return nowPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getStart() {
        return start;
    }

    public int getEnd(){
        return end;
    }

    public static int getCntPerPage(){
        return cntPerPage;
    }


    public int getPreviousPage() {
        return previousPage;
    }


    public int getNextPage() {
        return nextPage;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "nowPage=" + nowPage +
                ", startPage=" + startPage +
                ", endPage=" + endPage +
                ", lastPage=" + lastPage +
                ", start=" + start +
                ", end=" + end +
                ", previousPage=" + previousPage +
                ", nextPage=" + nextPage +
                ", hasPrevious=" + hasPrevious +
                ", hasNext=" + hasNext +
                ", pageNumbers=" + pageNumbers +
                '}';
    }
}
