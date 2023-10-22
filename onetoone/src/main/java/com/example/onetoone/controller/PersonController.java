package com.example.onetoone.controller;

import com.example.onetoone.dto.PersonDTO;
import com.example.onetoone.mapper.PersonMapper;
import com.example.onetoone.model.Person;
import com.example.onetoone.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/add") //http://localhost:8080/api/add
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
         personService.createPerson(personDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getAllPersons") //http://localhost:8080/api/getAllPersons
    public ResponseEntity<List<PersonDTO>> getAllPersonsWithAddress() {
        List<PersonDTO> allPersonsWithAddress = personService.getAllPersonsWithAddress();
        return new ResponseEntity<>(allPersonsWithAddress, HttpStatus.OK);
    }

    @GetMapping("/getPersonById/{id}") //http://localhost:8080/api/getPersonById/1
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        PersonDTO person = personService.getPersonById(id);

        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}") //http://localhost:8080/api/update/1
    public ResponseEntity<PersonDTO> updateAccountDetails(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPersonDTO = personService.updateAccount(id, personDTO);

        if (updatedPersonDTO != null) {
            return new ResponseEntity<>(updatedPersonDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}") //http://localhost:8080/api/delete/3
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        //this api will delete persons with address because cascade is all
        //if cascade only persist it will delete only in person along with address foreign key
        //but not the record in address table
        boolean deleted = personService.deletePerson(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findPersonsByName") //http://localhost:8080/api/findPersonsByName?name=Updated John Doe
    public ResponseEntity<List<PersonDTO>> findPersonsByName(@RequestParam String name) {
        Optional<List<Person>> personsOptional = personService.findByName(name);

        if (personsOptional.isPresent()) {
            List<PersonDTO> personDTOs = personsOptional.get()
                    .stream()
                    .map(person -> PersonMapper.toDTO(person, new PersonDTO()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(personDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findPersonsByCity") //http://localhost:8080/api/findPersonsByCity?city=Updated City
    public ResponseEntity<List<PersonDTO>> findPersonsByCity(@RequestParam String city) {
        Optional<List<Person>> persons = personService.findByAddressCityContaining(city);

        if (persons.isPresent()) {
            List<PersonDTO> personDTOs = persons.get().stream()
                    .map(person -> PersonMapper.toDTO(person,new PersonDTO()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(personDTOs);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllPersonsWithNamesAddressCityGrouped") //http://localhost:8080/api/getAllPersonsWithNamesAddressCityGrouped
    public ResponseEntity<Map<String, Map<String, List<PersonDTO>>>> getAllPersonsWithNamesAddressCityGrouped() {
        Map<String, Map<String, List<PersonDTO>>> groupedPersons = personService.getAllPersonsWithNamesAddressCityGrouped();

        if (groupedPersons.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(groupedPersons);
        }
    }

    @GetMapping("/getAllPersonsWithNamesGrouped") //http://localhost:8080/api/getAllPersonsWithNamesGrouped
    public ResponseEntity<Map<String, List<PersonDTO>>> getAllPersonsWithNamesGrouped() {
        Map<String, List<PersonDTO>> groupedPersons = personService.getAllPersonsWithNamesGrouped();

        if (groupedPersons.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(groupedPersons);
        }
    }

    //JSON STRUCTURE FOR POST
    /*{
        "name": "Rahul S",
            "address": {
        "street": "123 Main Street",
                "city": "CornField",
                "zipCode": "12345"
    }
    }
*/

    //JSON STRUCTURE FOR PUT
    /*{
        "id": 1,
            "name": "Updated John Doe",
            "address": {
        "id": 1,
                "street": "Updated Streets",
                "city": "Updated City",
                "zipCode": "Updated ZipCode"
    }
    }*/



}
