package pl.electronicgradebook.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.electronicgradebook.dto.ProfileDataDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.Role;
import pl.electronicgradebook.model.Student;
import pl.electronicgradebook.model.Teacher;
import pl.electronicgradebook.model.User;
import pl.electronicgradebook.repo.TeacherRepository;
import pl.electronicgradebook.repo.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherDataProducer implements UserDataProducer {

    private final UserRepository userRepository;

    private final TeacherRepository teacherRepository;

    @Override
    public ProfileDataDto buildUserData(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isEmpty()) {
            return ProfileDataDto.builder().success(false).build();
        }
        User user = userOptional.get();

        Optional<Teacher> teacherOptional = teacherRepository.findById(userDto.getId());
        if (teacherOptional.isEmpty()) {
            return ProfileDataDto.builder().success(false).build();
        }
        Teacher teacher = teacherOptional.get();

        return buildProfileDataDto(user, teacher);
    }

    @Override
    public boolean supports(Role role) {
        return role == Role.TEACHER;
    }

    private ProfileDataDto buildProfileDataDto(User user, Teacher student) {
        return ProfileDataDto.builder()
                .firstName(student.getFirstName())
                .secondName(student.getLastName())
                .email(user.getEmail())
                .login(user.getLogin())
                .dateOfBirth(String.valueOf(student.getDateOfBirth()))
                .password(user.getPassword())
                .success(true)
                .build();
    }
}
