package com.example.onetoone.mapper;

import com.example.onetoone.dto.AddressDTO;
import com.example.onetoone.dto.PersonDTO;
import com.example.onetoone.model.Address;
import com.example.onetoone.model.Person;

public class PersonMapper {

    public static Person toEntity(PersonDTO personDTO, Person person) {
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());

        if (personDTO.getAddress() != null) {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setId(personDTO.getAddress().getId());
            person.getAddress().setStreet(personDTO.getAddress().getStreet());
            person.getAddress().setCity(personDTO.getAddress().getCity());
            person.getAddress().setZipCode(personDTO.getAddress().getZipCode());
        } else {
            person.setAddress(null);
        }

        return person;
    }

    public static PersonDTO toDTO(Person person, PersonDTO personDTO) {
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());

        if (person.getAddress() != null) {
            if (personDTO.getAddress() == null) {
                personDTO.setAddress(new AddressDTO());
            }
            personDTO.getAddress().setId(person.getAddress().getId());
            personDTO.getAddress().setStreet(person.getAddress().getStreet());
            personDTO.getAddress().setCity(person.getAddress().getCity());
            personDTO.getAddress().setZipCode(person.getAddress().getZipCode());
        } else {
            personDTO.setAddress(null);
        }

        return personDTO;
    }
}

