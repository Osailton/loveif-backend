package com.amorif.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegraTest {

    private Regra regra;
    private Senso senso;
    private TipoRegra tipoRegra;

    @BeforeEach
    void setUp() {
        // Inicializa as entidades relacionadas
        senso = Senso.builder()
                .id(1L)
                .descricao("Senso de Teste")
                .build();

        tipoRegra = TipoRegra.builder()
                .id(1L)
                .fixo(true)
                .operacao("Operação de Teste")
                .temAluno(true)
                .frequencia(5)
                .build();

        // Cria um objeto Regra com dados iniciais
        regra = Regra.builder()
                .id(1L)
                .descricao("Regra de Teste")
                .valorMinimo(10)
                .valorMaximo(20)
                .senso(senso)
                .tipoRegra(tipoRegra)
                .build();
    }

    @Test
    void testRegraAttributes() {
        // Verifica se os atributos foram atribuídos corretamente
        assertThat(regra.getId()).isEqualTo(1L);
        assertThat(regra.getDescricao()).isEqualTo("Regra de Teste");
        assertThat(regra.getValorMinimo()).isEqualTo(10);
        assertThat(regra.getValorMaximo()).isEqualTo(20);
    }

    @Test
    void testRegraSensoAssociation() {
        // Verifica se a associação com Senso está correta
        assertThat(regra.getSenso()).isNotNull();
        assertThat(regra.getSenso().getDescricao()).isEqualTo("Senso de Teste");
    }

    @Test
    void testRegraTipoRegraAssociation() {
        // Verifica se a associação com TipoRegra está correta
        assertThat(regra.getTipoRegra()).isNotNull();
        assertThat(regra.getTipoRegra().getOperacao()).isEqualTo("Operação de Teste");
        assertThat(regra.getTipoRegra().isFixo()).isTrue();
    }
}
