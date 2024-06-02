package it.cgmconsulting.solution.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FilmStaff {

    @EmbeddedId
    private FilmStaffId filmStaffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmStaff filmStaff = (FilmStaff) o;
        return filmStaffId.equals(filmStaff.filmStaffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmStaffId);
    }
}
