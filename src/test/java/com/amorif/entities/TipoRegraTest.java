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
        Regra regra1 = Regra.builder().descricao("Regra de Teste 1").build();
        regras.add(regra1);

        // Cria um objeto TipoRegra com dados iniciais
        tipoRegra = TipoRegra.builder()
                .id(1L)
                .descricao("Teste")
                .fixo(true)
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
        assertThat(tipoRegra.isTemAluno()).isTrue();
        assertThat(tipoRegra.getFrequencia()).isEqualTo(5);
        assertThat(tipoRegra.getRegras()).isNotNull();
        assertThat(tipoRegra.getRegras()).hasSize(1);
        assertThat(tipoRegra.getRegras().get(0).getDescricao()).isEqualTo("Regra de Teste 1");
    }

    @Test
    void testAddRegraToTipoRegra() {
        // Adiciona uma nova regra e verifica se foi adicionada
        Regra regra2 = Regra.builder().descricao("Regra de Teste 2").build();
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
