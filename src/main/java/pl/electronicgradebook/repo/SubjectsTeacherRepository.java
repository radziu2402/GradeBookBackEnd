package pl.electronicgradebook.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.electronicgradebook.model.Subject;
import pl.electronicgradebook.model.SubjectsTeacher;
import pl.electronicgradebook.model.SubjectsTeacherId;

import java.util.List;


public interface SubjectsTeacherRepository extends JpaRepository<SubjectsTeacher, SubjectsTeacherId> {

    @Query("""
            SELECT s FROM Subject s WHERE s.id IN (
            SELECT st.subjectsid.id FROM SubjectsTeacher st WHERE st.id.teacherusersid = :teacherId)
            """)
    List<Subject> findByTeacherUserId(@Param("teacherId") Integer teacherusersid);
}