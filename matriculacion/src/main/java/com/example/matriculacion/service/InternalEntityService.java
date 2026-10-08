package com.example.matriculacion.service;

import com.example.matriculacion.entity.AsignacionDocente;
import com.example.matriculacion.entity.Aula;
import com.example.matriculacion.entity.Calificacion;
import com.example.matriculacion.entity.Carrera;
import com.example.matriculacion.entity.ControlAsistencia;
import com.example.matriculacion.entity.Curso;
import com.example.matriculacion.entity.Instituto;
import com.example.matriculacion.entity.Periodo;
import com.example.matriculacion.entity.Permiso;
import com.example.matriculacion.entity.Rol;
import com.example.matriculacion.entity.RolPermiso;
import com.example.matriculacion.entity.Usuario;
import com.example.matriculacion.repository.AsignacionDocenteRepository;
import com.example.matriculacion.repository.AulaRepository;
import com.example.matriculacion.repository.CalificacionRepository;
import com.example.matriculacion.repository.CarreraRepository;
import com.example.matriculacion.repository.ControlAsistenciaRepository;
import com.example.matriculacion.repository.CursoRepository;
import com.example.matriculacion.repository.InstitutoRepository;
import com.example.matriculacion.repository.PeriodoRepository;
import com.example.matriculacion.repository.PermisoRepository;
import com.example.matriculacion.repository.RolPermisoRepository;
import com.example.matriculacion.repository.RolRepository;
import com.example.matriculacion.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InternalEntityService {
    private final InstitutoRepository institutoRepository;
    private final CarreraRepository carreraRepository;
    private final AulaRepository aulaRepository;
    private final AsignacionDocenteRepository asignacionRepository;
    private final CalificacionRepository calificacionRepository;
    private final ControlAsistenciaRepository asistenciaRepository;
    private final CursoRepository cursoRepository;
    private final PeriodoRepository periodoRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final RolPermisoRepository rolPermisoRepository;
    private final UsuarioRepository usuarioRepository;

    public InternalEntityService(
            InstitutoRepository institutoRepository,
            CarreraRepository carreraRepository,
            AulaRepository aulaRepository,
            AsignacionDocenteRepository asignacionRepository,
            CalificacionRepository calificacionRepository,
            ControlAsistenciaRepository asistenciaRepository,
            CursoRepository cursoRepository,
            PeriodoRepository periodoRepository,
            RolRepository rolRepository,
            PermisoRepository permisoRepository,
            RolPermisoRepository rolPermisoRepository,
            UsuarioRepository usuarioRepository) {
        this.institutoRepository = institutoRepository;
        this.carreraRepository = carreraRepository;
        this.aulaRepository = aulaRepository;
        this.asignacionRepository = asignacionRepository;
        this.calificacionRepository = calificacionRepository;
        this.asistenciaRepository = asistenciaRepository;
        this.cursoRepository = cursoRepository;
        this.periodoRepository = periodoRepository;
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
        this.rolPermisoRepository = rolPermisoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Instituto> listarInstitutos() { return institutoRepository.findAll(); }
    public Instituto guardarInstituto(Instituto entidad) { return institutoRepository.save(entidad); }
    public Instituto obtenerInstituto(Long id) { return institutoRepository.findById(id).orElseThrow(() -> noEncontrado("Instituto", id)); }

    @Transactional(readOnly = true)
    public List<Carrera> listarCarreras() { return carreraRepository.findAll(); }
    public Carrera guardarCarrera(Carrera entidad) { return carreraRepository.save(entidad); }
    public Carrera obtenerCarrera(Long id) { return carreraRepository.findById(id).orElseThrow(() -> noEncontrado("Carrera", id)); }

    @Transactional(readOnly = true)
    public List<Aula> listarAulas() { return aulaRepository.findAll(); }
    public Aula guardarAula(Aula entidad) { return aulaRepository.save(entidad); }
    public Aula obtenerAula(Long id) { return aulaRepository.findById(id).orElseThrow(() -> noEncontrado("Aula", id)); }

    @Transactional(readOnly = true)
    public List<AsignacionDocente> listarAsignaciones() { return asignacionRepository.findAll(); }
    public AsignacionDocente guardarAsignacion(AsignacionDocente entidad) { return asignacionRepository.save(entidad); }

    @Transactional(readOnly = true)
    public List<Calificacion> listarCalificaciones() { return calificacionRepository.findAll(); }
    public Calificacion guardarCalificacion(Calificacion entidad) { return calificacionRepository.save(entidad); }

    @Transactional(readOnly = true)
    public List<ControlAsistencia> listarAsistencias() { return asistenciaRepository.findAll(); }
    public ControlAsistencia guardarAsistencia(ControlAsistencia entidad) { return asistenciaRepository.save(entidad); }

    @Transactional(readOnly = true)
    public List<Curso> listarCursos() { return cursoRepository.findAll(); }
    public Curso guardarCurso(Curso entidad) { return cursoRepository.save(entidad); }
    public Curso obtenerCurso(Long id) { return cursoRepository.findById(id).orElseThrow(() -> noEncontrado("Curso", id)); }

    @Transactional(readOnly = true)
    public List<Periodo> listarPeriodos() { return periodoRepository.findAll(); }
    public Periodo guardarPeriodo(Periodo entidad) { return periodoRepository.save(entidad); }
    public Periodo obtenerPeriodo(Long id) { return periodoRepository.findById(id).orElseThrow(() -> noEncontrado("Periodo", id)); }

    @Transactional(readOnly = true)
    public List<Rol> listarRoles() { return rolRepository.findAll(); }
    public Rol guardarRol(Rol entidad) { return rolRepository.save(entidad); }

    @Transactional(readOnly = true)
    public List<Permiso> listarPermisos() { return permisoRepository.findAll(); }
    public Permiso guardarPermiso(Permiso entidad) { return permisoRepository.save(entidad); }

    public RolPermiso guardarRolPermiso(RolPermiso entidad) { return rolPermisoRepository.save(entidad); }
    public Usuario guardarUsuario(Usuario entidad) { return usuarioRepository.save(entidad); }

    private EntityNotFoundException noEncontrado(String tipo, Long id) {
        return new EntityNotFoundException(tipo + " no encontrado: " + id);
    }
}
