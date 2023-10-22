package com.example.onetoone.mapper;


import com.example.onetoone.dto.AddressDTO;
import com.example.onetoone.model.Address;

public class AddressMapper {

        public static Address toEntity(AddressDTO addressDTO, Address address) {
            address.setId(addressDTO.getId());
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setZipCode(addressDTO.getZipCode());
            return address;
        }

        public static AddressDTO toDTO(Address address, AddressDTO addressDTO) {
            addressDTO.setId(address.getId());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setCity(address.getCity());
            addressDTO.setZipCode(address.getZipCode());
            return addressDTO;
        }
    }