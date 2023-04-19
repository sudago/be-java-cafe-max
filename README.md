# be-java-cafe
마스터즈 2023 스프링 카페 

배포: http://ec2-13-125-213-166.ap-northeast-2.compute.amazonaws.com:8080

# 이번주 계획

- 월: 미션5 구현
- 화: 피드백 반영 및 수정
- 수목금: 미션 6, 7 구현

## 수정 할 목록
- 게시글 역순 정렬
- 커스텀 예외 만들기
- 테스트 코드 작성
- readme에 url 정리
- 테스트 코드 수정 (Repository 자동 주입 받게)
- 로그아웃 post로 받게 수정

**추가 공부**

- 블로그 글 작성
- 오브젝트에서 맡은 단원 정리
- 알고리즘 문제 4개 풀기


# 미션 구현 목록
## URL

|       URL        | HTTP Method |    기능     |
|:----------------:|:-----------:|:---------:|
|      **\/**      |     GET     | 게시글 목록 조회 |
| **\/questions**  |     GET     | 게시글 작성 폼  |
|   \/questions    |    POST     |  게시글 작성   |
| \/articles/{id}  |     GET     | 게시글 상세 조회 |
|   **\/users**    |     GET     | 회원 목록 조회  |
|  \/users/create  |     GET     |  회원 가입 폼  |
|  \/users/create  |    POST     |   회원 가입   |
| \/users/{userId} |     GET     | 회원 프로필 조회 |
|   **\/login**    |     GET     |   로그인 폼   |
|     \/login      |    POST     |    로그인    |
|     \/logout     |     GET     |   로그아웃    |


## ERD


|     게시글     |     |           |              |          |    articles    |
|:-----------:|:---:|:---------:|:------------:|:--------:|:--------------:|
| primary key | 번호  |    id     |    bigint    | NOT NULL | auto_increment |
|             | 글쓴이 |  writer   | varchar(16)  | NOT NULL |                |
|             | 제목  |   title   | varchar(32)  | NOT NULL |                |
|             | 내용  | contents  | varchar(255) | NOT NULL |                |
|             | 생성일 | createdAt |   datetime   | NOT NULL |                |
|             | 포인트 |  points   |    bigint    | NOT NULL |                |

|     회원      |      |            |             |          |     users      |
|:-----------:|:----:|:----------:|:-----------:|:--------:|:--------------:|
| primary key | 회원번호 | customerId |   bigint    | NOT NULL | auto_increment |
|             | 아이디  |   userId   | varchar(16) | NOT NULL |                |
|             | 비밀번호 |  password  | varchar(32) | NOT NULL |                |
|             |  이름  |    name    | varchar(16) | NOT NULL |                |
|             | 이메일  |   email    | varchar(64) | NOT NULL |                |


