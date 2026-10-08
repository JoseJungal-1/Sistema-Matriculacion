package com.example.matriculacion.repository;
import com.example.matriculacion.entity.Instituto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface InstitutoRepository extends JpaRepository<Instituto, Long> {
    Optional<Instituto> findByCodigo(String codigo);
}
