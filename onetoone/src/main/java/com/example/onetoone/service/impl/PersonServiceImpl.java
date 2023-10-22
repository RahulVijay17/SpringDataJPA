package com.example.onetoone.service.impl;

import com.example.onetoone.dto.PersonDTO;
import com.example.onetoone.exception.ResourceNotFoundException;
import com.example.onetoone.mapper.PersonMapper;
import com.example.onetoone.model.Person;
import com.example.onetoone.repository.AddressRepository;
import com.example.onetoone.repository.PersonRepository;
import com.example.onetoone.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private AddressRepository addressRepository;


    @Override
    public void createPerson(PersonDTO personDTO) {
        Person person = PersonMapper.toEntity(personDTO, new Person());
        Person savedPerson = personRepository.save(person);
        PersonMapper.toDTO(savedPerson, new PersonDTO());
    }

    @Override
    public List<PersonDTO> getAllPersonsWithAddress() {
        List<Person> persons = personRepository.findAll();
        return persons.stream().map(persons1 -> PersonMapper.toDTO(persons1, new PersonDTO())).collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            return PersonMapper.toDTO(person, new PersonDTO());
        } else {
            return null;
        }
    }

    @Override //this api updates a person alone also without removing foreign keys of address
    public PersonDTO updateAccount(Long id, PersonDTO personDTO) {
        if (personDTO != null) {
            Person person = personRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Person", "PersonId", String.valueOf(id)));

            // Check if the personDTO includes a non-null name field and update it
            if (personDTO.getName() != null) {
                person.setName(personDTO.getName());
            }

            // Checking if the personDTO includes an Address and update it
            if (personDTO.getAddress() != null) {
                // Updating the Address fields
                if (personDTO.getAddress().getStreet() != null) {
                    person.getAddress().setStreet(personDTO.getAddress().getStreet());
                }
                if (personDTO.getAddress().getCity() != null) {
                    person.getAddress().setCity(personDTO.getAddress().getCity());
                }
                if (personDTO.getAddress().getZipCode() != null) {
                    person.getAddress().setZipCode(personDTO.getAddress().getZipCode());
                }
            }

            person = personRepository.save(person);

            return PersonMapper.toDTO(person, new PersonDTO());
        }

        return null;
    }

    @Override
    public boolean deletePerson(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<List<Person>> findByName(String name) {
        return personRepository.findByNameContaining(name);
    }

    @Override
    public Optional<List<Person>> findByAddressCityContaining(String city) {
        return personRepository.findByAddressCityContaining(city);
    }

    @Override
    public Map<String, Map<String, List<PersonDTO>>> getAllPersonsWithNamesAddressCityGrouped() {
        List<Person> persons = personRepository.findAll();

        Map<String, Map<String, List<PersonDTO>>> groupedPersons = persons.stream()
                .map(person -> PersonMapper.toDTO(person, new PersonDTO()))
                .collect(Collectors.groupingBy(
                        PersonDTO::getName,
                        Collectors.groupingBy(
                                personDTO -> personDTO.getAddress().getCity()
                        )
                ));

        return groupedPersons;
    }

    @Override
    public Map<String, List<PersonDTO>> getAllPersonsWithNamesGrouped() {
        List<Person> persons = personRepository.findAll();

        Map<String, List<PersonDTO>> groupedPersons = persons.stream()
                .map(person -> PersonMapper.toDTO(person, new PersonDTO()))
                .collect(Collectors.groupingBy(PersonDTO::getName));

        return groupedPersons;
    }



}
