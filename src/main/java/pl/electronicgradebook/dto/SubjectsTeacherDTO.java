package pl.electronicgradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectsTeacherDTO {
    private String subjectName;
    private String teacherFirstName;
    private String teacherLastName;
    private String academicDegree;
}

