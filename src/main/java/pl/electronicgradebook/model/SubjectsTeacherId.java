package pl.electronicgradebook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SubjectsTeacherId implements Serializable {
    private static final long serialVersionUID = -5227553330995461756L;
    @Column(name = "subjectsid", nullable = false)
    private Integer subjectsid;

    @Column(name = "teacherusersid", nullable = false)
    private Integer teacherusersid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubjectsTeacherId entity = (SubjectsTeacherId) o;
        return Objects.equals(this.teacherusersid, entity.teacherusersid) &&
                Objects.equals(this.subjectsid, entity.subjectsid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherusersid, subjectsid);
    }

}