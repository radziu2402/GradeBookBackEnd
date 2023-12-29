package pl.electronicgradebook.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaughtStudentsDto {
    List<StudentDto> students;

    @JsonIgnore
    boolean success;
}
