package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}