package com.example.matriculacion.dto;

import java.math.BigDecimal;

public record CalificacionRequest(Long matriculaId, String evaluacion, BigDecimal nota) {
}
