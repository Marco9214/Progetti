package it.cgmconsulting.solution.service;

import it.cgmconsulting.solution.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public boolean existsById(long customerId){
        return customerRepository.existsById(customerId);
    }
}
