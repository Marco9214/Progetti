package it.cgmconsulting.solution.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FilmRentableResponse {

    private String title;
    private String storeName;
    private long tot;

    @JsonIgnore
    private long rented;

    private long available;

    public long getAvailable() {
        return tot - rented;
    }

    public FilmRentableResponse(String title, String storeName, long tot, long rented) {
        this.title = title;
        this.storeName = storeName;
        this.tot = tot;
        this.rented = rented;
    }
}
