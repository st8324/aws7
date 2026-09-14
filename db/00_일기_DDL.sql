-- =========================================================
-- 일기 작성 웹사이트 DDL (MySQL 8.0+)
-- ERD 기준: USERS - DIARY - EMOTION_TAG - DIARY_EMOTION - COMMENT
-- PK/FK: BIGINT AUTO_INCREMENT
-- v1.2: 공개(FR-13,14) / 비공개(FR-12) 일기 조회 요구사항 반영
-- =========================================================

CREATE DATABASE IF NOT EXISTS diary_service
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE diary_service;

-- ---------------------------------------------------------
-- 1. USERS
-- ---------------------------------------------------------
CREATE TABLE users (
    user_id     BIGINT       NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,       -- 암호화된 비밀번호(해시) 저장
    nickname    VARCHAR(50)  NOT NULL,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id),
    UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------
-- 2. DIARY
-- is_public: FR-17(공개/비공개 설정) 기준값, 기본값 0(비공개)
-- FR-09(하루 1개 제한)을 DB 레벨에서도 보장하기 위해
-- user_id + diary_date 유니크 제약 추가
-- ---------------------------------------------------------
CREATE TABLE diary (
    diary_id    BIGINT       NOT NULL AUTO_INCREMENT,
    user_id     BIGINT       NOT NULL,
    diary_date  DATE         NOT NULL,        -- 일기가 해당하는 날짜 (하루 1개 제한 기준)
    title       VARCHAR(200) NOT NULL,
    content     TEXT         NOT NULL,
    image_url   VARCHAR(500) NULL,
    is_public   TINYINT(1)   NOT NULL DEFAULT 0,   -- 0: 비공개(기본값), 1: 전체 공개
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
                             ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (diary_id),
    UNIQUE KEY uk_diary_user_date (user_id, diary_date),  -- FR-09: 하루 1개 제한
    KEY idx_diary_user_id (user_id),
    KEY idx_diary_public_created (is_public, created_at), -- FR-13: 공개 일기 목록 조회 성능용
    CONSTRAINT fk_diary_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------
-- 3. EMOTION_TAG (감정 태그 마스터 테이블 - 미리 정의된 값)
-- ---------------------------------------------------------
CREATE TABLE emotion_tag (
    emotion_tag_id  BIGINT      NOT NULL AUTO_INCREMENT,
    name            VARCHAR(30) NOT NULL,
    emoji           VARCHAR(10) NULL,
    display_order   INT         NOT NULL DEFAULT 0,

    PRIMARY KEY (emotion_tag_id),
    UNIQUE KEY uk_emotion_tag_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------
-- 4. DIARY_EMOTION (DIARY - EMOTION_TAG 다대다 매핑)
-- ---------------------------------------------------------
CREATE TABLE diary_emotion (
    diary_id        BIGINT NOT NULL,
    emotion_tag_id  BIGINT NOT NULL,

    PRIMARY KEY (diary_id, emotion_tag_id),
    KEY idx_diary_emotion_tag (emotion_tag_id),
    CONSTRAINT fk_diary_emotion_diary
        FOREIGN KEY (diary_id) REFERENCES diary (diary_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_diary_emotion_tag
        FOREIGN KEY (emotion_tag_id) REFERENCES emotion_tag (emotion_tag_id)
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------
-- 5. COMMENT (FR-18: 공개된 일기에 한해 댓글 작성 가능)
-- 공개 여부 자체는 diary.is_public으로 판단하며,
-- "공개 일기에만 댓글 허용"은 애플리케이션 레벨에서 검증
-- (등록 시 대상 diary.is_public = 1 여부 확인 후 INSERT)
-- ---------------------------------------------------------
CREATE TABLE comment (
    comment_id  BIGINT   NOT NULL AUTO_INCREMENT,
    diary_id    BIGINT   NOT NULL,
    user_id     BIGINT   NOT NULL,
    content     TEXT     NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (comment_id),
    CONSTRAINT fk_comment_diary
        FOREIGN KEY (diary_id) REFERENCES diary (diary_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id) REFERENCES users (user_id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
