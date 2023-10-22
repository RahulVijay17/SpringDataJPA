package com.example.onetoone.dto;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private AddressDTO address;
}
