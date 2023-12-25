package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}