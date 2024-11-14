create table scores
(
    id      bigint primary key auto_increment,
    `name`  varchar(100),
    subject varchar(100),
    score   int
) engine = innodb
  charset = utf8mb4
  collate = utf8mb4_unicode_ci;


insert into scores(`name`, `subject`, `score`)
values ('学生a', 'java', '100'),
       ('学生b', 'java', '90'),
       ('学生c', 'java', '90'),
       ('学生d', 'java', '60'),
       ('学生e', 'java', '80'),
       ('学生a', 'python', '100'),
       ('学生b', 'python', '90'),
       ('学生c', 'python', '90'),
       ('学生d', 'python', '60'),
       ('学生e', 'python', '80');


#查询各科前3名的学生
SELECT s1.name, s1.subject, s1.score
FROM scores s1
         LEFT JOIN (
    SELECT SUBJECT, score
    FROM scores) s2
                   ON s1.subject = s2.subject AND s1.score < s2.score
GROUP BY s1.name, s1.subject, s1.score
HAVING COUNT(1) < 3
ORDER BY SUBJECT, score DESC;
