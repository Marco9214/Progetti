package it.cgmconsulting.solution.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.cgmconsulting.solution.entity.Inventory;
import it.cgmconsulting.solution.payload.response.FilmRentableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.cgmconsulting.solution.entity.Rental;
import it.cgmconsulting.solution.payload.response.CustomerStoreResponse;
import it.cgmconsulting.solution.payload.response.FilmMaxRentResponse;
import it.cgmconsulting.solution.payload.response.FilmRentResponse;
import it.cgmconsulting.solution.repository.RentalRepository;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;

    public void save(Rental rental){
        rentalRepository.save(rental);
    }

    public CustomerStoreResponse getCustomersByStoreName(String storeName){
        return rentalRepository.getCustomersByStoreName(storeName);
    }

    public Long countRentalsInDateRangeByStore(long storeId, Date start, Date end){
        return rentalRepository.countRentalsInDateRangeByStore(storeId, start, end);

    }

    public List<FilmRentResponse> getAllFilmsRentByOneCustomer(long customerId){
        return rentalRepository.getAllFilmsRentByOneCustomer(customerId);
    }

    public Optional<Rental> getRentalByInventory(long inventoryId){
        return rentalRepository.getRentalByInventory(inventoryId);
    }

    public List<FilmMaxRentResponse> findFilmWithMaxNumberOfRent(){
        List<FilmMaxRentResponse> list = rentalRepository.findFilmWithMaxNumberOfRent();
        List<FilmMaxRentResponse> newList = new ArrayList<>();
        long maxRent = list.get(0).getRentCount();
        for(FilmMaxRentResponse f : list){
            if(maxRent == f.getRentCount())
                newList.add(f);
        }
        return newList;
    }

    public List<FilmRentableResponse> getRentableFilmsByTitle(String title){
        return rentalRepository.getRentableFilmsByTitle(title);
    }
}
