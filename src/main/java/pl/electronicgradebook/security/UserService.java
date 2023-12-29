package pl.electronicgradebook.security;

import pl.electronicgradebook.dto.JwtResultDto;
import pl.electronicgradebook.dto.LoginTO;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.User;

public interface UserService {

    JwtResultDto login(LoginTO credentialsDto);

    UserDto findUserByLogin(String login);
}
