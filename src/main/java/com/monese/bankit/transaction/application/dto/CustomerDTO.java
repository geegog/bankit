package com.monese.bankit.transaction.application.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CustomerDTO extends ResourceSupport {

    private Long _id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String firstName;

    private String lastName;

    private LocalDate birthOfBirth;

    private String address;

}
