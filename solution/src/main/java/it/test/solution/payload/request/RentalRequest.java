package it.cgmconsulting.solution.payload.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RentalRequest {

    @Min(1)
    private long customerId;

    @Min(1)
    private long inventoryId;

}
