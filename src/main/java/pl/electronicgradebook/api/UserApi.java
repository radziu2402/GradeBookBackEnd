package pl.electronicgradebook.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.electronicgradebook.dto.ProfileDataDto;
import pl.electronicgradebook.dto.UserDto;

@RequestMapping("api/v1/")
public interface UserApi {


    @GetMapping("profile")
    ResponseEntity<Object> getProfileData(@AuthenticationPrincipal UserDto userDto);

    @PostMapping("profile")
    ResponseEntity<Object> updateProfileData(@AuthenticationPrincipal UserDto userDto,
                                             @RequestBody ProfileDataDto profileDataDto);
}
