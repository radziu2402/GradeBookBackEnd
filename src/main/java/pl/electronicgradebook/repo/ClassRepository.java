package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Class;

public interface ClassRepository extends JpaRepository<Class, Integer> {
}