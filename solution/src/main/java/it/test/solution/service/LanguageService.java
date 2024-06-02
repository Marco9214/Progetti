package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.entity.Language;
import it.cgmconsulting.solution.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Optional<Language> findById(long languageId){
        return languageRepository.findById(languageId);
    }
}
