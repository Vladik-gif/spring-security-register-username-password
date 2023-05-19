package com.vladik.demosecurity.api.service;

import com.vladik.demosecurity.api.dto.AuthDto;
import com.vladik.demosecurity.api.dto.LoginDto;
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
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> authenticateUser(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );



        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Ви в системі", HttpStatus.OK);
    }

    public ResponseEntity<?> registerUser(AuthDto authDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(authDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(authDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for password exists in DB
        if (userRepository.existsByPassword(authDto.getPassword())){
            return new ResponseEntity<>("Password is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        UserEntity user = new UserEntity();
        user.setName(authDto.getName());
        user.setUsername(authDto.getUsername());
        user.setEmail(authDto.getEmail());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));

        RoleEntity roles = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("Регистація пройшла успішно!", HttpStatus.OK);

    }

}
