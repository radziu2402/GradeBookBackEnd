CREATE TABLE "user" (
  ID       SERIAL NOT NULL,
  Email    varchar(150) NOT NULL UNIQUE,
  Login    varchar(150) NOT NULL UNIQUE,
  Role     varchar(50) NOT NULL,
  Password varchar(80) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE Address (
  ID              SERIAL NOT NULL,
  City            varchar(50) NOT NULL,
  Street          varchar(50) NOT NULL,
  "post code"     varchar(10) NOT NULL,
  "street number" int4 NOT NULL,
  "flat number"   int4 NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE Administrator (
  "first name" varchar(50) NOT NULL,
  "last name"  varchar(50) NOT NULL,
  UsersID      int4 NOT NULL,
  PRIMARY KEY (UsersID),
  CONSTRAINT FK_Administrator_User FOREIGN KEY (UsersID) REFERENCES "user" (ID)
);

CREATE TABLE Class (
  ID   SERIAL NOT NULL,
  Name varchar(50) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE Subject (
  ID   SERIAL NOT NULL,
  Name varchar(50) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE Classroom (
  ID          SERIAL NOT NULL,
  "room name" varchar(50) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE Teacher (
  "first name"      varchar(50) NOT NULL,
  "last name"       varchar(50) NOT NULL,
  "date of birth"   date NOT NULL,
  "academic degree" varchar(50),
  UsersID           int4 NOT NULL,
  ClassID           int4,
  AddressID         int4 NOT NULL,
  PRIMARY KEY (UsersID),
  CONSTRAINT FK_Teacher_Class FOREIGN KEY (ClassID) REFERENCES Class (ID),
  CONSTRAINT FK_Teacher_Address FOREIGN KEY (AddressID) REFERENCES Address (ID),
  CONSTRAINT FK_Teacher_User FOREIGN KEY (UsersID) REFERENCES "user" (ID)
);

CREATE TABLE Lesson (
  ID             SERIAL NOT NULL,
  Topic          varchar(50),
  "date"         date NOT NULL,
  ClassID      int4 NOT NULL,
  SubjectsID     int4 NOT NULL,
  ClassroomID    int4 NOT NULL,
  TeacherUsersID int4 NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Lesson_Class FOREIGN KEY (ClassID) REFERENCES Class (ID),
  CONSTRAINT FK_Lesson_Subject FOREIGN KEY (SubjectsID) REFERENCES Subject (ID),
  CONSTRAINT FK_Lesson_Classroom FOREIGN KEY (ClassroomID) REFERENCES Classroom (ID),
  CONSTRAINT FK_Lesson_Teacher FOREIGN KEY (TeacherUsersID) REFERENCES Teacher (UsersID)
);

CREATE TABLE Student (
  "first name"    varchar(50) NOT NULL,
  "last name"     varchar(50) NOT NULL,
  "date of birth" date NOT NULL,
  ClassID       int4 NOT NULL,
  UsersID         int4 NOT NULL,
  AddressID       int4 NOT NULL,
  PRIMARY KEY (UsersID),
  CONSTRAINT FK_Student_Class FOREIGN KEY (ClassID) REFERENCES Class (ID),
  CONSTRAINT FK_Student_Address FOREIGN KEY (AddressID) REFERENCES Address (ID),
  CONSTRAINT FK_Student_User FOREIGN KEY (UsersID) REFERENCES "user" (ID)
);

CREATE TABLE Attendance (
  ID             SERIAL NOT NULL,
  "date"         date NOT NULL,
  Present        bool NOT NULL,
  LessonsID      int4 NOT NULL,
  StudentUsersID int4 NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Attendance_Lesson FOREIGN KEY (LessonsID) REFERENCES Lesson (ID),
  CONSTRAINT FK_Attendance_Student FOREIGN KEY (StudentUsersID) REFERENCES Student (UsersID)
);

CREATE TABLE Grade (
  ID                     SERIAL NOT NULL,
  "grade value"          numeric(3, 2) NOT NULL,
  SubjectsID             int4 NOT NULL,
  "date of modification" date NOT NULL,
  TeacherUsersID         int4 NOT NULL,
  StudentUsersID         int4 NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Grade_Subject FOREIGN KEY (SubjectsID) REFERENCES Subject (ID),
  CONSTRAINT FK_Grade_Student FOREIGN KEY (StudentUsersID) REFERENCES Student (UsersID),
  CONSTRAINT FK_Grade_Teacher FOREIGN KEY (TeacherUsersID) REFERENCES Teacher (UsersID)
);

CREATE TABLE Subjects_Teachers (
  SubjectsID     int4 NOT NULL,
  TeacherUsersID int4 NOT NULL,
  PRIMARY KEY (SubjectsID, TeacherUsersID),
  CONSTRAINT FK_SubjectsTeachers_Subject FOREIGN KEY (SubjectsID) REFERENCES Subject (ID),
  CONSTRAINT FK_SubjectsTeachers_Teacher FOREIGN KEY (TeacherUsersID) REFERENCES Teacher (UsersID)
);

-- Widok z informacjami o ocenach dla danego ucznia
CREATE VIEW StudentGrades AS
SELECT
    s."first name" AS "Student First Name",
    s."last name" AS "Student Last Name",
    g."grade value" AS "Grade",
    g."date of modification" AS "Date of Modification",
    t."first name" AS "Teacher First Name",
    t."last name" AS "Teacher Last Name"
FROM
    Grade g
JOIN Student s ON g.StudentUsersID = s.UsersID
JOIN Teacher t ON g.TeacherUsersID = t.UsersID;

-- Widok z listą obecności dla danego nauczyciela
CREATE VIEW TeacherAttendance AS
SELECT
    l."date" AS "Lesson Date",
    l.Topic AS "Lesson Topic",
    s."first name" AS "Student First Name",
    s."last name" AS "Student Last Name",
    a.Present
FROM
    Lesson l
JOIN Attendance a ON l.ID = a.LessonsID
JOIN Student s ON a.StudentUsersID = s.UsersID;

-- Widok z listą ocen dla danego nauczyciela
CREATE VIEW TeacherGrades AS
SELECT
    s."first name" AS "Student First Name",
    s."last name" AS "Student Last Name",
    g."grade value" AS "Grade",
    g."date of modification" AS "Date of Modification",
    t."first name" AS "Teacher First Name",
    t."last name" AS "Teacher Last Name",
    sub.Name AS "Subject"
FROM
    Grade g
JOIN Student s ON g.StudentUsersID = s.UsersID
JOIN Teacher t ON g.TeacherUsersID = t.UsersID
JOIN Subject sub ON g.SubjectsID = sub.ID;

-- Widok z listą uczniów w danej klasie
CREATE VIEW ClassStudents AS
SELECT
    s."first name"    AS "Student First Name",
    s."last name"     AS "Student Last Name",
    a.City            AS "City",
    a.Street          AS "Street",
    a."post code"     AS "Post Code",
    a."street number" AS "Street Number",
    a."flat number"   AS "Flat Number"
FROM
    Student s
JOIN Address a ON s.AddressID = a.ID;

-- Widok z listą lekcji dla danego ucznia (dla zalogowanego użytkownika)
CREATE OR REPLACE VIEW StudentLessons AS
SELECT
    l.date AS "Lesson Date",
    l.Topic AS "Lesson Topic",
    t."first name" AS "Teacher First Name",
    t."last name" AS "Teacher Last Name",
    g."grade value" AS "Grade"
FROM
    Lesson l
JOIN Subjects_Teachers st ON l.SubjectsID = st.SubjectsID
JOIN Teacher t ON st.TeacherUsersID = t.UsersID
LEFT JOIN Grade g ON l.ID = g.ID AND g.StudentUsersID = (
    SELECT ID
    FROM "user"
    WHERE Login = current_user
);

-- Widok z średnimi ocenami uczniów
CREATE VIEW StudentAverageGrade AS
SELECT
    s.UsersID AS "StudentUsersID",
    AVG(g."grade value") AS "Average grade"
FROM
    Student s
JOIN Grade g ON s.UsersID = g.StudentUsersID
GROUP BY s.UsersID;

CREATE INDEX idx_teacher_firstname_lastname ON Teacher ("first name", "last name");

CREATE INDEX idx_student_firstname_lastname ON Student ("first name", "last name");
CREATE INDEX idx_student_classid ON Student (ClassID);

CREATE INDEX idx_grade_dateofmodification ON Grade ("date of modification");

CREATE INDEX idx_lesson_date ON Lesson (date);
CREATE INDEX idx_lesson_teacheruserid ON Lesson (TeacherUsersID);
CREATE INDEX idx_lesson_classid_subjectsid ON Lesson (ClassID, SubjectsID);

CREATE INDEX idx_attendance_date ON Attendance (date);

CREATE INDEX idx_address_city ON Address (City);
CREATE INDEX idx_address_postcode ON Address ("post code");

CREATE INDEX idx_teacher_academicdegree ON Teacher ("academic degree");

-- Dodawanie nowego ucznia do klasy
CREATE OR REPLACE PROCEDURE add_student(
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    class_id INT
) LANGUAGE plpgsql AS $$
DECLARE
    user_id INT;
BEGIN
    -- Dodaj nowego użytkownika
    INSERT INTO "user" (Email, Login, Role, Password)
    VALUES ('newstudent@example.com', 'newstudent', 'STUDENT', 'defaultpassword')
    RETURNING ID INTO user_id;

    -- Dodaj nowego ucznia
    INSERT INTO Student ("first name", "last name", "date of birth",
                        ClassID, UsersID, AddressID)
    VALUES (first_name, last_name, date_of_birth, class_id, user_id, 1);
END;
$$;

-- Przenoszenie ucznia do innej klasy
CREATE OR REPLACE PROCEDURE move_student_to_class(
    student_id INT,
    new_class_id INT
) AS
$$
BEGIN
    UPDATE Student SET ClassID = new_class_id WHERE UsersID = student_id;
END;
$$ LANGUAGE plpgsql;

-- Usuwanie ucznia
CREATE OR REPLACE PROCEDURE remove_student(
    student_id INT
) AS
$$
BEGIN
    DELETE FROM Student WHERE UsersID = student_id;
    DELETE FROM "user" WHERE ID = student_id;
END;
$$ LANGUAGE plpgsql;

-- Aktualizacja daty modyfikacji przy zmianie oceny
CREATE OR REPLACE FUNCTION update_grade_modification_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW."date of modification" = CURRENT_DATE;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_date_trigger
BEFORE UPDATE
ON Grade
FOR EACH ROW
EXECUTE FUNCTION update_grade_modification_date();

-- Sprawdzanie poprawności oceny
CREATE OR REPLACE FUNCTION check_grade_validity()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW."grade value" < 2.0 OR NEW."grade value" > 5.0 THEN
        RAISE EXCEPTION 'Invalid grade value. Grade must be between 2.0 and 5.0';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_grade_trigger
BEFORE INSERT OR UPDATE
ON Grade
FOR EACH ROW
EXECUTE FUNCTION check_grade_validity();

INSERT INTO "user" (Email, Login, Role, Password) VALUES
('jan.kowalski@email.pl', 'jkowalski', 'ADMINISTRATOR', 'haslo123'),
('anna.nowak@email.pl', 'anowak', 'ADMINISTRATOR', 'haslo321'),
('piotr.wisniewski@email.pl', 'pwisniewski', 'ADMINISTRATOR', 'haslo456'),
('maria.dabrowska@email.pl', 'mdabrowska', 'ADMINISTRATOR', 'haslo789'),
('wojciech.lewandowski@email.pl', 'wlewandowski', 'ADMINISTRATOR', 'haslo101'),
('ewa.wojcik@email.pl', 'ewojcik', 'ADMINISTRATOR', 'haslo112'),
('michal.kaminski@email.pl', 'mkaminski', 'ADMINISTRATOR', 'haslo131'),
('katarzyna.kowalczyk@email.pl', 'kkowalczyk', 'ADMINISTRATOR', 'haslo141'),
('tomasz.zajac@email.pl', 'tzajac', 'ADMINISTRATOR', 'haslo151'),
('magdalena.szymanska@email.pl', 'mszymanska', 'ADMINISTRATOR', 'haslo161'),
('tomasz.zielinski@student.pl', 'tzielinski_student', 'STUDENT', 'haslo272'),
('magdalena.szymanska@student.pl', 'mszymanska_student', 'STUDENT', 'haslo282'),
('kamil.wojcik@student.pl', 'kwojcik_student', 'STUDENT', 'haslo292'),
('ola.nowakowska@student.pl', 'onowakowska_student', 'STUDENT', 'haslo302'),
('adam.kowalski@student.pl', 'akowalski_student', 'STUDENT', 'haslo312'),
('monika.dabrowska@student.pl', 'mdabrowska_student', 'STUDENT', 'haslo322'),
('pawel.jankowski@student.pl', 'pjankowski_student', 'STUDENT', 'haslo332'),
('kasia.zajac@student.pl', 'kzajac_student', 'STUDENT', 'haslo342'),
('marcin.wisniewski@student.pl', 'mwisniewski_student', 'STUDENT', 'haslo352'),
('aleksandra.kowalczyk@student.pl', 'akowalczyk_student', 'STUDENT', 'haslo362'),
('adam.jakubowski@student.pl', 'ajakubowski_student', 'STUDENT', 'haslo372'),
('katarzyna.borowska@teacher.pl', 'kborowska_teacher', 'TEACHER', 'haslo483'),
('marek.nowicki@teacher.pl', 'mnowicki_teacher', 'TEACHER', 'haslo493'),
('agnieszka.wojciechowska@teacher.pl', 'awojciechowska_teacher', 'TEACHER', 'haslo503'),
('piotr.kowalczyk@teacher.pl', 'pkowalczyk_teacher', 'TEACHER', 'haslo513'),
('karolina.sikorska@teacher.pl', 'ksikorska_teacher', 'TEACHER', 'haslo523'),
('tomasz.jankowski@teacher.pl', 'tjankowski_teacher', 'TEACHER', 'haslo533'),
('alicja.zajac@teacher.pl', 'azajac_teacher', 'TEACHER', 'haslo543'),
('rafal.wisniewski@teacher.pl', 'rwisniewski_teacher', 'TEACHER', 'haslo553'),
('kinga.kowalska@teacher.pl', 'kkowalska_teacher', 'TEACHER', 'haslo563'),
('grzegorz.dabrowski@teacher.pl', 'gdabrowski_teacher', 'TEACHER', 'haslo573');

INSERT INTO Address (City, Street, "post code", "street number", "flat number")VALUES
('Warszawa', 'Marszałkowska', '00-100', 10, 2),
('Kraków', 'Floriańska', '31-019', 15, 5),
('Gdańsk', 'Długa', '80-888', 20, 1),
('Poznań', 'Wielka', '61-100', 5, 3),
('Wrocław', 'Szkolna', '50-500', 12, 8),
('Łódź', 'Piotrkowska', '90-001', 18, 4),
('Szczecin', 'Wojska Polskiego', '70-100', 3, 7),
('Katowice', 'Słowackiego', '40-200', 7, 12),
('Kraków', 'Krakowska', '30-123', 14, 9),
('Gdynia', 'Hryniewickiego', '81-111', 22, 11);


INSERT INTO Administrator ("first name", "last name", UsersID) VALUES
('Jan', 'Kowalski', 1),
('Anna', 'Nowak', 2),
('Piotr', 'Wiśniewski', 3),
('Maria', 'Dąbrowska', 4),
('Wojciech', 'Lewandowski', 5),
('Ewa', 'Wójcik', 6),
('Michał', 'Kamiński', 7),
('Katarzyna', 'Kowalczyk', 8),
('Tomasz', 'Zając', 9),
('Magdalena', 'Szymańska', 10);

INSERT INTO Class (Name) VALUES
('1A'),
('2B'),
('3C'),
('1B'),
('2A'),
('3B'),
('1D'),
('2C'),
('1C'),
('3A');

INSERT INTO Student ("first name", "last name", "date of birth", ClassID, UsersID, AddressID) VALUES
('Tomasz', 'Zieliński', '2005-04-10', 1, 11, 1),
('Magdalena', 'Szymańska', '2006-07-22', 1, 12, 2),
('Kamil', 'Wójcik', '2005-01-15', 1, 13, 3),
('Ola', 'Nowakowska', '2006-05-05', 1, 14, 4),
('Adam', 'Kowalski', '2004-11-20', 1, 15, 5),
('Monika', 'Dąbrowska', '2003-10-18', 1, 16, 6),
('Paweł', 'Jankowski', '2005-08-08', 2, 17, 7),
('Kasia', 'Zając', '2006-03-30', 2, 18, 8),
('Marcin', 'Wiśniewski', '2004-06-12', 3, 19, 9),
('Aleksandra', 'Kowalczyk', '2003-09-25', 3, 20, 10);

INSERT INTO Teacher ("first name", "last name", "date of birth", "academic degree", UsersID, ClassID, AddressID) VALUES
('Katarzyna', 'Borowska', '1980-05-20', 'Doktor', 21, 1, 9),
('Marek', 'Nowicki', '1975-10-15', 'Magister', 22, 2, 8),
('Agnieszka', 'Wojciechowska', '1982-04-03', 'Doktor', 23, 3, 7),
('Piotr', 'Kowalczyk', '1978-12-08', 'Magister', 24, NULL, 6),
('Karolina', 'Sikorska', '1985-06-25', 'Doktor', 25, NULL, 5),
('Tomasz', 'Jankowski', '1977-08-01', 'Magister', 26, NULL, 4),
('Alicja', 'Zając', '1983-11-12', 'Doktor', 27, NULL, 3),
('Rafał', 'Wiśniewski', '1981-02-28', 'Magister', 28, NULL, 2),
('Kinga', 'Kowalska', '1979-07-10', 'Profesor', 29, NULL, 1),
('Grzegorz', 'Dąbrowski', '1976-09-15', 'Brak', 30, NULL, 10);

INSERT INTO Subject (Name) VALUES
('Matematyka'),
('Język polski'),
('Historia'),
('Fizyka'),
('Chemia'),
('Biologia'),
('Informatyka'),
('Język angielski'),
('Geografia'),
('Wychowanie fizyczne'),
('Muzyka'),
('Plastyka'),
('Technika'),
('Religia'),
('Wiedza o społeczeństwie');

INSERT INTO Classroom ("room name") VALUES
('Sala 101'),
('Sala 102'),
('Sala 103'),
('Sala 104'),
('Sala 105'),
('Sala 106'),
('Sala 107'),
('Sala 108'),
('Sala 109'),
('Sala 110');

INSERT INTO Lesson (Topic, date, ClassID, SubjectsID, ClassroomID, TeacherUsersID) VALUES
('Wprowadzenie do algebra', '2023-09-01', 1, 1, 1, 21),
('Gramatyka i ortografia', '2023-09-02', 2, 2, 2, 22),
('Podstawy historii Polski', '2023-09-03', 1, 3, 3, 23),
('Ruch i siły fizyczne', '2023-09-04', 1, 4, 4, 24),
('Podstawy chemii organicznej', '2023-09-05', 2, 5, 5, 25),
('Budowa komórek', '2023-09-06', 3, 6, 6, 26),
('Programowanie w Pythonie', '2023-09-07', 1, 7, 7, 27),
('Trening sportowy', '2023-09-08', 2, 8, 8, 28),
('Krajobraz i kultura geograficzna', '2023-09-09', 3, 9, 9, 29),
('Angielski dla zaawansowanych', '2023-09-10', 3, 10, 10, 30);


INSERT INTO Attendance (date, Present, LessonsID, StudentUsersID) VALUES
('2023-09-01', true, 1, 11),
('2023-09-02', false, 1, 11),
('2023-09-01', true, 2, 11),
('2023-09-02', true, 2, 11),
('2023-09-03', false, 3, 11),
('2023-09-03', true, 3, 16),
('2023-09-04', true, 4, 17),
('2023-09-04', true, 4, 18),
('2023-09-05', true, 5, 19),
('2023-09-05', false, 5, 20);

INSERT INTO Grade ("grade value", SubjectsID, "date of modification", TeacherUsersID, StudentUsersID) VALUES
(5.0, 1, '2023-09-05', 21, 11),
(4.5, 2, '2023-09-06', 22, 11),
(3.0, 3, '2023-09-07', 23, 11),
(4.0, 4, '2023-09-08', 24, 11),
(5.0, 5, '2023-09-09', 25, 11),
(4.0, 6, '2023-09-10', 26, 16),
(3.5, 7, '2023-09-11', 27, 17),
(4.5, 8, '2023-09-12', 28, 18),
(4.0, 9, '2023-09-13', 29, 19),
(3.5, 10, '2023-09-14', 30, 20);


INSERT INTO Subjects_Teachers (SubjectsID, TeacherUsersID) VALUES
(1, 21),
(2, 22),
(3, 23),
(4, 24),
(5, 25),
(6, 26),
(7, 27),
(8, 28),
(9, 29),
(10, 30);

-- STUDENT
CREATE ROLE Student;

-- grant roles --
GRANT SELECT ON "user" TO Student;
GRANT SELECT ON Student TO Student;
GRANT SELECT ON Attendance TO Student;
GRANT SELECT ON Grade TO Student;
GRANT SELECT ON Lesson TO Student;
GRANT SELECT ON Address TO Student;
GRANT SELECT ON Teacher TO Student;
GRANT SELECT ON Class TO Student;
GRANT SELECT ON Subject TO Student;
GRANT SELECT ON Subjects_Teachers TO Student;
GRANT SELECT ON Classroom TO Student;

GRANT UPDATE ON "user" TO Student;
GRANT UPDATE ON Address TO Student;

-- RLS --
ALTER TABLE "user" ENABLE ROW LEVEL SECURITY;
ALTER TABLE Student ENABLE ROW LEVEL SECURITY;
ALTER TABLE Attendance ENABLE ROW LEVEL SECURITY;
ALTER TABLE Grade ENABLE ROW LEVEL SECURITY;
ALTER TABLE Lesson ENABLE ROW LEVEL SECURITY;

ALTER TABLE Address ENABLE ROW LEVEL SECURITY;

--- polices ---
-- SELECT
CREATE POLICY select_self_user ON "user"
FOR SELECT TO Student
USING (Login = current_user);

CREATE POLICY select_student_self ON Student FOR SELECT TO Student
USING (UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER));

CREATE POLICY select_student_lesson ON Lesson
FOR SELECT TO Student
USING (ClassID = (SELECT ClassID FROM Student WHERE UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)));

CREATE POLICY select_student_grade ON Grade FOR SELECT TO Student
USING (StudentUsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER));

CREATE POLICY select_student_attendace ON Attendance FOR SELECT TO Student
USING (StudentUsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER));

CREATE POLICY select_student_address ON Address
FOR SELECT TO Student
USING (id = (SELECT AddressID FROM Student WHERE UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)));

-- UPDATE
CREATE POLICY update_student ON "user" FOR UPDATE TO Student
USING (Login = current_user);

CREATE POLICY update_student_address ON Address
FOR UPDATE TO Student
USING (id = (SELECT AddressID FROM Student WHERE UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)));

CREATE USER anowak_student WITH PASSWORD 'pass';
GRANT Student TO anowak_student;

-- TEACHER --
CREATE ROLE Teacher;

-- GRANTS
GRANT SELECT, UPDATE ON "user" To Teacher;
GRANT SELECT ON Student TO Teacher;
GRANT SELECT ON Subject TO Teacher;
GRANT SELECT ON CLASS TO Teacher;
GRANT SELECT ON Classroom TO Teacher;
GRANT SELECT, UPDATE ON Address TO Teacher;
GRANT SELECT ON Teacher TO Teacher;
GRANT SELECT ON Subjects_Teachers TO Teacher;
GRANT ALL ON Grade TO Teacher;
GRANT ALL ON Lesson TO Teacher;
GRANT ALL ON Attendance TO Teacher;


-- GRANT USAGE ON SEQUENCE grade_id_seq TO Teacher;

--- RLS
ALTER TABLE Teacher ENABLE ROW LEVEL SECURITY;
ALTER TABLE Grade ENABLE ROW LEVEL SECURITY;
ALTER TABLE Lesson ENABLE ROW LEVEL SECURITY;
ALTER TABLE Attendance ENABLE ROW LEVEL SECURITY;

-- POLICIES

CREATE POLICY select_self_user_teacher ON "user"
FOR SELECT TO Teacher
USING (Login = current_user);

CREATE POLICY select_students_taught_by_teacher ON Student
FOR SELECT TO Teacher
USING (
     Student.ClassID = (
        SELECT c.ID
        FROM Class c
        JOIN Lesson l ON l.ClassID = c.ID
        WHERE l.TeacherUsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)
    )
);

CREATE POLICY select_teacher_self ON Teacher
    FOR SELECT TO Teacher
    USING (usersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER));

CREATE POLICY update_teacher_self ON Teacher
    FOR UPDATE TO Teacher
    USING (UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER));

CREATE POLICY update_teacher_address ON Address
    FOR UPDATE TO Teacher
    USING (ID = (SELECT AddressID FROM Teacher WHERE UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)));

CREATE POLICY insert_grade_policy ON Grade
    FOR INSERT TO Teacher;

CREATE POLICY modify_grade_policy ON Grade
FOR ALL TO Teacher
USING (TeacherUsersID = (SELECT UsersID FROM Teacher WHERE UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)));

CREATE POLICY insert_lesson_policy ON Lesson
    FOR INSERT TO Teacher;

CREATE POLICY modify_lesson_policy ON Lesson
    FOR ALL TO Teacher
    USING (TeacherUsersID = (SELECT UsersID FROM Teacher WHERE UsersID = (SELECT ID FROM "user" WHERE Login = CURRENT_USER)));

CREATE POLICY insert_attendance_policy ON Attendance
    FOR INSERT TO Teacher;

CREATE POLICY modify_attendance_policy ON Attendance
    FOR ALL TO Teacher
    USING (lessonsid = (SELECT id
        FROM Lesson
        JOIN Teacher ON Lesson.TeacherUsersID = Teacher.UsersID
        WHERE Lesson.ID = Attendance.LessonsID
        AND Teacher.UsersID = (SELECT UsersID FROM "user" WHERE Login = CURRENT_USER)));


CREATE USER tkowalczyk_teacher WITH PASSWORD 'pass';
GRANT Teacher TO tkowalczyk_teacher;

--- ADMIN ---
CREATE USER jkowalski WITH PASSWORD 'admin';
ALTER USER jkowalski WITH SUPERUSER;

