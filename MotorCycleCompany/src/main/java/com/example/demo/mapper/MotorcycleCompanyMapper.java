package com.example.demo.mapper;

import com.example.demo.dto.MotorcycleCompanyDTO;
import com.example.demo.dto.ProductionLineDTO;
import com.example.demo.model.MotorcycleCompany;
import com.example.demo.model.ProductionLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MotorcycleCompanyMapper {
    MotorcycleCompanyMapper INSTANCE = Mappers.getMapper(MotorcycleCompanyMapper.class);

    MotorcycleCompanyDTO motorcycleCompanyToDTO(MotorcycleCompany motorcycleCompany);

    MotorcycleCompany dtoToMotorcycleCompany(MotorcycleCompanyDTO motorcycleCompanyDTO);

    @Mapping(target = "types", source = "type")
    ProductionLineDTO mapProductionLineToDTO(ProductionLine productionLine);

    @Mapping(target = "type", source = "types")
    ProductionLine mapDTOToProductionLine(ProductionLineDTO productionLineDTO);


}
