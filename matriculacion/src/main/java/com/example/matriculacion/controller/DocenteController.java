package com.example.matriculacion.controller;
import com.example.matriculacion.entity.Docente;
import com.example.matriculacion.repository.DocenteRepository;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/docentes")
public class DocenteController extends CrudController<Docente> {
    public DocenteController(DocenteRepository r) { super(r, (e, id) -> e.setId(id)); }
}
