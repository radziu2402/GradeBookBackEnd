package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.api.StudentApi;
import pl.electronicgradebook.dto.*;
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
}
