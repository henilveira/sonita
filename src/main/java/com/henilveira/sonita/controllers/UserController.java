package com.henilveira.sonita.controllers;

import com.henilveira.sonita.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("getall")
    public ResponseEntity getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

}
