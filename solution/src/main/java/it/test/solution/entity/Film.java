package it.cgmconsulting.solution.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private short releaseYear;

    @ManyToOne
    @JoinColumn(name = "languageId")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "genreId")
    private Genre genre;

    public Film(long filmId) {
        this.filmId = filmId;
    }

    public Film(String title, String description, short releaseYear, Language language, Genre genre) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return filmId == film.filmId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }
}
