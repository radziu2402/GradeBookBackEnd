package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Grade;
import pl.electronicgradebook.model.Student;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudentusersidId(Integer studentId);
}