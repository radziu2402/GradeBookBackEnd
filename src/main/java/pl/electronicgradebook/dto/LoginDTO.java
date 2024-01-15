package pl.electronicgradebook.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LoginDTO {

    private String login;

    private String password;
}
