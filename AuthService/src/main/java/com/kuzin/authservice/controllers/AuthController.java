package com.kuzin.authservice.controllers;

import com.kuzin.authservice.dto.AuthRequest;
import com.kuzin.authservice.dto.AuthenticationResponse;
import com.kuzin.authservice.dto.RegisterRequest;
import com.kuzin.authservice.logic.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }

}
