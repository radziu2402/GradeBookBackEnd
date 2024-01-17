package pl.electronicgradebook.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.electronicgradebook.dto.UserDto;


@RequestMapping("api/v1/")
public interface StudentApi {

    @GetMapping("grades")
    ResponseEntity<Object> getGrades(@AuthenticationPrincipal UserDto userDto);

    @GetMapping("grades/{studentId}")
    ResponseEntity<Object> getGrades(@AuthenticationPrincipal UserDto userDto,
                                     @PathVariable("studentId") Integer studentId);

    @GetMapping("attendances")
    ResponseEntity<Object> getAttendance(@AuthenticationPrincipal UserDto userDto);

    @GetMapping("teachers")
    ResponseEntity<Object> getTeachers();

    @GetMapping("myclass")
    ResponseEntity<Object> getStudentClass(@AuthenticationPrincipal UserDto userDto);
}
