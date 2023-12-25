package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}