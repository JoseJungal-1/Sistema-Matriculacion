package com.example.matriculacion.config;

import com.example.matriculacion.entity.Aula;
import com.example.matriculacion.entity.Carrera;
import com.example.matriculacion.entity.Curso;
import com.example.matriculacion.entity.Docente;
import com.example.matriculacion.entity.Instituto;
import com.example.matriculacion.entity.Materia;
import com.example.matriculacion.entity.Periodo;
import com.example.matriculacion.entity.Permiso;
import com.example.matriculacion.entity.Rol;
import com.example.matriculacion.entity.Usuario;
import com.example.matriculacion.repository.AulaRepository;
import com.example.matriculacion.repository.CarreraRepository;
import com.example.matriculacion.repository.CursoRepository;
import com.example.matriculacion.repository.DocenteRepository;
import com.example.matriculacion.repository.InstitutoRepository;
import com.example.matriculacion.repository.MateriaRepository;
import com.example.matriculacion.repository.PeriodoRepository;
import com.example.matriculacion.repository.PermisoRepository;
import com.example.matriculacion.repository.RolRepository;
import com.example.matriculacion.repository.UsuarioRepository;
import com.example.matriculacion.repository.RolPermisoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
            InstitutoRepository institutoRepository,
            CarreraRepository carreraRepository,
            MateriaRepository materiaRepository,
            DocenteRepository docenteRepository,
            AulaRepository aulaRepository,
            CursoRepository cursoRepository,
            PeriodoRepository periodoRepository,
            RolRepository rolRepository,
            PermisoRepository permisoRepository,
            UsuarioRepository usuarioRepository,
            RolPermisoRepository rolPermisoRepository,
            @Value("${app.seed.admin-password}") String adminPassword,
            PasswordEncoder passwordEncoder) {
        return args -> {
            Rol administrador = rolRepository.findByNombre("ADMIN")
                    .orElseGet(() -> rolRepository.save(Rol.builder()
                            .nombre("ADMIN").descripcion("Administrador del sistema").build()));

            Usuario admin = usuarioRepository.findByUsername("admin")
                    .orElseGet(() -> Usuario.builder().username("admin").build());
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setActivo(true);
            admin.setRoles(java.util.Set.of(administrador));
            usuarioRepository.save(admin);

            Permiso gestionar = permisoRepository.findByNombre("GESTIONAR_CATALOGO")
                    .orElseGet(() -> permisoRepository.save(Permiso.builder()
                            .nombre("GESTIONAR_CATALOGO")
                            .descripcion("Gestionar la información académica").build()));
            if (rolPermisoRepository.findAll().stream()
                    .noneMatch(asignacion -> asignacion.getRol().getId().equals(administrador.getId())
                            && asignacion.getPermiso().getId().equals(gestionar.getId()))) {
                rolPermisoRepository.save(com.example.matriculacion.entity.RolPermiso.builder()
                        .rol(administrador).permiso(gestionar).build());
            }

            Instituto instituto = institutoRepository.findByCodigo("INST-001")
                    .orElseGet(() -> institutoRepository.save(Instituto.builder()
                            .codigo("INST-001").nombre("Instituto Tecnológico Central").build()));
            Carrera carrera = carreraRepository.findByCodigo("ING-SIS")
                    .orElseGet(() -> carreraRepository.save(Carrera.builder()
                            .codigo("ING-SIS").nombre("Ingeniería de Sistemas")
                            .duracionSemestres(10).instituto(instituto).build()));
            materiaRepository.findByCodigo("MAT-101").orElseGet(() -> materiaRepository.save(Materia.builder()
                    .codigo("MAT-101").nombre("Matemática I").creditos(4).semestre(1)
                    .carrera(carrera).build()));
            docenteRepository.findByCedula("000000001").orElseGet(() -> docenteRepository.save(Docente.builder()
                    .nombre("Ana").apellido("García").cedula("000000001")
                    .correoElectronico("ana.garcia@example.com").especialidad("Matemática").build()));
            aulaRepository.findByCodigo("A-101").orElseGet(() -> aulaRepository.save(Aula.builder()
                    .codigo("A-101").edificio("Edificio A").capacidad(30).build()));
            cursoRepository.findByCodigo("CUR-101").orElseGet(() -> cursoRepository.save(Curso.builder()
                    .codigo("CUR-101").nombre("Matemática I - Grupo A").creditos(4).build()));
            periodoRepository.findByNombre("2026-1").orElseGet(() -> periodoRepository.save(Periodo.builder()
                    .nombre("2026-1").fechaInicio(LocalDate.of(2026, 1, 15))
                    .fechaFin(LocalDate.of(2026, 6, 30)).build()));
        };
    }
}