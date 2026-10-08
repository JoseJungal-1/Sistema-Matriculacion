package com.example.matriculacion.repository;
import com.example.matriculacion.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Optional<Carrera> findByCodigo(String codigo);
}
