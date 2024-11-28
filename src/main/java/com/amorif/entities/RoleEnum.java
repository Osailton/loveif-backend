package com.amorif.entities;

import java.util.Arrays;
import java.util.List;

/**
 * @author osailton
 */

public enum RoleEnum {
	
	// Grouping with categories
    ROLE_AVAL("Aval"),
    ROLE_ALUNO("General"),
    ROLE_BIBLIOTECARIO("Aval"),
    ROLE_APOIO_ACADEMICO("Aval"),
    ROLE_SISTEMA("System"),
    ROLE_DOCENTE("General"),
    ROLE_ASSESSORIA_PEDAGOGICA("Aval"),
    ROLE_COEXPEIN("Aval"),
    ROLE_COORDENADOR_CURSO("Aval"),
    ROLE_ASSISTENCIA_ESTUDANTIL("Aval"),
    ROLE_ASSESSORIA_LABORATORIO("Aval"),
    ROLE_ADMINISTRADOR("Admin");

    private final String category;

    RoleEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    // Method to filter roles by category
    public static List<RoleEnum> getByCategory(String category) {
        return Arrays.stream(values())
                     .filter(role -> role.category.equals(category))
                     .toList();
    }
  
}
