package com.prestamossb.prestamossbapi.infraestruture.controllers.authentication;

import com.prestamossb.prestamossbapi.app.Auth.AuthRequest;
import com.prestamossb.prestamossbapi.app.Auth.AuthResponse;
import com.prestamossb.prestamossbapi.app.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/public/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
