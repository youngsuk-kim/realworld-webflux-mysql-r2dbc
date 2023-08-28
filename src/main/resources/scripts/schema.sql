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
    id         bigint NOT NULL AUTO_INCREMENT,
    follower_Id bigint,
    followee_Id bigint,
    unfollow   boolean,
    created_at timestamp default NOW(),
    primary key (id)
);
