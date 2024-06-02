package it.cgmconsulting.solution.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
public class Rental {

    @EmbeddedId
    private RentalId rentalId;

    private Date rentalReturn;

    public Rental(RentalId rentalId) {
        this.rentalId = rentalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return rentalId.equals(rental.rentalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId);
    }
}
