package it.cgmconsulting.solution.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CustomerStoreResponse {

    private String storeName;
    private long totalCustomers;
}
