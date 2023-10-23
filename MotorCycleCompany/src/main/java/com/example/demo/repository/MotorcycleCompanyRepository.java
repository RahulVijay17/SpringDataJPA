package com.example.demo.repository;

import com.example.demo.model.MotorcycleCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotorcycleCompanyRepository extends JpaRepository<MotorcycleCompany, Long> {
}
