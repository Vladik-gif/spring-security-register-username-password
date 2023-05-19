package com.vladik.demosecurity.api.controllers;

import com.vladik.demosecurity.api.dto.LoginDto;
import com.vladik.demosecurity.api.dto.AuthDto;
import com.vladik.demosecurity.api.service.AuthService;
import com.vladik.demosecurity.store.entity.RoleEntity;
import com.vladik.demosecurity.store.entity.UserEntity;
import com.vladik.demosecurity.store.repository.RoleRepository;
import com.vladik.demosecurity.store.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    private static final String authenticate = "/authenticate";
    private static final String register = "/register";

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(authenticate)
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        return authService.authenticateUser(loginDto);
    }

    @PostMapping(register)
    public ResponseEntity<?> registerUser(@RequestBody AuthDto authDto){
        return authService.registerUser(authDto);
    }
}