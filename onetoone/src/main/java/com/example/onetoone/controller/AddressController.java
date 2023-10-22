package com.example.onetoone.controller;

import com.example.onetoone.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AddressController {

    private AddressService addressService;
}
