package it.cgmconsulting.solution.repository;

import it.cgmconsulting.solution.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByStoreName(String storeName);
}
