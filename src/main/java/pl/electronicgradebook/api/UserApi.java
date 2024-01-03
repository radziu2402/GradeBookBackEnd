package pl.electronicgradebook.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.electronicgradebook.dto.UserDto;

@RequestMapping("api/v1/")
public interface UserApi {


    @GetMapping("profile")
    ResponseEntity<Object> getProfileData(@AuthenticationPrincipal UserDto userDto);
}
