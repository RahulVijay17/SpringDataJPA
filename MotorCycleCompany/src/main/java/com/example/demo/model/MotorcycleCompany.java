package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class MotorcycleCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "motorcycleCompany", cascade = CascadeType.ALL,targetEntity = ProductionLine.class)
    private List<ProductionLine> productionLines;

    private int numberOfMotorcycles;

    public List<ProductionLine> getProductionLines() {
        if (productionLines == null) {
            productionLines = new ArrayList<>();
        }
        return productionLines;
    }

}
