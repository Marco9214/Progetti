package it.cgmconsulting.solution.repository;

import it.cgmconsulting.solution.entity.Film;
import it.cgmconsulting.solution.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findById(long inventoryId);
    List<Inventory> findByFilm(Film film);
}
