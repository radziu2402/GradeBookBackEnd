package pl.electronicgradebook.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.electronicgradebook.dto.LoginTO;
import pl.electronicgradebook.dto.UserDto;

@RequestMapping("api/v1/")
public interface TeacherApi {

    @GetMapping("students")
    ResponseEntity<Object> getStudents(@AuthenticationPrincipal UserDto userDto);
}
