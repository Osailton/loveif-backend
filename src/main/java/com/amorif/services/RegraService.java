package com.amorif.services;

import org.springframework.stereotype.Service;

import com.amorif.entities.Regra;

import java.util.List;

@Service
public interface RegraService {

	public List<Regra> listarTodas();
    
}