package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.electronicgradebook.model.Student;
import pl.electronicgradebook.model.User;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByUser(User user);

    @Query("""
            SELECT s FROM Student s WHERE s.classid.id IN (\s
            SELECT c.id FROM Class c \s
            JOIN Lesson l ON l.classid.id = c.id\s
            WHERE l.teacherusersid.user.login = :login)
            """)
    List<Student> findStudentsTaughtByTeacher(@Param("login") String teacherLogin);
}
