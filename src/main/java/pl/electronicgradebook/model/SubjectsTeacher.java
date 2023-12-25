package pl.electronicgradebook.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subjects_teachers")
public class SubjectsTeacher {
    @EmbeddedId
    private SubjectsTeacherId id;

    @MapsId("subjectsid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subjectsid", nullable = false)
    private Subject subjectsid;

}