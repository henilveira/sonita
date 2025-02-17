package com.henilveira.sonita.controllers;

import com.henilveira.sonita.services.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.henilveira.sonita.domain.auth.LoginDTO;
import com.henilveira.sonita.domain.auth.RegisterDTO;
import com.henilveira.sonita.utils.CookieUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthorizationService auth;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data, HttpServletResponse response) {
        auth.validateLogin(data,response);
        return ResponseEntity.status(HttpStatus.OK).body("Login efetuado com sucesso!");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        auth.validateRegister(data);
        if (auth.userExists(data)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        return ResponseEntity.status(HttpStatus.OK).body("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@Valid HttpServletResponse response, HttpServletRequest request) {
        CookieUtil.removeCookie(request, response);
        return ResponseEntity.status(HttpStatus.OK).body("Cookie removido com sucesso!");

    }
}
