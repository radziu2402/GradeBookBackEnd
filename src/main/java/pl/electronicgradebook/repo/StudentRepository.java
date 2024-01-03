package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Student;
import pl.electronicgradebook.model.User;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByUser(User user);
}