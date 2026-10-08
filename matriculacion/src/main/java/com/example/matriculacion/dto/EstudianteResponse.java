package com.example.matriculacion.dto;

public record EstudianteResponse(
        Long id,
        String nombre,
        String apellido,
        String cedula,
        String correoElectronico,
        String telefono) {}