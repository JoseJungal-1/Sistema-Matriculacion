package com.example.matriculacion.dto;

public record AuthResponse(String token, String tokenType, long expiresIn) {}
