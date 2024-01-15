package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.SubjectsTeacher;
import pl.electronicgradebook.model.SubjectsTeacherId;

import java.util.List;

public interface SubjectsTeacherRepository extends JpaRepository<SubjectsTeacher, SubjectsTeacherId> {
}