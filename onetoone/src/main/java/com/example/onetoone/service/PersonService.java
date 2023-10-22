package com.example.onetoone.service;

import com.example.onetoone.dto.PersonDTO;
import com.example.onetoone.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PersonService {

    void createPerson(PersonDTO personDTO);

    List<PersonDTO> getAllPersonsWithAddress();

    PersonDTO getPersonById(Long id);

    PersonDTO updateAccount(Long id, PersonDTO personDTO);

    boolean deletePerson(Long id);

    Optional<List<Person>> findByName(String name);

    Optional<List<Person>> findByAddressCityContaining(String city);

    Map<String, Map<String, List<PersonDTO>>> getAllPersonsWithNamesAddressCityGrouped();

    Map<String, List<PersonDTO>> getAllPersonsWithNamesGrouped();
}
