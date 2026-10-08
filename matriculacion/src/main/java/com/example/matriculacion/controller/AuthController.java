package com.example.matriculacion.controller;

import com.example.matriculacion.dto.AuthResponse;
import com.example.matriculacion.dto.LoginRequest;
import com.example.matriculacion.dto.RegistroUsuarioRequest;
import com.example.matriculacion.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }

    @PostMapping("/registro")
    public ResponseEntity<Void> registrar(@RequestBody RegistroUsuarioRequest request) {
        service.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return service.autenticar(request);
    }
}
