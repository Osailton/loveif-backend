package com.amorif.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amorif.entities.Regra;
import com.amorif.repository.RegraRepository;

@Service
public class RegraServiceImpl {
	
	@Autowired
    private RegraRepository regraRepository;

    public List<Regra> listarTodas() {
        return regraRepository.findAll();
    }
    
}
