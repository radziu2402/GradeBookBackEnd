package pl.electronicgradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDTO {
    private String tutorFirstName;
    private String tutorLastName;
    private String academicDegree;
    private List<StudentDto> inClassStudents;
}
