package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.entity.Genre;
import it.cgmconsulting.solution.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Optional<Genre> findById(long genreId){
        return genreRepository.findById(genreId);
    }
}
