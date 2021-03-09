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

-- drop table user;
CREATE TABLE user (
     id INT NOT NULL AUTO_INCREMENT,
     email VARCHAR(100) NOT NULL,
     first_name VARCHAR(100) NOT NULL,
     last_name VARCHAR(100) NOT NULL,
     birth_date DATE,
     user_type_id TINYINT,
     PRIMARY KEY (id),
     FOREIGN KEY (user_type_id)
                  REFERENCES user_type(id)
);

-- drop table user_privilege;
CREATE TABLE user_privilege (
    id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL,
    primary key (id)
);

-- drop table course;
CREATE TABLE course (
    id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    primary key (id)
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
CREATE TABLE submission(
    id INT NOT NULL AUTO_INCREMENT,
    student_id INT NOT NULL,
    deliverable_id INT NOT NULL,
    submission_dt DATETIME,
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
    term_id INT NOT NULL,
    grade TINYINT,
    letter_grade CHAR(1),
    FOREIGN KEY (stud_id)
                  REFERENCES user(id),
    FOREIGN KEY (course_id)
                  REFERENCES course(id),
    FOREIGN KEY (term_id)
                  REFERENCES term(id)
);

-- drop table registration_application;
CREATE TABLE registration_application (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    primary key (id)
)