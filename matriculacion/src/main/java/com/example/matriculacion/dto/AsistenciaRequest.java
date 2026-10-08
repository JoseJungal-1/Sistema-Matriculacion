package com.example.matriculacion.dto;

import com.example.matriculacion.entity.EstadoAsistencia;

import java.time.LocalDate;

public record AsistenciaRequest(Long matriculaId, LocalDate fecha, EstadoAsistencia estado) {
}
