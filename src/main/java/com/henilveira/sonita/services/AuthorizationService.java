package com.henilveira.sonita.services;

import com.henilveira.sonita.domain.auth.LoginDTO;
import com.henilveira.sonita.domain.auth.RegisterDTO;
import com.henilveira.sonita.domain.user.User;
import com.henilveira.sonita.repositories.UserRepository;
import com.henilveira.sonita.utils.CookieUtil;
import jdk.jfr.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.henilveira.sonita.services.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private TokenService tokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    public void validateLogin(LoginDTO data , HttpServletResponse response) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var accessToken = tokenService.generateAccessToken((User) auth.getPrincipal());
        var refreshToken = tokenService.generateRefreshToken((User) auth.getPrincipal());

        CookieUtil.addJwtCookie(response, accessToken, "accessToken");
        CookieUtil.addJwtCookie(response, refreshToken, "refreshToken");
    }

    public UserDetails validateRegister(RegisterDTO data) {
        UserDetails userExists = this.userRepository.findByEmail(data.email());
        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.email(), encryptedPassword, data.role());
        this.userRepository.save(newUser);

        return userExists;
    }

    public boolean userExists(RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null ) return true;
        return false;
    }
}
