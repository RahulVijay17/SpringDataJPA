package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotorcycleCompanyDTO {
    private Long id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ProductionLineDTO> productionLines;
    private int numberOfMotorcycles;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int productionRateTotal;

}
