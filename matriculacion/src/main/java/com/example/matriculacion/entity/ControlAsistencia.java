package com.example.matriculacion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Entity
@Table(name = "controles_asistencia")
@Audited
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlAsistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoAsistencia estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "matricula_id", nullable = false)
    private Matricula matricula;
}
