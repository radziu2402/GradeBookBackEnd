package pl.electronicgradebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"grade value\"", nullable = false, precision = 3, scale = 2)
    private BigDecimal gradeValue;

    @Column(name = "\"date of modification\"", nullable = false)
    private LocalDate dateOfModification;

}