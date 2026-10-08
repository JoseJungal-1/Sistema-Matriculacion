package com.example.matriculacion.repository;

import com.example.matriculacion.entity.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
    Optional<Periodo> findByNombre(String nombre);
}
