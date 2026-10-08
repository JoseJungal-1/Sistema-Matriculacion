package com.example.matriculacion.controller;

import com.example.matriculacion.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.function.BiConsumer;

abstract class CrudController<T> {

    private final CrudService<T> service;
    private final BiConsumer<T, Long> idSetter;

    protected CrudController(org.springframework.data.jpa.repository.JpaRepository<T, Long> repository,
                             BiConsumer<T, Long> idSetter) {
        this.service = new CrudService<>(repository);
        this.idSetter = idSetter;
    }

    @GetMapping
    public List<T> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public T crear(@RequestBody T entidad) {
        idSetter.accept(entidad, null);
        return service.crear(entidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> actualizar(
            @PathVariable Long id,
            @RequestBody T entidad) {
        idSetter.accept(entidad, id);
        return ResponseEntity.ok(service.actualizar(id, entidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
