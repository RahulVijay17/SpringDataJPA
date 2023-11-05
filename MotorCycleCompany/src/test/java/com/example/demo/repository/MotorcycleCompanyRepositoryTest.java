package com.example.demo.repository;

import com.example.demo.model.MotorcycleCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:test.properties")
public class MotorcycleCompanyRepositoryTest {

    @Autowired
    private MotorcycleCompanyRepository motorcycleCompanyRepository;

    @Test
    public void testSaveMotorcycleCompanyWhenValidMotorcycleCompanyThenSaveSuccessfully() {
        // Arrange
        MotorcycleCompany motorcycleCompany = new MotorcycleCompany();
        motorcycleCompany.setName("Test Company");
        motorcycleCompany.setNumberOfMotorcycles(100);

        // Act
        MotorcycleCompany savedMotorcycleCompany = motorcycleCompanyRepository.save(motorcycleCompany);

        // Assert
        Optional<MotorcycleCompany> optionalMotorcycleCompany = motorcycleCompanyRepository.findById(savedMotorcycleCompany.getId());
        assertTrue(optionalMotorcycleCompany.isPresent());
        assertEquals(motorcycleCompany.getName(), optionalMotorcycleCompany.get().getName());
        assertEquals(motorcycleCompany.getNumberOfMotorcycles(), optionalMotorcycleCompany.get().getNumberOfMotorcycles());
    }

    @Test
    public void testFindByNameWhenValidNameThenReturnMotorcycleCompany() {
        // Arrange
        String companyName = "Test Company";
        MotorcycleCompany motorcycleCompany = new MotorcycleCompany();
        motorcycleCompany.setName(companyName);
        motorcycleCompany.setNumberOfMotorcycles(100);
        motorcycleCompanyRepository.save(motorcycleCompany);

        // Act
        MotorcycleCompany foundMotorcycleCompany = motorcycleCompanyRepository.findByName(companyName);

        // Assert
        assertEquals(companyName, foundMotorcycleCompany.getName());
    }
}