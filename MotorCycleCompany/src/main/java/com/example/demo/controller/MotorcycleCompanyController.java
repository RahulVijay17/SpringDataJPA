package com.example.demo.controller;

import com.example.demo.dto.MotorcycleCompanyDTO;
import com.example.demo.repository.MotorcycleCompanyRepository;
import com.example.demo.repository.ProductionLineRepository;
import com.example.demo.service.MotorcycleCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MotorcycleCompanyController {

    @Autowired
    private MotorcycleCompanyService motorcycleCompanyService;

    @GetMapping
    //http://localhost:8089/api/v1
    //this will print all the mapped entities
    public ResponseEntity<List<MotorcycleCompanyDTO>> getAllCompanies() {
        return new ResponseEntity<>(motorcycleCompanyService.getAllCompany(), HttpStatus.OK);
    }

    @PostMapping
    //http://localhost:8089/api/v1
    //this method will save entities
    public ResponseEntity<MotorcycleCompanyDTO> createCompany(@RequestBody MotorcycleCompanyDTO motorcycleCompanyDTO) {
        return new ResponseEntity<>(motorcycleCompanyService.saveMotorcycleCompany(motorcycleCompanyDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    //http://localhost:8089/api/v1/1
    //will get all parent and child with id mapped
    public ResponseEntity<MotorcycleCompanyDTO> getCompanyById(@PathVariable Long id) {
        MotorcycleCompanyDTO companyDTO = motorcycleCompanyService.getMotorcycleCompanyById(id);
        if (companyDTO != null) {
            return ResponseEntity.ok(companyDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    //this method will delete only motorcycleCompany and production lines pk but not production lines
    //if u want to delete along with production lines use orphan removal=true
    public ResponseEntity<Void> deleteCompanyById(@PathVariable Long id) {
        motorcycleCompanyService.deleteMotorcycleCompany(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    //http://localhost:8089/api/v1/2
    public ResponseEntity<MotorcycleCompanyDTO> updateMotorcycleCompany(@PathVariable Long id, @RequestBody MotorcycleCompanyDTO motorcycleCompanyDTO) {
            MotorcycleCompanyDTO updatedCompanyDTO = motorcycleCompanyService.updateMotorcycleCompany(motorcycleCompanyDTO);
        if (updatedCompanyDTO != null) {
            return ResponseEntity.ok(updatedCompanyDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getCompanyGroupedByName")
    //http://localhost:8089/api/v1/getCompanyGroupedByName
    public ResponseEntity<Map<String, List<MotorcycleCompanyDTO>>> getCompanyGroupedByName() {
        Map<String, List<MotorcycleCompanyDTO>> result = motorcycleCompanyService.getCompanyGroupedByName();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getCompanyGroupedByNameWithProductionRateGreaterThan150")
    //http://localhost:8089/api/v1/getCompanyGroupedByNameWithProductionRateGreaterThan150
    public ResponseEntity<Map<String, Map<String, List<MotorcycleCompanyDTO>>> > getCompanyGroupedByNameWithProductionRateGreaterThan150() {
        Map<String, Map<String, List<MotorcycleCompanyDTO>>> result = motorcycleCompanyService.getCompanyGroupedByNameWithProductionRateGreaterThan150();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getCompanyGroupedByNameWithProductionRateTotal/{companyId}")
    public ResponseEntity<Map<String, Map<String, List<MotorcycleCompanyDTO>>> > getCompanyGroupedByNameWithProductionRateTotal(@PathVariable Long companyId) {
        Map<String, Map<String, List<MotorcycleCompanyDTO>>> result = motorcycleCompanyService.getCompanyGroupedByNameWithProductionRateTotal(companyId);
        return ResponseEntity.ok(result);
    }

}


    //POST JSON

/*    {
            "name": "Company XYZ",
            "productionLines": [
        {
                "types": "Type A",
                "productionRate": 100
        },
        {
                "types": "Type B",
                "productionRate": 150
        }
  ],
        "numberOfMotorcycles": 1000
    }*/

    //PUT JSON

        /*{
                "id": 2,
                "name": "Updated Companies",
                "productionLines": [
                {
                "id": 3,
                "types": "Updated Type C",
                "productionRate": 120
                },
                {
                "id": 4,
                "types": "Updated Type D",
                "productionRate": 180
                }
                ],
                "numberOfMotorcycles": 1500
        }
                */
