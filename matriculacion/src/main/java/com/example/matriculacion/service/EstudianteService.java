package com.example.matriculacion.service;

import com.example.matriculacion.dto.EstudianteRequest;
import com.example.matriculacion.dto.EstudianteResponse;
import com.example.matriculacion.entity.Estudiante;
import com.example.matriculacion.repository.EstudianteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EstudianteService {
    private final EstudianteRepository repository;

    public EstudianteService(EstudianteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<EstudianteResponse> listar() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteResponse obtener(Long id) {
        return toResponse(repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Estudiante no encontrado: " + id)));
    }

    public EstudianteResponse crear(EstudianteRequest request) {
        return toResponse(repository.save(toEntity(request)));
    }

    public EstudianteResponse actualizar(Long id, EstudianteRequest request) {
        Estudiante estudiante = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Estudiante no encontrado: " + id));
        estudiante.setNombre(request.nombre());
        estudiante.setApellido(request.apellido());
        estudiante.setCedula(request.cedula());
        estudiante.setCorreoElectronico(request.correoElectronico());
        estudiante.setTelefono(request.telefono());
        return toResponse(repository.save(estudiante));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Estudiante no encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private Estudiante toEntity(EstudianteRequest request) {
        return Estudiante.builder().nombre(request.nombre()).apellido(request.apellido())
                .cedula(request.cedula()).correoElectronico(request.correoElectronico())
                .telefono(request.telefono()).build();
    }

    private EstudianteResponse toResponse(Estudiante e) {
        return new EstudianteResponse(e.getId(), e.getNombre(), e.getApellido(), e.getCedula(),
                e.getCorreoElectronico(), e.getTelefono());
    }
}
