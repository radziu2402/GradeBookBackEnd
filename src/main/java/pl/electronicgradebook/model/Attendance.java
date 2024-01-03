package pl.electronicgradebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "attendance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"date\"", nullable = false)
    private LocalDate date;

    @Column(name = "present", nullable = false)
    private Boolean present = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lessonsid", nullable = false)
    private Lesson lessonsid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "studentusersid", nullable = false)
    private Student studentusersid;

}