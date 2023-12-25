package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
}