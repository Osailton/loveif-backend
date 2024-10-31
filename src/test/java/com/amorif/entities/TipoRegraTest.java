package com.amorif.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoRegraTest {

    private TipoRegra tipoRegra;
    private List<Regra> regras;

    @BeforeEach
    void setUp() {
        // Inicializa uma lista de regras para associar ao TipoRegra
        regras = new ArrayList<>();
        Regra regra1 = new Regra();
        regra1.setDescricao("Regra de Teste 1");
        regras.add(regra1);

        // Cria um objeto TipoRegra com dados iniciais
        tipoRegra = TipoRegra.builder()
                .id(1L)
                .fixo(true)
                .operacao("Operação de Teste")
                .temAluno(true)
                .frequencia(5)
                .regras(regras)
                .build();
    }

    @Test
    void testTipoRegraAttributes() {
        // Verifica se os atributos foram atribuídos corretamente
        assertThat(tipoRegra.getId()).isEqualTo(1L);
        assertThat(tipoRegra.isFixo()).isTrue();
        assertThat(tipoRegra.getOperacao()).isEqualTo("Operação de Teste");
        assertThat(tipoRegra.isTemAluno()).isTrue();
        assertThat(tipoRegra.getFrequencia()).isEqualTo(5);
        assertThat(tipoRegra.getRegras()).isNotNull();
        assertThat(tipoRegra.getRegras()).hasSize(1);
        assertThat(tipoRegra.getRegras().get(0).getDescricao()).isEqualTo("Regra de Teste 1");
    }

    @Test
    void testAddRegraToTipoRegra() {
        // Adiciona uma nova regra e verifica se foi adicionada
        Regra regra2 = new Regra();
        regra2.setDescricao("Regra de Teste 2");
        tipoRegra.getRegras().add(regra2);

        assertThat(tipoRegra.getRegras()).hasSize(2);
        assertThat(tipoRegra.getRegras().get(1).getDescricao()).isEqualTo("Regra de Teste 2");
    }

    @Test
    void testRemoveRegraFromTipoRegra() {
        // Remove a regra e verifica se a lista fica vazia
        tipoRegra.getRegras().clear();

        assertThat(tipoRegra.getRegras()).isEmpty();
    }
}
