package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.entity.Film;
import it.cgmconsulting.solution.payload.response.FilmActorsResponse;
import it.cgmconsulting.solution.payload.response.FilmResponse;
import it.cgmconsulting.solution.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public Optional<Film> findById(long filmId){
        return filmRepository.findById(filmId);
    }

    public Optional<Film> findByTitle(String title){
        return filmRepository.findByTitle(title);
    }

    public boolean existsById(long filmId){
        return filmRepository.existsById(filmId);
    }

    public List<FilmResponse> getFilmByLanguage(long languageId){
        return filmRepository.getFilmByLanguage(languageId);
    }
}
