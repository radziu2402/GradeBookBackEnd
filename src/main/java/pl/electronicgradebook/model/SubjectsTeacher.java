package pl.electronicgradebook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "subjects_teachers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectsTeacher {
    @EmbeddedId
    private SubjectsTeacherId id;

    @MapsId("subjectsid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subjectsid", nullable = false)
    private Subject subjectsid;

}