package com.example.matriculacion.controller;

import com.example.matriculacion.dto.EstudianteRequest;
import com.example.matriculacion.dto.EstudianteResponse;
import com.example.matriculacion.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    private final EstudianteService service;

    public EstudianteController(EstudianteService service) {
        this.service = service;
    }

    @GetMapping
    public List<EstudianteResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EstudianteResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<EstudianteResponse> crear(@RequestBody EstudianteRequest request) {
        return ResponseEntity.status(201).body(service.crear(request));
    }

    @PutMapping("/{id}")
    public EstudianteResponse actualizar(@PathVariable Long id,
                                         @RequestBody EstudianteRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
