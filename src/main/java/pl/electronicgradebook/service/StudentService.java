package pl.electronicgradebook.service;

import pl.electronicgradebook.dto.*;

import java.util.List;

public interface StudentService {
    ClassDTO getStudentClass(UserDto userDto);
    List<SubjectsTeacherDTO> getAllTeachers();
    List<GradeDTO> getGradesByStudentId(UserDto userDto);
    List<AttendanceDTO> getAttendancesByStudentId(UserDto userDto);

}
