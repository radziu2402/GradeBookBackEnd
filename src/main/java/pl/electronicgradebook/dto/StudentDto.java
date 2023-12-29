package pl.electronicgradebook.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentDto {

    String firstName;

    String lastName;

    String className;
}
