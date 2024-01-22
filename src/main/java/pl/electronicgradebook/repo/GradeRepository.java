package pl.electronicgradebook.repo;

import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.electronicgradebook.model.Grade;

import java.util.List;


public interface GradeRepository extends JpaRepository<Grade, Integer> {

    @Query("""
                SELECT g FROM Grade g
                WHERE g.teacherusersid.id = :teacherId\s
                AND g.studentusersid.id = :studentId
            """)
    List<Grade> findGradesThatGivenByTeacher(@PathParam("studentId") Integer studentId,
                                             @PathParam("teacherId") Integer teacherId);

    @Query("SELECT g FROM Grade g WHERE  g.studentusersid.id = :studentId")
    List<Grade> findGradesThatBelongToStudent(@PathParam("studentId") Integer studentId);
}