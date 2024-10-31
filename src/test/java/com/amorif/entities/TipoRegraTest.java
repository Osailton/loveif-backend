package com.amorif.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TipoRegraTest {

    @Test
    public void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        boolean fixo = true;
        String operacao = "some_operation";
        boolean temAluno = false;
        int frequencia = 5;

        // Act
        TipoRegra tipoRegra = new TipoRegra(id, fixo, operacao, temAluno, frequencia);

        // Assert
        assertNotNull(tipoRegra);
        assertEquals(id, tipoRegra.getId());
        assertEquals(fixo, tipoRegra.isFixo());
        assertEquals(operacao, tipoRegra.getOperacao());
        assertEquals(temAluno, tipoRegra.isTemAluno());
        assertEquals(frequencia, tipoRegra.getFrequencia());
    }

    @Test
    public void testSetters() {
        // Arrange
        TipoRegra tipoRegra = new TipoRegra();

        // Act
        tipoRegra.setId(2L);
        tipoRegra.setFixo(false);
        tipoRegra.setOperacao("another_operation");
        tipoRegra.setTemAluno(true);
        tipoRegra.setFrequencia(10);

        // Assert
        assertEquals(2L, tipoRegra.getId());
        assertEquals(false, tipoRegra.isFixo());
        assertEquals("another_operation", tipoRegra.getOperacao());
        assertEquals(true, tipoRegra.isTemAluno());
        assertEquals(10, tipoRegra.getFrequencia());
    }

    @Test
    public void testBuilder() {
        // Act
        TipoRegra tipoRegra = TipoRegra.builder()
                .id(3L)
                .fixo(true)
                .operacao("operation_with_builder")
                .temAluno(false)
                .frequencia(15)
                .build();

        // Assert
        assertNotNull(tipoRegra);
        assertEquals(3L, tipoRegra.getId());
        assertEquals(true, tipoRegra.isFixo());
        assertEquals("operation_with_builder", tipoRegra.getOperacao());
        assertEquals(false, tipoRegra.isTemAluno());
        assertEquals(15, tipoRegra.getFrequencia());
    }
}
