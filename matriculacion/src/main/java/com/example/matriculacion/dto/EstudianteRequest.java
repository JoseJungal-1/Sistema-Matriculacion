package com.example.matriculacion.dto;

public record EstudianteRequest(
        String nombre,
        String apellido,
        String cedula,
        String correoElectronico,
        String telefono) {}
