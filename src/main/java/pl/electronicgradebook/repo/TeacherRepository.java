package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Class;
import pl.electronicgradebook.model.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByClassid(Class classid);
}