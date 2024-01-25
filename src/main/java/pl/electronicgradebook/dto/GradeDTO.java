package pl.electronicgradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeDTO {
    private Integer id;
    private BigDecimal gradeValue;
    private String dateOfModification;
    private String subjectName;
    private String teacherFirstName;
    private String teacherLastName;
}
