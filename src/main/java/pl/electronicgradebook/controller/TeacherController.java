package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.api.TeacherApi;
import pl.electronicgradebook.dto.TaughtStudentsDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.service.TeacherService;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherController implements TeacherApi  {

    private final TeacherService teacherService;

    @Override
    public ResponseEntity<Object> getStudents(UserDto userDto) {
        TaughtStudentsDto taughtStudents = teacherService.getStudentsLearnedByTeacher(userDto);
        if (taughtStudents.isSuccess()) {
            return new ResponseEntity<>(taughtStudents, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }
}
