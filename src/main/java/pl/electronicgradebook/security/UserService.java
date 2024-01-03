package pl.electronicgradebook.security;

import pl.electronicgradebook.dto.JwtResultDto;
import pl.electronicgradebook.dto.LoginTO;
import pl.electronicgradebook.dto.UserDto;

public interface UserService {

    JwtResultDto login(LoginTO credentialsDto);

    UserDto findUserByLogin(String login);
}
