package it.cgmconsulting.solution.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FilmStaffId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmStaffId that = (FilmStaffId) o;
        return film.equals(that.film) && staff.equals(that.staff) && role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, staff, role);
    }
}
