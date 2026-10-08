package com.example.matriculacion.service;

import com.example.matriculacion.dto.EstudianteRequest;
import com.example.matriculacion.entity.Estudiante;
import com.example.matriculacion.repository.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository repository;

    @InjectMocks
    private EstudianteService service;

    @Test
    void creaEstudianteYDevuelveRespuesta() {
        EstudianteRequest request = new EstudianteRequest(
                "Laura", "Pérez", "123", "laura@example.com", "555-0101");
        Estudiante saved = Estudiante.builder().id(1L).nombre("Laura")
                .apellido("Pérez").cedula("123")
                .correoElectronico("laura@example.com").telefono("555-0101").build();
        when(repository.save(any(Estudiante.class))).thenReturn(saved);

        var response = service.crear(request);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nombre()).isEqualTo("Laura");
        verify(repository).save(any(Estudiante.class));
    }
}
