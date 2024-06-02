package it.cgmconsulting.solution.repository;

import it.cgmconsulting.solution.entity.Film;
import it.cgmconsulting.solution.payload.response.FilmActorsResponse;
import it.cgmconsulting.solution.payload.response.FilmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT new it.cgmconsulting.solution.payload.response.FilmResponse(" +
            "f.filmId, " +
            "f.title, " +
            "f.description, " +
            "f.releaseYear, " +
            "f.language.languageName" +
            ")" +
            "FROM Film f " +
            "WHERE f.language.languageId = :languageId")
    List<FilmResponse> getFilmByLanguage(@Param("languageId") long languageId);

    @Query("SELECT new it.cgmconsulting.solution.payload.response.FilmActorsResponse(" +
            "f.filmId," +
            "fs.filmStaffId.staff.lastname" +
            ") " +
            "FROM Film f " +
            "JOIN FilmStaff fs on fs.filmStaffId.film.filmId = f.filmId " +
            "WHERE f.filmId IN :ids")
    List<FilmActorsResponse> getFilmActorsByIds(@Param("ids") List<Long> ids);

    Optional<Film> findByTitle(String title);

}
