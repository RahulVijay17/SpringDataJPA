package com.example.demo.controller;

import com.example.demo.dto.MotorcycleCompanyDTO;
import com.example.demo.service.MotorcycleCompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(MotorcycleCompanyController.class)
public class MotorcycleCompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotorcycleCompanyService motorcycleCompanyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCompanyWhenNewCompanyThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        when(motorcycleCompanyService.saveMotorcycleCompany(any(MotorcycleCompanyDTO.class))).thenReturn(motorcycleCompanyDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(motorcycleCompanyDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Company"));
    }

    @Test
    public void testCreateCompanyWhenInvalidCompanyThenBadRequest() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();

        when(motorcycleCompanyService.saveMotorcycleCompany(any(MotorcycleCompanyDTO.class))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(motorcycleCompanyDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testGetAllCompaniesWhenCompaniesExistThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        when(motorcycleCompanyService.getAllCompany()).thenReturn(Collections.singletonList(motorcycleCompanyDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Company"));
    }

    @Test
    public void testGetCompanyByIdWhenCompanyExistsThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        when(motorcycleCompanyService.getMotorcycleCompanyById(anyLong())).thenReturn(motorcycleCompanyDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Company"));
    }

    @Test
    public void testGetCompanyByIdWhenCompanyDoesNotExistThenNotFound() throws Exception {
        when(motorcycleCompanyService.getMotorcycleCompanyById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteCompanyByIdWhenCompanyExistsThenNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testUpdateMotorcycleCompanyWhenCompanyExistsThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        when(motorcycleCompanyService.updateMotorcycleCompany(any(MotorcycleCompanyDTO.class))).thenReturn(motorcycleCompanyDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(motorcycleCompanyDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Company"));
    }

    @Test
    public void testUpdateMotorcycleCompanyWhenCompanyDoesNotExistThenNotFound() throws Exception {
        when(motorcycleCompanyService.updateMotorcycleCompany(any(MotorcycleCompanyDTO.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new MotorcycleCompanyDTO())))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetCompanyGroupedByNameWhenCompaniesExistThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        Map<String, List<MotorcycleCompanyDTO>> result = new HashMap<>();
        result.put("Test Company", Collections.singletonList(motorcycleCompanyDTO));

        when(motorcycleCompanyService.getCompanyGroupedByName()).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getCompanyGroupedByName"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Test Company'][0].name").value("Test Company"));
    }

    @Test
    public void testGetCompanyGroupedByNameWithProductionRateGreaterThan150WhenCompaniesExistThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        Map<String, Map<String, List<MotorcycleCompanyDTO>>> result = new HashMap<>();
        result.put("Test Company", Collections.singletonMap("Test Company", Collections.singletonList(motorcycleCompanyDTO)));

        when(motorcycleCompanyService.getCompanyGroupedByNameWithProductionRateGreaterThan150()).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getCompanyGroupedByNameWithProductionRateGreaterThan150"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Test Company']['Test Company'][0].name").value("Test Company"));
    }

    @Test
    public void testGetCompanyGroupedByNameWithProductionRateTotalWhenCompaniesExistThenSuccess() throws Exception {
        MotorcycleCompanyDTO motorcycleCompanyDTO = new MotorcycleCompanyDTO();
        motorcycleCompanyDTO.setName("Test Company");

        Map<String, Map<String, List<MotorcycleCompanyDTO>>> result = new HashMap<>();
        result.put("Test Company", Collections.singletonMap("Test Company", Collections.singletonList(motorcycleCompanyDTO)));

        when(motorcycleCompanyService.getCompanyGroupedByNameWithProductionRateTotal(anyLong())).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getCompanyGroupedByNameWithProductionRateTotal/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Test Company']['Test Company'][0].name").value("Test Company"));
    }
}