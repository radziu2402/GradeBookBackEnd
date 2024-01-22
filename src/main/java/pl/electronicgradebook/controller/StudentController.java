package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.api.StudentApi;
import pl.electronicgradebook.dto.AttendanceDTO;
import pl.electronicgradebook.dto.ClassDTO;
import pl.electronicgradebook.dto.GradeDTO;
import pl.electronicgradebook.dto.NewGradeDto;
import pl.electronicgradebook.dto.SubjectsTeacherDTO;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.service.StudentServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController implements StudentApi {

    private final StudentServiceImpl studentService;

    @Override
    public ResponseEntity<Object> getGrades(UserDto userDto) {
        List<GradeDTO> gradesByStudentId = studentService.getGradesByStudentId(userDto);
        return new ResponseEntity<>(gradesByStudentId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getGrades(final UserDto userDto, final Integer studentId) {
        return new ResponseEntity<>(studentService.getGradesByStudentId(userDto, studentId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAttendance(UserDto userDto) {
        List<AttendanceDTO> attendancesByStudentId = studentService.getAttendancesByStudentId(userDto);
        return new ResponseEntity<>(attendancesByStudentId, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getTeachers() {
        List<SubjectsTeacherDTO> allTeachers = studentService.getAllTeachers();
        return new ResponseEntity<>(allTeachers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getStudentClass(UserDto userDto) {
        ClassDTO studentClass = studentService.getStudentClass(userDto);
        return new ResponseEntity<>(studentClass, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteGrade(UserDto userDto, Integer gradeId) {
        studentService.deleteGradeById(gradeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> addGrade(final UserDto userDto, final NewGradeDto newGradeDto) {
        return ResponseEntity.ok(studentService.addGrade(userDto, newGradeDto));
    }
}
