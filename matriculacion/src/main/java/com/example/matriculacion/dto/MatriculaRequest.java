package com.example.matriculacion.dto;

import com.example.matriculacion.entity.EstadoMatricula;

import java.time.LocalDate;

public record MatriculaRequest(Long estudianteId, Long cursoId, Long periodoId,
                               LocalDate fechaMatricula, EstadoMatricula estado) {
}
