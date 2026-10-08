package com.example.matriculacion.repository;
import com.example.matriculacion.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AulaRepository extends JpaRepository<Aula, Long> {
    Optional<Aula> findByCodigo(String codigo);
}
