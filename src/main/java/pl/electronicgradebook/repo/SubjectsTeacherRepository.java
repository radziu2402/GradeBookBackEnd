package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronicgradebook.model.SubjectsTeacher;
import pl.electronicgradebook.model.SubjectsTeacherId;

public interface SubjectsTeacherRepository extends JpaRepository<SubjectsTeacher, SubjectsTeacherId> {
}