package pl.electronicgradebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classroom")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"room name\"", nullable = false, length = 50)
    private String roomName;

}