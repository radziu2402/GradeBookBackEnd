package pl.electronicgradebook.service;

import pl.electronicgradebook.dto.GradeDTO;
import pl.electronicgradebook.dto.UserDto;

import java.util.List;

public interface StudentService {
    List<GradeDTO> getGradesByStudentId(UserDto userDto);
}
