package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.api.StudentApi;
import pl.electronicgradebook.dto.GradeDTO;
import pl.electronicgradebook.dto.TaughtStudentsDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.Grade;
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
}
