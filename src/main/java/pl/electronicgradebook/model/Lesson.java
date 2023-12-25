package pl.electronicgradebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "topic", length = 50)
    private String topic;

    @Column(name = "\"date\"", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classid", nullable = false)
    private Class classid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "classroomid", nullable = false)
    private Classroom classroomid;

}