package com.example.matriculacion.controller;

import com.example.matriculacion.entity.Matricula;
import com.example.matriculacion.repository.MatriculaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController extends CrudController<Matricula> {

    public MatriculaController(MatriculaRepository repository) {
        super(repository, (matricula, id) -> matricula.setId(id));
    }
}
