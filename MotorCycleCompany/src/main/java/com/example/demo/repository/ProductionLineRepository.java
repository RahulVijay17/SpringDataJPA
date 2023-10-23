package com.example.demo.repository;

import com.example.demo.model.ProductionLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLine, Long> {
}
