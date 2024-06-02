package it.cgmconsulting.solution.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.cgmconsulting.solution.entity.Inventory;
import it.cgmconsulting.solution.payload.response.FilmRentableResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.cgmconsulting.solution.entity.Rental;
import it.cgmconsulting.solution.entity.RentalId;
import it.cgmconsulting.solution.payload.response.CustomerStoreResponse;
import it.cgmconsulting.solution.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.solution.payload.response.FilmRentResponse;

@Repository
public interface RentalRepository extends JpaRepository<Rental, RentalId> {

    @Query("SELECT new it.cgmconsulting.solution.payload.response.CustomerStoreResponse(" +
        "s.storeName, " +
        "COUNT(DISTINCT r.rentalId.customer.customerId)" +
         ") " +
        "FROM Store s " +
        "LEFT JOIN Inventory i ON i.store.storeId = s.storeId " +
        "LEFT JOIN Rental r ON i.inventoryId = r.rentalId.inventory.inventoryId " +
        "LEFT JOIN Customer c ON c.customerId = r.rentalId.customer.customerId " +
        "WHERE s.storeName = :storeName")
    CustomerStoreResponse getCustomersByStoreName(@Param("storeName") String storeName);


    @Query("SELECT COUNT(r) FROM Rental r " +
            "WHERE r.rentalId.inventory.store.storeId = :storeId " +
            "AND (r.rentalId.rentalDate BETWEEN :start AND :end)")
    Long countRentalsInDateRangeByStore(@Param("storeId") long storeId, @Param("start") Date start, @Param("end") Date end);


    @Query("SELECT DISTINCT new it.cgmconsulting.solution.payload.response.FilmRentResponse(" +
            "r.rentalId.inventory.film.filmId, " +
            "r.rentalId.inventory.film.title, " +
            "r.rentalId.inventory.store.storeName" +
            ")" +
            "FROM Rental r " +
            "WHERE r.rentalId.customer.customerId = :customerId")
    List<FilmRentResponse> getAllFilmsRentByOneCustomer(@Param("customerId") long customerId);


    @Query("SELECT r FROM Rental r " +
            "WHERE r.rentalId.inventory.inventoryId = :inventoryId " +
            "AND r.rentalReturn IS NULL ")
    Optional<Rental> getRentalByInventory(@Param("inventoryId") long inventoryId);


    @Query(value="SELECT new it.cgmconsulting.solution.payload.response.FilmMaxRentResponse("
        + "f.filmId, "
        + "f.title,"
        + "COUNT(f.filmId) AS tot"
        + ") "
        + "FROM Rental r "
        + "INNER JOIN Inventory i ON i.inventoryId = r.rentalId.inventory.inventoryId "
        + "INNER JOIN Film f ON f.filmId = i.film.filmId "
        + "GROUP BY f.filmId, f.title "
        + "ORDER BY tot DESC"
     )
    List<FilmMaxRentResponse> findFilmWithMaxNumberOfRent();

    @Query("SELECT new it.cgmconsulting.solution.payload.response.FilmRentableResponse(" +
            "f.title, " +
            "s.storeName, " +
            "COUNT(i), " +
            "(COUNT(i) - COUNT(r))) " +
            "FROM Inventory i " +
            "JOIN i.film f " +
            "JOIN i.store s " +
            "LEFT JOIN Rental r ON r.rentalId.inventory = i AND r.rentalReturn IS NULL " +
            "WHERE f.title = :title " +
            "GROUP BY f.title, s.storeName")
    List<FilmRentableResponse> getRentableFilmsByTitle(@Param("title") String title);

}
