package pl.electronicgradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDTO {
    private String date;
    private Boolean present;
    private String subjectName;
    private String teacherFirstName;
    private String teacherLastName;
}
