package pl.electronicgradebook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.electronicgradebook.dto.JwtResultDto;
import pl.electronicgradebook.dto.LoginTO;
import pl.electronicgradebook.dto.SignUpDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.exceptions.AppException;
import pl.electronicgradebook.mappers.UserMapper;
import pl.electronicgradebook.model.Role;
import pl.electronicgradebook.model.User;
import pl.electronicgradebook.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final UserAuthenticationProvider userAuthenticationProvider;

    public JwtResultDto login(LoginTO credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        if (!credentialsDto.getPassword().equals(user.getPassword())) {
            return JwtResultDto.builder().success(false).build();
        }

        String token = userAuthenticationProvider.createToken(user);
        return JwtResultDto.builder().accessToken(token).success(true).build();
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
            user.setRole(Role.STUDENT);

            User savedUser = userRepository.save(user);

            return userMapper.toUserDtoWithRoles(savedUser);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
    }
}
