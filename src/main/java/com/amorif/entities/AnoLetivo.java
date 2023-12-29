package com.amorif.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AnoLetivo")
public class AnoLetivo {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

	public AnoLetivo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
