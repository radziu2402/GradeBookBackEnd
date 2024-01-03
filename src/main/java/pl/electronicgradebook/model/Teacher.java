package pl.electronicgradebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "teacher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher {
    @Id
    @Column(name = "usersid", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usersid", nullable = false)
    private User user;

    @Column(name = "\"first name\"", nullable = false, length = 50)
    private String firstName;

    @Column(name = "\"last name\"", nullable = false, length = 50)
    private String lastName;

    @Column(name = "\"date of birth\"", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "\"academic degree\"", length = 50)
    private String academicDegree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classid")
    private Class classid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "addressid", nullable = false)
    private Address addressid;

}