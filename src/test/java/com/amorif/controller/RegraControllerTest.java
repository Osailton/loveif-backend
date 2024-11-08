package com.amorif.controller;

import com.amorif.config.security.JWTTokenProvider;
import com.amorif.config.security.TestSecurityConfig;
import com.amorif.entities.Regra;
import com.amorif.repository.TokenRepository;
import com.amorif.services.RegraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@WebMvcTest(RegraController.class)
@Import(TestSecurityConfig.class)
public class RegraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegraService regraService;
    
    @MockBean
    private JWTTokenProvider jwtTokenProvider;
    
    @MockBean
    private TokenRepository tokenRepository;

    @Test
    public void listarTodas_DeveRetornarListaDeRegras() throws Exception {
        // Configurar dados simulados
    	Regra regra1 =  Regra.builder().descricao("1 ponto por livro emprestado").operacao("SUM").valorMinimo(1).build();
        Regra regra2 = Regra.builder().descricao("12 ponto por livro emprestado").operacao("SUB").valorMinimo(1).build();

        // Configurar comportamento do serviço
        given(regraService.listarTodas()).willReturn(Arrays.asList(regra1, regra2));

        // Executar e verificar a resposta
        mockMvc.perform(MockMvcRequestBuilders.get("/api/regras/listarTodas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].descricao", is("1 ponto por livro emprestado")));
    }
}
