package pl.electronicgradebook.producer;

import pl.electronicgradebook.dto.ProfileDataDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.Role;

public interface UserDataProducer {

    ProfileDataDto buildUserData(UserDto userDto);

    boolean supports(Role role);
}
