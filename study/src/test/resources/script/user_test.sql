CREATE TABLE `users`
(
    `id`            bigint  NOT NULL AUTO_INCREMENT,
    `name`          longtext,
    `email`         longtext,
    `age`           tinyint  DEFAULT NULL,
    `birthday`      datetime         DEFAULT NULL,
    `member_number` longtext,
    `activated_at`  datetime         DEFAULT NULL,
    `created_at`    datetime         DEFAULT NULL,
    `updated_at`    datetime         DEFAULT NULL,
    `deleted_at`    datetime         DEFAULT NULL,
    PRIMARY KEY (`id`)
);
insert into users (name, email, age) values ('John Doe', 'john.doe@example.com', 25);