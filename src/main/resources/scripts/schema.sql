DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
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

DROP TABLE IF EXISTS FOLLOW;
CREATE TABLE FOLLOW
(
    id          bigint NOT NULL AUTO_INCREMENT,
    follower_Id bigint,
    followee_Id bigint,
    unfollow    boolean,
    created_at  timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS ARTICLE;
CREATE TABLE ARTICLE
(
    id          bigint NOT NULL AUTO_INCREMENT,
    slug       varchar(255),
    title       varchar(255),
    description varchar(500),
    body        mediumtext,
    is_deleted  boolean,
    created_at  timestamp default NOW(),
    updated_at  timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS COMMENTS;
CREATE TABLE COMMENTS
(
    id         bigint NOT NULL AUTO_INCREMENT,
    content    mediumtext,
    articleId  bigint NOT NULL,
    is_deleted boolean,
    created_at timestamp default NOW(),
    updated_at timestamp default NOW(),
    primary key (id)
);

DROP TABLE IF EXISTS TAG;
CREATE TABLE TAG
(
    id         bigint NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255),
    articleId  bigint NOT NULL,
    created_at timestamp default NOW(),
    is_deleted boolean,
    primary key (id)
);

