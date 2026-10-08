package com.example.matriculacion.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class CrudService<T> {
    private final JpaRepository<T, Long> repository;

    public CrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<T> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public T obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro no encontrado: " + id));
    }

    public T crear(T entidad) {
        return repository.save(entidad);
    }

    public T actualizar(Long id, T entidad) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Registro no encontrado: " + id);
        }
        return repository.save(entidad);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Registro no encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
