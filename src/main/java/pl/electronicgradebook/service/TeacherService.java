package pl.electronicgradebook.service;

import pl.electronicgradebook.dto.TaughtStudentsDto;
import pl.electronicgradebook.dto.UserDto;

public interface TeacherService {
    TaughtStudentsDto getStudentsLearnedByTeacher(UserDto userDto);
}
