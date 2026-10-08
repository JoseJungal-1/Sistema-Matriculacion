package com.example.matriculacion.repository;
import com.example.matriculacion.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Optional<Docente> findByCedula(String cedula);
}
