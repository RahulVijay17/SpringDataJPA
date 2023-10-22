package com.example.onetoone.service.impl;

import com.example.onetoone.repository.AddressRepository;
import com.example.onetoone.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepository;

}
