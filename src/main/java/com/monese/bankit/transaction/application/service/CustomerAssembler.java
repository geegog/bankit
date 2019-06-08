package com.monese.bankit.transaction.application.service;

import com.monese.bankit.transaction.application.dto.CustomerDTO;
import com.monese.bankit.transaction.domain.model.Customer;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class CustomerAssembler extends ResourceAssemblerSupport<Customer, CustomerDTO> {

    public CustomerAssembler() {
        super(TransactionService.class, CustomerDTO.class);
    }


    @Override
    public CustomerDTO toResource(Customer customer) {
        if (customer == null)
            return null;
        CustomerDTO dto = instantiateResource(customer);
        dto.set_id(customer.getId());
        dto.setCreated(customer.getCreated());
        dto.setAddress(customer.getAddress());
        dto.setBirthOfBirth(customer.getBirthOfBirth());
        dto.setUpdated(customer.getUpdated());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());

        return dto;
    }
}
