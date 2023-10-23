package com.example.demo.serviceImpl;

import com.example.demo.dto.MotorcycleCompanyDTO;
import com.example.demo.dto.ProductionLineDTO;
import com.example.demo.mapper.MotorcycleCompanyMapper;
import com.example.demo.model.MotorcycleCompany;
import com.example.demo.model.ProductionLine;
import com.example.demo.repository.MotorcycleCompanyRepository;
import com.example.demo.repository.ProductionLineRepository;
import com.example.demo.service.MotorcycleCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MotorcycleCompanyServiceImpl implements MotorcycleCompanyService {

    @Autowired
    private MotorcycleCompanyRepository motorcycleCompanyRepository;
    @Autowired
    private ProductionLineRepository productionLineRepository;

    @Override
    public List<MotorcycleCompanyDTO> getAllCompany() {
        List<MotorcycleCompany> motorcycleCompanies = motorcycleCompanyRepository.findAll();
        return motorcycleCompanies.stream()
                .map(MotorcycleCompanyMapper.INSTANCE::motorcycleCompanyToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MotorcycleCompanyDTO saveMotorcycleCompany(MotorcycleCompanyDTO motorcycleCompanyDTO) {
        MotorcycleCompany motorcycleCompany = MotorcycleCompanyMapper.INSTANCE.dtoToMotorcycleCompany(motorcycleCompanyDTO);

        // Set MotorcycleCompany as the owner of the relationship with ProductionLines
        for (ProductionLine productionLine : motorcycleCompany.getProductionLines()) {
            productionLine.setMotorcycleCompany(motorcycleCompany);
        }

        MotorcycleCompany savedCompany = motorcycleCompanyRepository.save(motorcycleCompany);

        return MotorcycleCompanyMapper.INSTANCE.motorcycleCompanyToDTO(savedCompany);
    }


    @Override
    public MotorcycleCompanyDTO getMotorcycleCompanyById(Long id) {
        Optional<MotorcycleCompany> optionalCompany = motorcycleCompanyRepository.findById(id);
        return optionalCompany.map(MotorcycleCompanyMapper.INSTANCE::motorcycleCompanyToDTO).orElse(null);
    }

    @Override
    public void deleteMotorcycleCompany(Long id) {
        motorcycleCompanyRepository.deleteById(id);
    }

    @Override
    public MotorcycleCompanyDTO updateMotorcycleCompany(MotorcycleCompanyDTO motorcycleCompanyDTO) {
        // 1. Retrieve the existing MotorcycleCompany entity from the database
        Optional<MotorcycleCompany> optionalCompany = motorcycleCompanyRepository.findById(motorcycleCompanyDTO.getId());

        if (optionalCompany.isPresent()) {
            // 2. Update the fields of the retrieved entity with DTO values
            MotorcycleCompany existingCompany = optionalCompany.get();
            existingCompany.setName(motorcycleCompanyDTO.getName());
            existingCompany.setNumberOfMotorcycles(motorcycleCompanyDTO.getNumberOfMotorcycles());

            // 3. Update associated ProductionLine entities
            List<ProductionLineDTO> productionLineDTOs = motorcycleCompanyDTO.getProductionLines();
            if (productionLineDTOs != null) {
                List<ProductionLine> updatedProductionLines = new ArrayList<>();

                for (ProductionLineDTO productionLineDTO : productionLineDTOs) {
                    // Fetch the existing ProductionLine entity by ID
                    Optional<ProductionLine> optionalProductionLine = productionLineRepository.findById(productionLineDTO.getId());

                    if (optionalProductionLine.isPresent()) {
                        // Update the existing ProductionLine entity based on the DTO data
                        ProductionLine existingProductionLine = optionalProductionLine.get();
                        existingProductionLine.setType(productionLineDTO.getTypes()); // Corrected field name
                        existingProductionLine.setProductionRate(productionLineDTO.getProductionRate());
                        updatedProductionLines.add(existingProductionLine);
                    }
                }

                // Update the MotorcycleCompany with the updated ProductionLine entities
                existingCompany.setProductionLines(updatedProductionLines);
            }

            // 4. Save the updated entity and its associated ProductionLine entities
            MotorcycleCompany updatedCompany = motorcycleCompanyRepository.save(existingCompany);

            // 5. Optionally, return a DTO representing the updated entity
            return MotorcycleCompanyMapper.INSTANCE.motorcycleCompanyToDTO(updatedCompany);
        } else {
            // Handle the case where the entity with the given ID is not found
            return null;
        }
    }

    @Override
    public Map<String, List<MotorcycleCompanyDTO>> getCompanyGroupedByName() {
        List<MotorcycleCompany> companies = motorcycleCompanyRepository.findAll();

        return companies.stream()
                .map(MotorcycleCompanyMapper.INSTANCE::motorcycleCompanyToDTO)
                .collect(Collectors.groupingBy(MotorcycleCompanyDTO::getName));

}

    @Override
    public Map<String, Map<String, List<MotorcycleCompanyDTO>>> getCompanyGroupedByNameWithProductionRateGreaterThan150() {
        List<MotorcycleCompany> companies = motorcycleCompanyRepository.findAll();

        Map<String, Map<String, List<MotorcycleCompanyDTO>>> groupedCompanies = companies.stream()
                .map(MotorcycleCompanyMapper.INSTANCE::motorcycleCompanyToDTO)
                .collect(Collectors.groupingBy(MotorcycleCompanyDTO::getName,
                        Collectors.groupingBy(dto -> "productionRate",
                                Collectors.toList())));

        return groupedCompanies;
    }

    @Override
    public Map<String, Map<String, List<MotorcycleCompanyDTO>>> getCompanyGroupedByNameWithProductionRateTotal(Long companyId) {
        List<MotorcycleCompany> companies = motorcycleCompanyRepository.findAll();

        Map<String, Map<String, List<MotorcycleCompanyDTO>>> groupedCompanies = companies.stream()
                .filter(company -> company.getId().equals(companyId)) // Filter by the provided companyId
                .map(MotorcycleCompanyMapper.INSTANCE::motorcycleCompanyToDTO)
                .collect(Collectors.groupingBy(MotorcycleCompanyDTO::getName,
                        Collectors.groupingBy(dto -> "productionRate",
                                Collectors.collectingAndThen(Collectors.toList(), list -> {
                                    int total = list.stream()
                                            .map(MotorcycleCompanyDTO::getProductionLines)
                                            .flatMap(List::stream)
                                            .mapToInt(ProductionLineDTO::getProductionRate)
                                            .sum();
                                    list.forEach(dto -> dto.setProductionRateTotal(total));
                                    return list;
                                }))));

        return groupedCompanies;
    }
}