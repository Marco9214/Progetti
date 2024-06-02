package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public boolean existsById(long storeId){
        return storeRepository.existsById(storeId);
    }

    public boolean existsByStoreName(String storeName){
        return storeRepository.existsByStoreName(storeName);
    }
}
