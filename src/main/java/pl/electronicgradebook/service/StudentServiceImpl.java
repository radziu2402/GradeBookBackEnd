package pl.electronicgradebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.electronicgradebook.dto.GradeDTO;
import pl.electronicgradebook.dto.UserDto;
import pl.electronicgradebook.exceptions.AppException;
import pl.electronicgradebook.model.Grade;
import pl.electronicgradebook.model.User;
import pl.electronicgradebook.repo.GradeRepository;
import pl.electronicgradebook.repo.UserRepository;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;

    public List<GradeDTO> getGradesByStudentId(UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));
        List<Grade> gradesByStudentusersid = gradeRepository.findByStudentusersidId(user.getId());

        return mapGradesToDTO(gradesByStudentusersid);
    }

    private List<GradeDTO> mapGradesToDTO(List<Grade> grades) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return grades.stream()
                .map(grade -> GradeDTO.builder()
                        .gradeValue(grade.getGradeValue())
                        .dateOfModification(grade.getDateOfModification().atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter))
                        .subjectName(grade.getSubjectsid().getName())
                        .teacherFirstName(grade.getTeacherusersid().getFirstName())
                        .teacherLastName(grade.getTeacherusersid().getLastName())
                        .build())
                .collect(Collectors.toList());
    }
}
