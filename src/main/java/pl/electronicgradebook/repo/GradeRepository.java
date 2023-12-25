package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}