package pl.electronicgradebook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.electronicgradebook.api.UserApi;
import pl.electronicgradebook.dto.ProfileDataDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.service.UserService;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Object> getProfileData(UserDto userDto) {
        ProfileDataDto profileData = userService.getProfileData(userDto);
        if (!profileData.isSuccess()) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(profileData);
    }

    @Override
    public ResponseEntity<Object> updateProfileData(UserDto userDto, ProfileDataDto profileDataDto) {
        ProfileDataDto updatedProfileData = userService.updateProfileData(userDto, profileDataDto);
        if (!updatedProfileData.isSuccess()) {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(updatedProfileData);
    }
}
