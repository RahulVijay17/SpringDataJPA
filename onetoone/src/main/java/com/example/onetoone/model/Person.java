package com.example.onetoone.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Address.class)
    @JoinColumn(name = "address_id",referencedColumnName = "id")
    private Address address;

}
