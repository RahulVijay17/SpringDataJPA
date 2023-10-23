package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private int productionRate;

    @ManyToOne(targetEntity = MotorcycleCompany.class)
    @JoinColumn(name = "motorcycle_company_id",referencedColumnName = "id")
    private MotorcycleCompany motorcycleCompany;

}
