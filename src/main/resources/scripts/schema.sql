DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         bigint NOT NULL AUTO_INCREMENT,
    email      varchar(100),
    username   varchar(50),
    bio        varchar(300),
    image      varchar(500),
    password   varchar(500),
    created_at timestamp default NOW(),
    updated_at timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS follow;
CREATE TABLE follow
(
    id          bigint NOT NULL AUTO_INCREMENT,
    follower_Id bigint,
    followee_Id bigint,
    unfollow    boolean,
    created_at  timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS article;
CREATE TABLE article
(
    id          bigint NOT NULL AUTO_INCREMENT,
    user_id     bigint NOT NULL,
    slug        varchar(255),
    title       varchar(255),
    description varchar(500),
    body        mediumtext,
    is_deleted  boolean,
    created_at  timestamp default NOW(),
    updated_at  timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS comments;
CREATE TABLE comments
(
    id         bigint NOT NULL AUTO_INCREMENT,
    body    mediumtext,
    article_id bigint NOT NULL,
    user_id bigint NOT NULL,
    is_deleted boolean,
    created_at timestamp default NOW(),
    updated_at timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS tag;
CREATE TABLE tag
(
    id         bigint NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255),
    article_id bigint NOT NULL,
    created_at timestamp default NOW(),
    is_deleted boolean,
    primary key (id)
);

DROP TABLE IF EXISTS favorite;
CREATE TABLE favorite
(
    id         bigint NOT NULL AUTO_INCREMENT,
    user_id    bigint NOT NULL,
    article_id bigint NOT NULL,
    is_deleted boolean,
    created_at timestamp default NOW(),
    primary key (id)
);