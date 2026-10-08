package com.example.matriculacion.controller;
import com.example.matriculacion.entity.Materia;
import com.example.matriculacion.repository.MateriaRepository;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/materias")
public class MateriaController extends CrudController<Materia> {
    public MateriaController(MateriaRepository r) { super(r, (e, id) -> e.setId(id)); }
}
