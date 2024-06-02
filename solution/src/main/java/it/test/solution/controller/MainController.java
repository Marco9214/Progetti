package it.cgmconsulting.solution.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import it.cgmconsulting.solution.payload.response.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.cgmconsulting.solution.entity.Customer;
import it.cgmconsulting.solution.entity.Film;
import it.cgmconsulting.solution.entity.Genre;
import it.cgmconsulting.solution.entity.Inventory;
import it.cgmconsulting.solution.entity.Language;
import it.cgmconsulting.solution.entity.Rental;
import it.cgmconsulting.solution.entity.RentalId;
import it.cgmconsulting.solution.entity.Store;
import it.cgmconsulting.solution.payload.request.FilmRequest;
import it.cgmconsulting.solution.payload.request.RentalRequest;
import it.cgmconsulting.solution.service.CustomerService;
import it.cgmconsulting.solution.service.FilmService;
import it.cgmconsulting.solution.service.FilmStaffService;
import it.cgmconsulting.solution.service.GenreService;
import it.cgmconsulting.solution.service.InventoryService;
import it.cgmconsulting.solution.service.LanguageService;
import it.cgmconsulting.solution.service.RentalService;
import it.cgmconsulting.solution.service.StoreService;

@RestController
@Validated
@RequiredArgsConstructor
public class MainController {

    private final GenreService genreService;
    private final LanguageService languageService;
    private final CustomerService customerService;
    private final FilmService filmService;
    private final StoreService storeService;
    private final InventoryService inventoryService;
    private final RentalService rentalService;
    private final FilmStaffService filmStaffService;

    /* ---------------- 1 ------------------ */
    @PutMapping("update-film/{filmId}")
    @Transactional
    public ResponseEntity<?> updateFilm(@PathVariable @Min(1) long filmId, @RequestBody @Valid FilmRequest request){

        if(request.getReleaseYear() > LocalDate.now().getYear())
            return new ResponseEntity<>("The release year is in the future", HttpStatus.BAD_REQUEST);

        Optional<Film> film = filmService.findById(filmId);
        if(film.isEmpty())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);

        film.get().setTitle(request.getTitle().trim().toUpperCase());
        film.get().setDescription(request.getDescription());
        film.get().setReleaseYear(request.getReleaseYear());

        Optional<Language> language = languageService.findById(request.getLanguageId());
        if (language.isEmpty()) {
            //Rollback di tutto perché il dato inserito non è corretto
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Language not exists", HttpStatus.NOT_FOUND);
        }
        film.get().setLanguage(language.get());

        Optional<Genre> genre = genreService.findById(request.getGenreId());
        if (genre.isEmpty()) {
            //Rollback di tutto perché il dato inserito non è corretto
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Genre not exists", HttpStatus.NOT_FOUND);
        }
        film.get().setGenre(genre.get());

        return new ResponseEntity<>("Film has been updated", HttpStatus.OK);
    }

    /* ---------------- 2 ------------------ */
    @GetMapping("find-films-by-language/{languageId}")
    public ResponseEntity<?> findFilmsByLanguage(@PathVariable @Min(1) long languageId){
        List<FilmResponse> list = filmService.getFilmByLanguage(languageId);
        if(list.isEmpty())
            return new ResponseEntity<>("Film in language "+languageId+" not found", HttpStatus.OK);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /* ---------------- 3 ------------------ */
    @PostMapping("add-film-to-store/{storeId}/{filmId}")
    public ResponseEntity<?> addFilmToStore(@PathVariable @Min(1) long storeId, @PathVariable @Min(1) long filmId){

        if (!storeService.existsById(storeId))
            return new ResponseEntity<>("Store with id " + storeId + " not found", HttpStatus.NOT_FOUND);

        if (!filmService.existsById(filmId))
            return new ResponseEntity<>("Film with id " + filmId + " not found", HttpStatus.NOT_FOUND);

        inventoryService.save(new Inventory(new Store(storeId), new Film(filmId)));
        return new ResponseEntity<>("Film " + filmId + " has been added to the store " + storeId, HttpStatus.OK);
    }

    /* ---------------- 4 ------------------ */
    @GetMapping("count-customers-by-store/{storeName}")
    public ResponseEntity<?> countCustomersByStore(@PathVariable String storeName){

        storeName = storeName.trim();
        if (!storeService.existsByStoreName(storeName))
            return new ResponseEntity<>("Store " + storeName + " not found", HttpStatus.NOT_FOUND);

        CustomerStoreResponse response = rentalService.getCustomersByStoreName(storeName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /* ---------------- 5 ------------------ */
    @PutMapping("add-update-rental")
    @Transactional
    public ResponseEntity<?> addUpdateRental(@RequestBody @Valid RentalRequest request){

        Optional<Inventory> i = inventoryService.findById(request.getInventoryId());
        if (!i.isPresent())
            return new ResponseEntity<>("Film not found in store", HttpStatus.NOT_FOUND);

        if (!customerService.existsById(request.getCustomerId()))
            return new ResponseEntity<>("Customer with id " + request.getCustomerId() + " not found", HttpStatus.NOT_FOUND);

        Optional<Rental> rental = rentalService.getRentalByInventory(request.getInventoryId());

        // Se trovo il noleggio allora significa che posso solo restituirlo
        if (rental.isPresent()){
            if(rental.get().getRentalId().getCustomer().getCustomerId() == request.getCustomerId()) {
                rental.get().setRentalReturn(Date.from(Instant.now()));
                return new ResponseEntity<>("Film was returned", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Ths film is in rental", HttpStatus.BAD_REQUEST);
            }
        }
        // altrimenti procedo al noleggio
        Rental newRental = new Rental(new RentalId(new Customer(request.getCustomerId()), i.get(), Date.from(Instant.now())));
        rentalService.save(newRental);

        return new ResponseEntity<>("The film was rented", HttpStatus.OK);
    }

    /* ---------------- 6 ------------------ */
    @GetMapping("count-rentals-in-date-range-by-store/{storeId}")
    public ResponseEntity<?> countRentalsInDateRangeByStore(
            @PathVariable @Min(1) long storeId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {

        if (start.after(end))
            return new ResponseEntity<>("Start date should be before the end date", HttpStatus.OK);

        if (!storeService.existsById(storeId))
            return new ResponseEntity<>("Store with id " + storeId + " not found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(rentalService.countRentalsInDateRangeByStore(storeId, start, end), HttpStatus.OK);
    }

    /* ---------------- 7 ------------------ */
    @GetMapping("find-all-films-rent-by-one-customer/{customerId}")
    public ResponseEntity<?> findAllFilmsRentByOneCustomer(@PathVariable @Min(1) long customerId){

        if (!customerService.existsById(customerId))
            return new ResponseEntity<>("Customer with id " + customerId + " not found", HttpStatus.NOT_FOUND);
        List<FilmRentResponse> list = rentalService.getAllFilmsRentByOneCustomer(customerId);

        if(list.isEmpty())
            return new ResponseEntity<>("No rents found for customer "+customerId, HttpStatus.OK);

        return new ResponseEntity<>(rentalService.getAllFilmsRentByOneCustomer(customerId), HttpStatus.OK);
    }

    /* ---------------- 8 ------------------ */
    @GetMapping("find-film-with-max-number-of-rent")
    public ResponseEntity<List<FilmMaxRentResponse>> findFilmWithMaxNumberOfRent(){
        return new ResponseEntity<>(rentalService.findFilmWithMaxNumberOfRent(), HttpStatus.OK);
    }

    /* ---------------- 9 ------------------ */
    @GetMapping("/find-films-by-actors")
    public ResponseEntity<?> findFilmsByActors(@RequestParam List<Long> staffIds){

        if(staffIds.isEmpty())
            return new ResponseEntity<>("No staff selected", HttpStatus.BAD_REQUEST);

        List<Long> actorsIds = filmStaffService.getActorsIds(staffIds);
        // Se il numero di elementi di actorIds è diverso da quello di staffIds significa che non sono tutti ACTOR
        if(staffIds.size() != actorsIds.size())
            return new ResponseEntity<>("You not selected only actors", HttpStatus.BAD_REQUEST);

        if(actorsIds.isEmpty())
            return new ResponseEntity<>("No actors selected", HttpStatus.NOT_FOUND);

        List<FilmResponse> filmsByStaffIds = filmStaffService.getFilmsByStaffIds(actorsIds, Long.valueOf(actorsIds.size()));

        if(filmsByStaffIds.isEmpty())
            return new ResponseEntity<>("No film found with ALL the selected actors", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(filmsByStaffIds, HttpStatus.OK);

    }

    /* ---------------- 10 ------------------ */
    @GetMapping("/find-rentable-films")
    public ResponseEntity<?> findRentableFilms(@RequestParam String title){
        List<FilmRentableResponse> list = rentalService.getRentableFilmsByTitle(title);
        if (list.isEmpty())
            return new ResponseEntity<>("Film not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
