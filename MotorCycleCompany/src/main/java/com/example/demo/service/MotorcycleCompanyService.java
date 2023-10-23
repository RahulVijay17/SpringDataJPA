package com.example.demo.service;

import com.example.demo.dto.MotorcycleCompanyDTO;
import com.example.demo.dto.ProductionLineDTO;
import java.util.List;
import java.util.Map;

public interface MotorcycleCompanyService {

     List<MotorcycleCompanyDTO> getAllCompany();

     MotorcycleCompanyDTO saveMotorcycleCompany(MotorcycleCompanyDTO motorcycleCompanyDTO);

     MotorcycleCompanyDTO getMotorcycleCompanyById(Long id);

     void deleteMotorcycleCompany(Long id);

     MotorcycleCompanyDTO updateMotorcycleCompany(MotorcycleCompanyDTO motorcycleCompanyDTO);

     Map<String, List<MotorcycleCompanyDTO>> getCompanyGroupedByName();

     Map<String, Map<String, List<MotorcycleCompanyDTO>>> getCompanyGroupedByNameWithProductionRateGreaterThan150();

     Map<String, Map<String, List<MotorcycleCompanyDTO>>> getCompanyGroupedByNameWithProductionRateTotal(Long companyId);

}
