<div align="center">

# Social-Network-Service

### ✨ Backend TechStack ✨
![Java](https://img.shields.io/badge/-Java-FF7800?style=flat&logo=Java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-flat&logo=spring&logoColor=white)
![SpringBoot](https://img.shields.io/badge/-SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![SpringDataJPA](https://img.shields.io/badge/SpringDataJpa-236DB33F?style=flat&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-EB5424?style=flat&logo=auth0&logoColor=white)

### 🍀 서비스 개요 🍀
본 서비스는 유저 계정의 해시태그(”#dani”) 를 기반으로 `인스타그램`, `스레드`, `페이스북`, `트위터(X)` 등 <br>
복수의 SNS에 게시된 게시물 중 유저의 해시태그가 포함된 게시물들을 하나의 서비스에서 확인할 수 있는 <br>
통합 Feed 어플리케이션 입니다.
</div>

</br>

## [목차]
- [📁 디렉토리 구조](#디렉토리-구조)
- [📦 ERD](#erd)
- [💌 API 명세서](#api-명세서)
- [✉ Git Commit Message Convention](#-git-commit-message-convention)
- [🌿 Git Branch 전략](#-git-branch-전략)

## 디렉토리 구조

## ERD
<img src="https://github.com/user-attachments/assets/10551fb6-14a2-4e52-a797-f0c7e5d7c2d2" width="70%" height="100%">
</br>

## API 명세서

| 기능 | Method | URL |
|------|--------|------|
| 회원가입 | POST | /api/users/sign-up |
| 로그인 | POST | /api/users/login |
| JWT 재발급 | POST | /api/token |
| 회원 가입 승인 | POST | /api/users/approve
| 인증코드 재발급 | POST | /api/users/reissue-code |
| 게시물 목록 | GET | /api/posts?hashtag={account}&type={type}</br>&orderBy={createdAt}&sortDirection={ASC}</br>&search_by={title}&search={search_keyword}</br>&page={page_number}&page_count={count_number} |
| 게시물 상세 | GET | /api/posts/:postId |
| 게시물 좋아요 | POST | /api/posts/likes/:postId |
| 게시물 공유 | POST | /api/posts/share/:postId |
| 통계 | GET | /api/statistics |

</br>

<details>
<summary>사용자</summary>
<div markdown="1">

# A. 사용자 🤸‍♀️

## ☁️ 회원가입
 ### 1. 설명
 - `계정`, `이메일`, `비밀번호`을 입력해 회원가입합니다.
 -  회원가입에 성공하면 `인증코드`가 발급됩니다.
  
### 2. 처리 과정
 a. `계정`,`이메일`,`비밀번호` 입력

 b. `비밀번호` 유효성 검사 (2가지 이상 만족)
    <details>
      <summary> 비밀번호 유효성 검사 조건 </summary>

          • 계정, 이메일 아이디와 유사한 비밀번호는 사용할 수 없습니다.
          • 비밀번호는 최소 10자 이상이어야 합니다.
          • 숫자로만 이루어진 비밀번호는 사용할 수 없습니다.
          • 숫자, 문자, 특수문자 중 2가지 이상을 포함해야 합니다.
          • 3회 이상 연속되는 문자 사용이 불가합니다.
</details>

c. 유효성 검사에 통과한 `비밀번호`는 암호화되어 DB에 저장

d. 가입 인증코드 발급 

e. 인증코드, 사용자 정보 DB에 저장

### 3. 입력
```json
{
  "account": "계정",
  "email": "이메일",
  "password": "비밀번호"
}
```
### 4. 출력 
#### - Response : 성공시
```json
{
    "message": "회원가입이 성공적으로 완료됐습니다.",
    "user": {
        "account": "계정",
        "email": "이메일"
    },
    "authCode": "회원가입 인증 코드"
}
```
#### -  Response : 실패시
 - `400 Bad Request`
    - 이메일 형식 오류
    - 비밀번호 제약 조건 오류
   
  - `409 Conflict`
    - 계정이나 이메일 주소가 이미 사용 중인 경우
  - `500 Internal Server Error`

## ☁️ 가입승인
 ### 1. 설명
 - 회원가입 시, 생성된 6자리 랜덤 `인증코드`를 입력합니다.
- `계정`, `비밀번호`, `인증코드`가 올바르게 입력되면 가입승인됩니다.
- 가입승인 되면 `회원등급`이 변경됩니다.
  
### 2. 처리 과정
 a. `계정`,`비밀번호`,`인증코드` 입력

 b. `계정`, `비밀번호` 회원정보 일치 조회

 c. 사용자의 모든 `인증코드` DB 조회 (최신순 정렬)

 d. 입력된 `인증코드`와 가장 최근 발급된 `인증코드` 일치 조회

 e. `인증코드` 유효성 검증 (유효시간 15분)

 f. 회원등급 변경  `NORMAL_USER` → `PREMIUM_USER`

 g. 인증 완료 회원의 `인증코드` DB 삭제

### 3. 입력
```json
{
    "account" : "사용자 계정",
    "password" : "비밀번호",
    "inputCode" : "인증코드"
}
```
### 4. 출력 
#### - Response : 성공시
```json
{
    "message": "인증이 성공적으로 완료되었습니다!",
    "userInfo": {
        "account": "사용자 계정",
        "email": "이메일",
        "grade": "회원등급"
    }
}
```
#### -  Response : 실패시
 - `400 Bad Request`
    - 계정 오류
    - 비밀번호 오류
    - 인증코드 오류
    - 인증코드 만료
  
  - `500 Internal Server Error`

## ☁️ 인증코드 재발급
 ### 1. 설명
 - `인증코드` 재발급을 요청합니다.
  
### 2. 처리 과정
 a. `계정`,`비밀번호` 입력

 b. `계정`, `비밀번호` 회원정보 일치 조회

 c. 새로운 `인증코드` 발급

   d. `인증코드` 저장

   e. 회원등급 변경  `NORMAL_USER` → `PREMIUM_USER`

### 3. 입력
```json
{
    "account" : "사용자 계정",
    "password" : "비밀번호"
}
```
### 4. 출력 
#### - Response : 성공시
```json
{
    "message": "인증코드가 성공적으로 재발급되었습니다",
    "newAuthCode": "재발급 받은 인증코드"
}
```
#### -  Response : 실패시
 - `400 Bad Request`
    - 계정 오류
    - 비밀번호 오류
  - `500 Internal Server Error`
</div>
</details>

<details>
<summary>로그인 & JWT 재발급</summary>
<div markdown="1">

# 기능 요구사항

1. 로그인
    - 설명
        - 회원가입한 회원은 로그인할 수 있다.
        - 로그인에 성공하면 `JWT`가 발급된다.
        - **이후 모든 서비스 이용 시 `JWT`를 사용자 인증에 사용한다.**
    - 입력
        - `계정`, `비밀번호`
    - 출력
        - 성공 : `회원 고유 식별값(id)`, `액세스 토큰`
        - 실패 : `오류 메시지`
    - 처리 과정
        - `계정`과 `비밀번호`로 회원이 존재하는지 확인한다.
        - 존재하는 경우 `액세스 토큰`은 발급받아 출력하고 `리프레시 토큰`은 발급받아 DB에 저장한다.
2. JWT 재발급
    - 설명
        - `액세스 토큰` 과 `리프레시 토큰`을 재발급할 수 있다.
    - 입력
        - `액세스 토큰`, `리프레시 토큰`
    - 출력
        - 성공 : `재발급받은 액세스 토큰`, `재발급받은 리프레시 토큰`
        - 실패 : `오류 메시지`
    - 처리 과정
        - `리프레시 토큰`유효성을 검증한다.
        - 유효한 경우 `액세스 토큰`과 `리프레시 토큰`을 재발급한다.
        - `리프레시 토큰`은 재발급 받은 것으로 업데이트한다.

</div>
</details>

<details>
<summary>게시물 조회</summary>
<div markdown="1">

- 게시물 조회
  - 본인의 계정의 해쉬 태그로 조회하는 기능
  - 선택 시 해당 type의 필드를 보여주는 기능
  - 목록을 정렬해주는 기능
  - 생성날짜순, 업데이트된날짜순, 좋아요순, 공유순, 조회수순 으로 오름차순과 내림차순으로 정렬할 수 있다.
  - 일반 검색을 할 수 있는 기능
  - 기본으로 10개의 게시물을 한 번에 보여주는 기능
  - 조회하려는 페이지 선택 시 해당 페이지만 보여주는 기능

</div>
</details>

<details>
<summary>게시물 상세</summary>
<div markdown="1">

- 게시물 상세(API)
	- 유저가 게시물을 클릭 시 사용되는 API
	- 모든 필드 값을 확인
	- API 호출 시, 해당 게시물 view_count 가 1 증가

</div>
</details>

<details>
<summary>게시물 좋아요 & 공유하기</summary>
<div markdown="1">

<div>

## 게시물 좋아요

### 기능 요구사항
  게시물 목록 또는 상세 에서 게시물 `좋아요` 클릭 시 사용되는 API
  - 좋아요 클릭 시 각 SNS 별 명시된 API 를 호출합니다.
  - 해당 호출이 성공할 시 `response status 200` 해당 게시물의 `like_count`가 1 증가합니다.
  - 횟수 제한이 없습니다. 한 유저가 몇 번의 좋아요를 누르던 좋아요 수는 계속 상승합니다.
  
### API 처리 과정
1. postId를 통해 게시물의 SNS 유형을 조회한다. ex) FACEBOOK, TWITTER, INSTAGRAM, THREADS
2. SNS 유형별로 엔드 포인트를 만들고, 외부 SNS API 호출을 한다. (기능 개발을 위한 요소로, 실제 동작하지 않음)
3. 외부 SNS API를 호출한다고 가정하고, **성공하면 좋아요 수를 증가**시키고,  200을 보낸다.

<details>
  <summary><strong>API 테스트</strong></summary>
  <div markdown="1">

  ### Request
  ```java
HTTP : POST
URL: /api/posts/likes/:postId
```

### Response : 성공시
```java
{
    "postId": "게시물아이디",
    "messgae": "좋아요 수 증가 완료"
}
```

### Response : 실패시
- `500 Internal Server Error`

  - 잘못된 postId를 입력했을 때 
    ```java
    {
        "postId": "게시물아이디",
        "messgae": {
            "좋아요 수 증가 실패": "존재하지 않는 엔티티입니다."
        }
    }
    ```
  </div>
</details>
  
## 게시물 공유

### 기능 요구사항
  게시물 목록 또는 상세 에서 `공유하기` 클릭 시 사용되는 API
  - 좋아요 클릭 시 각 SNS 별 명시된 API 를 호출합니다.
  - 해당 호출이 성공할 시 `response status 200` 해당 게시물의 `share_count`가 1 증가합니다.
  - 횟수 제한이 없습니다. 한 유저가 몇 번의 공유를 누르던 공유 수는 계속 상승합니다.

### API 처리 과정
1. postId를 통해 게시물의 SNS 유형을 조회한다. ex) FACEBOOK, TWITTER, INSTAGRAM, THREADS
2. SNS 유형별로 엔드 포인트를 만들고, 외부 SNS API 호출을 한다. (기능 개발을 위한 요소로, 실제 동작하지 않음)
3. 외부 SNS API를 호출한다고 가정하고, **성공하면 공유 수를 증가**시키고,  200을 보낸다.

<details>
  <summary><strong>API 테스트</strong></summary>
  <div markdown="1">
  
  ### Request
```java
HTTP : POST
URL: /api/posts/share/:postId
```

### Response : 성공시
```java
{
    "postId": "게시물아이디",
    "messgae": "공유 수 증가 완료"
}
```

### Response : 실패시
- `500 Internal Server Error`

  - 잘못된 postId를 입력했을 때 
    ```java
    {
        "postId": "게시물아이디",
        "messgae": {
            "공유 수 증가 실패": "존재하지 않는 엔티티입니다."
        }
    }
    ```
  </div>
</details>
</div>

</div>
</details>

<details>
<summary>통계</summary>
<div markdown="1">

| queryParam | 속성 | default(미입력 시 값) | **설명** |
| --- | --- | --- | --- |
| hashtag | string | 본인계정 |  |
| type | string (열거형) | 필수 값 | date, hour |
| start | date | 오늘로 부터 7일전 | 2023-10-01 과 같이 데이트 형식이며 조회 기준 시작일을 의미합니다. |
| end | date | 오늘 | 2023-10-25 과 같이 데이트 형식이며 조회 기준 시작일을 의미합니다. |
| value | string (열거형) | count (게시물 개수) | count , view_count, like_count, share_count 가 사용 가능합니다. |
- `value`
    - `count` 일 시, 게시물 개수
    - `view_count` 일 시, 해시 태그에 해당하는 게시물 들의 `view_count` 의 합(`like_count`,`share_count` 도 동일)
- `?value=count&type=date` 일시, `start` ~ `end` 기간내 (시작일, 종료일 포함) 해당 `hashtag` 가 포함된 게시물 수를 일자별로 제공합니다.
    - ex) api/statistics?type=DATE&hashtag=user1
    - D-7일전부터 D-day까지 user1이라는 해시태그가 포함된 게시글의 개수를 반환합니다.
- `?value=count&type=hour` 일시, `start` ~ `end` 기간내 (시작일, 종료일 포함) 해당 `hashtag` 가 포함된 게시물 수를 시간별로 제공합니다.
    - `start` 일자의 00시 부터 1시간 간격으로.
    - ex) api/statistics?type=HOUR&hashtag=고양이
    - D-7일전 00:00:00부터 D-day23:59:59까지 고양이이라는 해시태그가 포함된 게시글의 개수를 반환합니다.

</div>
</details>


## ✉ Git Commit Message Convention
<details>
<summary>커밋 유형</summary>
<div markdown="1">
</br>
  <table>
    <tr>
      <th scope="col">커밋 유형</td>
      <th scope="col">의미</td>
    </tr>
    <tr>
      <td>feat</td>
      <td>새로운 기능 추가</td>
    </tr>
    <tr>
      <td>fix</td>
      <td>버그 수정</td>
    </tr>
    <tr>
      <td>docs</td>
      <td>문서 수정</td>
    </tr>
    <tr>
      <td>style</td>
      <td>코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우</td>
    </tr>
    <tr>
      <td>refactor</td>
      <td>코드 리팩토링</td>
    </tr>
    <tr>
      <td>test</td>
      <td>테스트 코드, 리팩토링 테스트 코드 추가</td>
    </tr>
    <tr>
      <td>chore</td>
      <td>패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore</td>
    </tr>
  </table>
  </br>
</div>
</details>

<details>
<summary>커밋 메세지 세부 내용</summary>
<div markdown="1">
</br>
&emsp;• 글로 작성하여 내용이 잘 전달될 수 있도록 할 것 </br></br>
&emsp;• 본문에는 변경한 내용과 이유 설명 (어떻게보다는 무엇 & 왜를 설명)</br>
&emsp;<div align="center" style="width:90%; height: 140px; border: 1px solid gray; border-radius: 1em; background-color: #AEADAB; color: black; text-align: left ">
&emsp;ex ) </br>
&emsp;refactor : 로그인 기능 변경 (title)</br>
&emsp;( 공 백 ) </br>
&emsp;기존 로그인 방식에서 ~~한 문제로 ~~한 방식으로 변경하였습니다. (content)
</br>
</br>
&emsp;feat : 로그인 기능 구현
</div>
</div>
</details>

## 🌿 Git Branch 전략
<details>
<summary>브렌치 명명 규칙</summary>
<div markdown="1">
</br>

`feat/기능명`

- ex)  feat/users_apply

</div>
</details>

<details>
<summary>브랜치 작성 방법</summary>
<div markdown="1">
</br>

- 브랜치는 기능 단위로 나눈다.
- feat 브랜치는 dev 브랜치에서 파생해서 만든다.
- PR을 통해 dev 브랜치에서 기능이 완성되면 main 브랜치로 merge 한다.

</br>

|이름|텍스트|
|----|-----|
|main|제품으로 출시될 수 있는 브랜치|
|dev|다음 출시 버전을 개발하는 브랜치|
|feat|기능을 개발하는 브랜치|

</div>
</details>

</br>
