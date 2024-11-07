package com.amorif.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amorif.entities.Regra;
import com.amorif.repository.RegraRepository;

import java.util.List;

@Service
public class RegraService {

    @Autowired
    private RegraRepository regraRepository;

    public List<Regra> listarTodas() {
        return regraRepository.findAll();
    }
}