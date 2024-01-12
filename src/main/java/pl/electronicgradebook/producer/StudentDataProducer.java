package pl.electronicgradebook.producer;

import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.electronicgradebook.dto.ProfileDataDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.Role;
import pl.electronicgradebook.model.Student;
import pl.electronicgradebook.model.User;
import pl.electronicgradebook.repo.StudentRepository;
import pl.electronicgradebook.repo.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentDataProducer implements UserDataProducer {

    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    @Override
    public ProfileDataDto buildUserData(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isEmpty()) {
            return ProfileDataDto.builder().success(false).build();
        }
        User user = userOptional.get();

        Optional<Student> studentOptional = studentRepository.findById(userDto.getId());
        if (studentOptional.isEmpty()) {
            return ProfileDataDto.builder().success(false).build();
        }
        Student student = studentOptional.get();

        return buildProfileDataDto(user, student);
    }

    @Override
    public boolean supports(Role role) {
        return role == Role.STUDENT;
    }

    private ProfileDataDto buildProfileDataDto(User user, Student student) {
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
