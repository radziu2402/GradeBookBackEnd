package pl.electronicgradebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "administrator")
public class Administrator {
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

}