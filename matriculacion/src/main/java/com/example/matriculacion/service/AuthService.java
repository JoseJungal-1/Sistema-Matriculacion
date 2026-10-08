package com.example.matriculacion.service;

import com.example.matriculacion.dto.AuthResponse;
import com.example.matriculacion.dto.LoginRequest;
import com.example.matriculacion.dto.RegistroUsuarioRequest;
import com.example.matriculacion.entity.Usuario;
import com.example.matriculacion.repository.UsuarioRepository;
import com.example.matriculacion.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository repository, PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registrar(RegistroUsuarioRequest request) {
        if (repository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        repository.save(Usuario.builder().username(request.username())
                .password(passwordEncoder.encode(request.password())).activo(true).build());
    }

    @Transactional(readOnly = true)
    public AuthResponse autenticar(LoginRequest request) {
        Usuario usuario = repository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Usuario o contraseña inválidos"));
        if (!usuario.isActivo() || !passwordEncoder.matches(request.password(), usuario.getPassword())) {
            throw new BadCredentialsException("Usuario o contraseña inválidos");
        }

        UserDetails user = org.springframework.security.core.userdetails.User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRoles().stream()
                        .flatMap(rol -> java.util.stream.Stream.of("ROLE_" + rol.getNombre()))
                        .toArray(String[]::new))
                .build();
        return new AuthResponse(jwtService.generateToken(user), "Bearer", jwtService.expirationMs());
    }
}
