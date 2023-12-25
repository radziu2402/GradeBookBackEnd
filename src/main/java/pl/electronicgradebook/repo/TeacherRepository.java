package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}