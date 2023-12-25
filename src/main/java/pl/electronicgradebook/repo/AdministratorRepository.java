package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}