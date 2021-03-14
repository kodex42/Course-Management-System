-- drop table user_type;
CREATE TABLE user_type (
    id TINYINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(50),
    PRIMARY KEY (id)
);
INSERT INTO user_type (type) VALUES ('STUDENT');
INSERT INTO user_type (type) VALUES ('PROFESSOR');
INSERT INTO user_type (type) VALUES ('ADMIN');
COMMIT;

/*
SET FOREIGN_KEY_CHECKS = 0
drop table user
SET FOREIGN_KEY_CHECKS = 1
*/
CREATE TABLE user (
     id INT NOT NULL AUTO_INCREMENT,
     username VARCHAR(100) NOT NULL,
     password VARCHAR(100) NOT NULL,
     first_name VARCHAR(100) NOT NULL,
     last_name VARCHAR(100) NOT NULL,
     birth_date DATE,
     user_type_id TINYINT,
     PRIMARY KEY (id, username),
     FOREIGN KEY (user_type_id)
                  REFERENCES user_type(id)
);

INSERT INTO user (username, password, first_name, last_name, user_type_id)
SELECT d.username,
       d.pass,
       d.fn,
       d.ln,
       t.id
FROM (
         SELECT 'admin'                                               username,
                '$2a$10$aY1CHojFgB2k6HmO2gZiAun4dn4yb.VqQmVZ2CxfYkGK2.gZ.jMu6' pass,
                'ADMIN'                                                        fn,
                'ADMIN'                                                        ln
         FROM dual
     ) d
         JOIN user_type t
              ON t.type = 'ADMIN';

INSERT INTO user (username, password, first_name, last_name, birth_date, user_type_id)
SELECT d.username,
       d.pass,
       d.fn,
       d.ln,
       d.bd,
       t.id
FROM (
         SELECT 'student@mail.com'                                             username,
                '$2a$10$gFhrK/bUuf7JrHYLJRaLI.g7h9QdH0k/miv2uUuzkSkt6HDSqMAoe' pass,
                'student'                                                      fn,
                'student'                                                      ln,
                '2021-03-10'                                                    bd
         FROM dual
     ) d
         JOIN user_type t
              ON t.type = 'STUDENT';

INSERT INTO user (username, password, first_name, last_name, user_type_id)
SELECT d.username,
       d.pass,
       d.fn,
       d.ln,
       t.id
FROM (
         SELECT 'professor@mail.com'                                           username,
                '$2a$10$ThbapmPJHqYG4AqBPyeiQ.5fOU7fU3Tp6Lc5LNslMZzGjcx9kh4Iu' pass,
                'professor'                                                    fn,
                'professor'                                                    ln
         FROM dual
     ) d
         JOIN user_type t
              ON t.type = 'PROFESSOR';
commit;

-- drop table user_privilege;
CREATE TABLE user_privilege (
    id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL,
    primary key (id)
);

/*
SET FOREIGN_KEY_CHECKS = 0
drop table course
SET FOREIGN_KEY_CHECKS = 1
*/
CREATE TABLE course (
    id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    term_id INT NOT NULL,
    description TEXT,
    primary key (id, term_id),
    FOREIGN KEY (term_id)
        REFERENCES term(id)
);

-- drop table deliverable;
CREATE TABLE deliverable (
    id INT NOT NULL AUTO_INCREMENT,
    course_id INT NOT NULL,
    description TEXT,
    author_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (course_id)
                  REFERENCES course(id),
    FOREIGN KEY (author_id)
                  REFERENCES user(id)
);

-- drop table submission
CREATE TABLE submission (
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    deliverable_id INT NOT NULL,
    submission_dt DATETIME,
    grade TINYINT,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id)
                  REFERENCES user(id),
    FOREIGN KEY (deliverable_id)
                  REFERENCES deliverable(id)
);


-- dtop table season
CREATE TABLE season (
    id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(80),
    primary key (id)
);

-- drop table term;
CREATE TABLE term (
     id INT NOT NULL AUTO_INCREMENT,
     season_id INT NOT NULL,
     start_date DATE NOT NULL,
     end_date DATE NOT NULL,
     registration_deadline DATE NOT NULL,
     wdn_deadline DATE NOT NULL,
     withdrawal_deadline DATE NOT NULL,
     primary key (id, start_date, end_date),
     FOREIGN KEY (season_id)
                  REFERENCES season(id)
);

-- drop table course_x_professor;
CREATE TABLE professor_x_course (
    prof_id INT NOT NULL,
    course_id INT NOT NULL,
    term_id INT NOT NULL,
    primary key (prof_id, course_id, term_id),
    FOREIGN KEY (prof_id)
                  REFERENCES user(id),
    FOREIGN KEY (course_id)
                  REFERENCES course(id),
    FOREIGN KEY (term_id)
                  REFERENCES term(id)
);


-- drop table student_x_course;
CREATE TABLE student_x_course (
    stud_id INT NOT NULL,
    course_id INT NOT NULL,
    grade TINYINT,
    letter_grade CHAR(1),
    FOREIGN KEY (stud_id)
                  REFERENCES user(id),
    FOREIGN KEY (course_id)
                  REFERENCES course(id)
);

-- drop table reg_application;
CREATE TABLE reg_application (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    primary key (id)
);
