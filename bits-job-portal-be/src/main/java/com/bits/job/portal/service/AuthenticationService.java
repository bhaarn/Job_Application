package com.bits.job.portal.service;

import com.bits.job.portal.dto.AuthResponse;
import com.bits.job.portal.dto.SignUpRequest;
import com.bits.job.portal.dto.SigninRequest;
import com.bits.job.portal.model.Role;
import com.bits.job.portal.model.User;
import com.bits.job.portal.repository.RoleRepository;
import com.bits.job.portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse signup(SignUpRequest request) {

        Role role = roleRepository.findByName("USER");
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .username(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(role))
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user.toUserDetails());
        return AuthResponse.builder().token(jwt).build();
    }

    public AuthResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user.toUserDetails());
        return AuthResponse.builder().token(jwt).build();
    }
}