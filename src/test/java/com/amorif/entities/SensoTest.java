package com.amorif.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SensoTest {

    @Test
    void testConstructorAndGetters() {
        // Criando uma instância usando o construtor com argumentos
        Senso senso = new Senso(1L, "Organização");
        
        // Verificando se os valores foram atribuídos corretamente
        assertEquals(1L, senso.getId());
        assertEquals("Organização", senso.getDescricao());
    }

    @Test
    void testSetters() {
        // Criando uma instância vazia
        Senso senso = new Senso();
        
        // Configurando valores via setters
        senso.setId(2L);
        senso.setDescricao("Limpeza");
        
        // Verificando se os setters funcionaram corretamente
        assertEquals(2L, senso.getId());
        assertEquals("Limpeza", senso.getDescricao());
    }

    @Test
    void testBuilder() {
        // Criando uma instância usando o builder
        Senso senso = Senso.builder()
                           .id(3L)
                           .descricao("Disciplina")
                           .build();
        
        // Verificando os valores definidos pelo Builder
        assertNotNull(senso);
        assertEquals(3L, senso.getId());
        assertEquals("Disciplina", senso.getDescricao());
    }
}
