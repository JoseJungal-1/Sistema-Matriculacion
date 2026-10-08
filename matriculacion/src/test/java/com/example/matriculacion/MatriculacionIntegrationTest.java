package com.example.matriculacion;

import com.example.matriculacion.repository.UsuarioRepository;
import com.example.matriculacion.security.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class MatriculacionIntegrationTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        mockMvc = webAppContextSetup(context)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    void inicializaUsuarioAdministrador() {
        var admin = usuarioRepository.findByUsername("admin");

        Assertions.assertThat(admin).isPresent();
        Assertions.assertThat(admin.get().isActivo()).isTrue();
    }

    @Test
    void generaTokenJwtParaUsuarioAutenticado() {
        var user = User.withUsername("admin")
                .password("encoded")
                .authorities("ROLE_ADMIN")
                .build();

        String token = jwtService.generateToken(user);

        Assertions.assertThat(jwtService.username(token)).isEqualTo("admin");
    }

    @Test
    void rechazaEndpointProtegidoSinJwt() throws Exception {
        mockMvc.perform(get("/api/estudiantes"))
                .andExpect(status().isUnauthorized());
    }
}
