package pl.electronicgradebook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.electronicgradebook.dto.CredentialsDto;
import pl.electronicgradebook.dto.SignUpDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.exceptions.AppException;
import pl.electronicgradebook.mappers.UserMapper;
import pl.electronicgradebook.model.User;
import pl.electronicgradebook.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(credentialsDto.password(), user.getPassword())) {
            return userMapper.toUserDtoWithRoles(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUserByLogin = userRepository.findByLogin(userDto.login());
        Optional<User> optionalUserByEmail = userRepository.findByEmail(userDto.email());

        if (optionalUserByLogin.isPresent()) {
            throw new AppException("Account with this login already exists", HttpStatus.BAD_REQUEST);
        } else if (optionalUserByEmail.isPresent()) {
            throw new AppException("Account with this email already exists", HttpStatus.BAD_REQUEST);
        } else {
            User user = userMapper.signUpToUser(userDto);
            user.setPassword(passwordEncoder.encode(userDto.password()));
            user.setRole("student");

            User savedUser = userRepository.save(user);

            return userMapper.toUserDtoWithRoles(savedUser);
        }
    }

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDtoWithRoles(user);
    }
}
