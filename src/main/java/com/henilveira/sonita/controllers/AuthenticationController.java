package com.henilveira.sonita.controllers;

import jakarta.servlet.http.HttpServletResponse;
import com.henilveira.sonita.domain.auth.LoginDTO;
import com.henilveira.sonita.domain.auth.RegisterDTO;
import com.henilveira.sonita.domain.user.User;
import com.henilveira.sonita.domain.user.UserDTO;
import com.henilveira.sonita.repositories.UserRepository;
import com.henilveira.sonita.services.TokenService;
import com.henilveira.sonita.utils.CookieUtil;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data, HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        CookieUtil.addJwtCookie(response, token);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário autenticado com sucesso!");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null ) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");

        String encryptedPassword = passwordEncoder.encode(data.password());

        User newUser = new User(data.email(), encryptedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário cadastrado com sucesso!");
    }
}
