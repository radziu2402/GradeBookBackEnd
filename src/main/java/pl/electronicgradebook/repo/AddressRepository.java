package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}