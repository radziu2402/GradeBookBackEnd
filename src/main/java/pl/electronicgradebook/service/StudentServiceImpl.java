package pl.electronicgradebook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.TypeRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.electronicgradebook.dto.*;
import pl.electronicgradebook.exceptions.AppException;
import pl.electronicgradebook.factory.UserDataProducerFactory;
import pl.electronicgradebook.model.*;
import pl.electronicgradebook.repo.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final SubjectsTeacherRepository subjectsTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public ClassDTO getStudentClass(UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));
        Student student = studentRepository.findByUser(user);
        Teacher tutor = teacherRepository.findByClassid(student.getClassid());
        List<Student> allStudents = studentRepository.findAll();
        List<Student> inClassStudents = allStudents.stream()
                .filter(s -> s.getClassid().equals(student.getClassid())).toList();
        return mapStudentsToDTO(inClassStudents, tutor);
    }

    @Override
    public List<SubjectsTeacherDTO> getAllTeachers() {
        List<SubjectsTeacher> subjectsTeacherList = subjectsTeacherRepository.findAll();
        List<Teacher> teacherList = teacherRepository.findAll();
        return mapSubjectsTeachersToDTO(subjectsTeacherList, teacherList);
    }


    @Override
    public List<GradeDTO> getGradesByStudentId(UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));
        List<Grade> gradesByStudentUsersId = gradeRepository.findByStudentusersidId(user.getId());

        return mapGradesToDTO(gradesByStudentUsersId);
    }

    @Override
    public List<GradeDTO> getGradesByStudentId(final UserDto userDto, final Integer studentId) {
        userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        List<Grade> gradesByStudentUsersId = gradeRepository.findByStudentusersidId(studentId);
        return mapGradesToDTO(gradesByStudentUsersId);
    }

    @Override
    public List<AttendanceDTO> getAttendancesByStudentId(UserDto userDto) {
        User user = userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));
        List<Attendance> attendancesByStudentUsersId = attendanceRepository.findByStudentusersidId(user.getId());

        return mapAttendancesToDTO(attendancesByStudentUsersId);
    }

    @Override
    @Transactional
    public void deleteGradeById(Integer id) {
        this.gradeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public GradeDTO addGrade(final UserDto userDto, final NewGradeDto gradeDto) {
        userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.BAD_REQUEST));

        Teacher teacher = teacherRepository.findById(userDto.getId()).get();
        Student student = studentRepository.findById(gradeDto.getStudentId()).get();

        List<Subject> subjects = subjectsTeacherRepository.findByTeacherUserId(teacher.getId());

        Grade grade = Grade.builder()
                .gradeValue(gradeDto.getGradeValue())
                .dateOfModification(LocalDate.now())
                .studentusersid(student)
                .subjectsid(subjects.get(0))
                .teacherusersid(teacher).build();

        return mapGradesToDTO(gradeRepository.save(grade));
    }

    private ClassDTO mapStudentsToDTO(List<Student> inClassStudents, Teacher tutor) {
        return ClassDTO.builder()
                .tutorFirstName(tutor.getFirstName())
                .tutorLastName(tutor.getLastName())
                .academicDegree(tutor.getAcademicDegree())
                .inClassStudents(inClassStudents.stream()
                        .map(student -> StudentDto.builder()
                                .firstName(student.getFirstName())
                                .lastName(student.getLastName())
                                .className(student.getClassid().getName())
                                .build())
                        .toList()
                )
                .build();
    }

    private List<SubjectsTeacherDTO> mapSubjectsTeachersToDTO(List<SubjectsTeacher> subjectsTeacherList, List<Teacher> teacherList) {
        return subjectsTeacherList.stream()
                .map(subjectsTeacher -> SubjectsTeacherDTO.builder()
                        .subjectName(subjectsTeacher.getSubjectsid().getName())
                        .teacherFirstName(teacherList.stream()
                                .filter(teacher -> teacher.getId().equals(subjectsTeacher.getId().getTeacherusersid()))
                                .findFirst()
                                .map(Teacher::getFirstName)
                                .orElse("Brak danych"))
                        .teacherLastName(teacherList.stream()
                                .filter(teacher -> teacher.getId().equals(subjectsTeacher.getId().getTeacherusersid()))
                                .findFirst()
                                .map(Teacher::getLastName)
                                .orElse("Brak danych"))
                        .academicDegree(teacherList.stream()
                                .filter(teacher -> teacher.getId().equals(subjectsTeacher.getId().getTeacherusersid()))
                                .findFirst()
                                .map(Teacher::getAcademicDegree)
                                .orElse("Brak danych"))
                        .build())
                .collect(Collectors.toList());
    }

    private List<AttendanceDTO> mapAttendancesToDTO(List<Attendance> attendances) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return attendances.stream()
                .map(attendance -> AttendanceDTO.builder()
                        .date(attendance.getDate().atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter))
                        .present(attendance.getPresent())
                        .subjectName(attendance.getLessonsid().getSubjectsid().getName())
                        .teacherFirstName(attendance.getLessonsid().getTeacherusersid().getFirstName())
                        .teacherLastName(attendance.getLessonsid().getTeacherusersid().getLastName())
                        .build())
                .collect(Collectors.toList());
    }

    private List<GradeDTO> mapGradesToDTO(List<Grade> grades) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return grades.stream()
                .map(grade -> GradeDTO.builder()
                        .id(grade.getId())
                        .gradeValue(grade.getGradeValue())
                        .dateOfModification(grade.getDateOfModification().atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter))
                        .subjectName(grade.getSubjectsid().getName())
                        .teacherFirstName(grade.getTeacherusersid().getFirstName())
                        .teacherLastName(grade.getTeacherusersid().getLastName())
                        .build())
                .collect(Collectors.toList());
    }

    private GradeDTO mapGradesToDTO(Grade grade) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return GradeDTO.builder()
                .id(grade.getId())
                .gradeValue(grade.getGradeValue())
                .dateOfModification(grade.getDateOfModification().atStartOfDay().atOffset(ZoneOffset.UTC).format(formatter))
                .subjectName(grade.getSubjectsid().getName())
                .teacherFirstName(grade.getTeacherusersid().getFirstName())
                .teacherLastName(grade.getTeacherusersid().getLastName())
                .build();
    }
}
