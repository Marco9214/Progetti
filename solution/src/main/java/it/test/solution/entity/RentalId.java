package it.cgmconsulting.solution.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RentalId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @Column(nullable = false)
    private Date rentalDate;

	@Override
	public int hashCode() {
		return Objects.hash(customer, inventory, rentalDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentalId other = (RentalId) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(inventory, other.inventory)
				&& Objects.equals(rentalDate, other.rentalDate);
	}
}
