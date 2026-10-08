package com.example.matriculacion.controller;

import com.example.matriculacion.dto.EstudianteResponse;
import com.example.matriculacion.service.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class EstudianteControllerTest {

    @Mock
    private EstudianteService service;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = standaloneSetup(new EstudianteController(service)).build();
    }

    @Test
    void listaEstudiantesComoJson() throws Exception {
        when(service.listar()).thenReturn(List.of(new EstudianteResponse(
                1L, "Laura", "Pérez", "123", "laura@example.com", "555-0101")));

        mockMvc.perform(get("/api/estudiantes"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{"id":1,"nombre":"Laura","apellido":"Pérez",
                          "cedula":"123","correoElectronico":"laura@example.com",
                          "telefono":"555-0101"}]
                        """));
    }
}
