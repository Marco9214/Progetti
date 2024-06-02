package it.cgmconsulting.solution.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FilmActorsResponse {

    private long filmId;
    private String lastname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorsResponse that = (FilmActorsResponse) o;
        return filmId == that.filmId && lastname.equals(that.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, lastname);
    }
}
