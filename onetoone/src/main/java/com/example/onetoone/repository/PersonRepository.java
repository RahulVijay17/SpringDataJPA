package com.example.onetoone.repository;

import com.example.onetoone.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    Optional<List<Person>> findByNameContaining(String name);

    Optional<List<Person>> findByAddressCityContaining(String city);


}
