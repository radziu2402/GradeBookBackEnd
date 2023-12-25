package pl.electronicgradebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "login", nullable = false, length = 150)
    private String login;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "password", nullable = false, length = 80)
    private String password;

}