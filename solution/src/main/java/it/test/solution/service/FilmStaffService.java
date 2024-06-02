package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.payload.response.FilmResponse;
import it.cgmconsulting.solution.repository.FilmStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmStaffService {

    private final FilmStaffRepository filmStaffRepository;

    public List<FilmResponse> getFilmsByStaffIds(List<Long> staffIds, long countActors){
        return filmStaffRepository.getFilmsByStaffIds(staffIds, countActors);
    }

    public List<Long> getActorsIds(List<Long> staffIds){
        return filmStaffRepository.getActorsIds(staffIds);
    }
}
