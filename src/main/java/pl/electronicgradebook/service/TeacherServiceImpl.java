package pl.electronicgradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.electronicgradebook.dto.StudentDto;
import pl.electronicgradebook.dto.TaughtStudentsDto;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.model.Student;
import pl.electronicgradebook.repo.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherServiceImpl implements TeacherService {

    private final StudentRepository studentRepository;

    @Override
    public TaughtStudentsDto getStudentsLearnedByTeacher(UserDto userDto) {
        // TODO: ADD logic using @Query or some View
        List<StudentDto> studentsTaughtByTeacher = studentRepository.findAll().stream().map(this::mapToStudentDto).toList();

        return TaughtStudentsDto.builder().students(studentsTaughtByTeacher).success(true).build();
    }

    private StudentDto mapToStudentDto(Student student) {
        return StudentDto.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .className(student.getClassid().getName())
                .build();
    }
}
