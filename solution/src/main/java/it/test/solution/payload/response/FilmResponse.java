package it.cgmconsulting.solution.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FilmResponse {

    private long filmId;
    private String title;
    private String description;
    private short releaseYear;
    private String languageName;

    public FilmResponse(long filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmResponse that = (FilmResponse) o;
        return filmId == that.filmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }

}
