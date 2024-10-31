package com.amorif.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest {

    private Role role;
    private Regra regra1;
    private Regra regra2;

    @BeforeEach
    void setUp() {
        // Criação das regras para associar ao Role
        regra1 = Regra.builder()
                .id(1L)
                .descricao("Regra de Teste 1")
                .build();

        regra2 = Regra.builder()
                .id(2L)
                .descricao("Regra de Teste 2")
                .build();

        // Lista de regras associada ao Role
        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        // Criação do objeto Role com a lista de regras
        role = Role.builder().id(1L).name(RoleEnum.ROLE_ALUNO.toString()).regras(regras).build();
    }

    @Test
    void testRoleAttributes() {
        // Verifica se os atributos foram atribuídos corretamente
        assertThat(role.getId()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("ROLE_ALUNO");
    }

    @Test
    void testRoleRegrasAssociation() {
        // Verifica se a associação com Regra está correta
        assertThat(role.getRegras()).isNotNull();
        assertThat(role.getRegras()).hasSize(2);
        assertThat(role.getRegras().get(0).getDescricao()).isEqualTo("Regra de Teste 1");
        assertThat(role.getRegras().get(1).getDescricao()).isEqualTo("Regra de Teste 2");
    }

    @Test
    void testRoleAuthority() {
        // Verifica o método getAuthority()
        assertThat(role.getAuthority()).isEqualTo("ROLE_ALUNO");
    }
}
