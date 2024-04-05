package com.natwest.authapi.controllers;

import com.natwest.authapi.dtos.LoginUserDto;
import com.natwest.authapi.dtos.RegisterUserDto;
import com.natwest.authapi.entities.User;
import com.natwest.authapi.response.LoginResponse;
import com.natwest.authapi.services.AuthenticationService;
import com.natwest.authapi.services.JwtService;
import jakarta.mail.internet.AddressException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    //Neha Dhore = pass123
    //Dipti = dipti123
    //Neha Saraf = neha123
    //Janhvi Sende = janhvi123
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @Validated
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) throws AddressException {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}