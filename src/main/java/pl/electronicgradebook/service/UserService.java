package pl.electronicgradebook.service;

import pl.electronicgradebook.dto.JwtResultDto;
import pl.electronicgradebook.dto.LoginDTO;
import pl.electronicgradebook.dto.ProfileDataDto;
import pl.electronicgradebook.dto.UserDto;

public interface UserService {

    JwtResultDto login(LoginDTO credentialsDto);

    UserDto findUserByLogin(String login);

    ProfileDataDto getProfileData(UserDto userDto);

    ProfileDataDto updateProfileData(UserDto userDto, ProfileDataDto profileDataDto);
}
