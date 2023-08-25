DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    id          bigint NOT NULL AUTO_INCREMENT,
    email       varchar(100),
    username    varchar(50),
    password    varchar(500),
    created_at  timestamp default NOW(),
    updated_at  timestamp default NOW(),
    primary key (id)
);