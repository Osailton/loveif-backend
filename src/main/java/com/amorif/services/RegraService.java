package com.amorif.services;

import com.amorif.entities.Regra;

import java.util.List;

public interface RegraService {

	public List<Regra> listarTodas();
	
	public List<Regra> listarRegrasPermitidasParaUsuario();
    
}