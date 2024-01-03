package pl.electronicgradebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDataDto {
    private String login;
    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String dateOfBirth;
}
