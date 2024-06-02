package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.entity.Film;
import it.cgmconsulting.solution.entity.Inventory;
import it.cgmconsulting.solution.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void save(Inventory inventory){
        inventoryRepository.save(inventory);
    }

    public Optional<Inventory> findById(long inventoryId){
        return inventoryRepository.findById(inventoryId);
    }

    public List<Inventory> findByFilm(Film film){
        return inventoryRepository.findByFilm(film);
    }

}
