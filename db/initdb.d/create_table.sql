CREATE TABLE statistics_post
(
    id              INT(11) AUTO_INCREMENT COMMENT '통계 피드 일련번호',
    post_id         VARCHAR(32) NOT NULL COMMENT '피드 아이디',
    view_count      BIGINT      NOT NULL DEFAULT 0 COMMENT '조회수',
    like_count      BIGINT      NOT NULL DEFAULT 0 COMMENT '좋아요 수',
    stat_type       VARCHAR(32) NOT NULL COMMENT '통계 타입',
    post_created_at DATETIME             DEFAULT CURRENT_TIMESTAMP() COMMENT '피드 생성일자',
    PRIMARY KEY (id)
) COMMENT '통계용 피드 테이블';

CREATE TABLE statistics_hash_tag
(
    stat_post_id INT(11)      NOT NULL COMMENT '통계용 피드 아이디',
    hash_tag     VARCHAR(100) NOT NULL COMMENT '해시태그',
    PRIMARY KEY (stat_post_id, hash_tag)
) COMMENT '통계용 게시글 해시태그';

CREATE TABLE posts
(
    post_id     VARCHAR(100) COMMENT '게시물 일련번호',
    type        VARCHAR(100)  NOT NULL COMMENT '유형',
    title       VARCHAR(150)  NOT NULL COMMENT '제목',
    content     MEDIUMTEXT    NOT NULL COMMENT '내용',
    hashtags    VARCHAR(1024) NOT NULL COMMENT '해시태그',
    like_count  BIGINT        NOT NULL DEFAULT 0 COMMENT '좋아요 수',
    view_count  BIGINT        NOT NULL DEFAULT 0 COMMENT '조회 수',
    share_count BIGINT        NOT NULL DEFAULT 0 COMMENT '공유 수',
    updated_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '수정일',
    created_at  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '생성일',
    user_id     VARCHAR(100)  NOT NULL COMMENT '사용자 일련번호',
    PRIMARY KEY (post_id)
);

CREATE TABLE users
(
    user_id  CHAR(36) COMMENT '사용자 일련번호',
    account  VARCHAR(50)   not null COMMENT '계정',
    email    VARCHAR(50)   not null COMMENT '이메일',
    password varchar(1024) not null COMMENT '비밀번호',
    grade    varchar(100)  not null COMMENT '등급',
    PRIMARY KEY (user_id)
);

CREATE TABLE tokens
(
    token_id      BIGINT AUTO_INCREMENT COMMENT '토큰 일련번호',
    refresh_token VARCHAR(512) NOT NULL COMMENT '리프레시 토큰',
    user_id       CHAR(36)     NOT NULL COMMENT '사용자 일련번호',
    PRIMARY KEY (token_id)
) COMMENT '토큰';

CREATE table codes
(
    code_id      BIGINT AUTO_INCREMENT COMMENT '인증코드 일련번호',
    auth_code    VARCHAR(10) NOT NULL COMMENT '인증코드',
    created_time DATETIME    NOT NULL COMMENT '생성시간',
    user_id      CHAR(36)    NOT NULL COMMENT '사용자 일련번호',
    PRIMARY KEY (code_id)
) COMMENT '인증코드';
