package com.amorif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amorif.entities.Regra;
import com.amorif.services.RegraService;

import java.util.List;

@RestController
@RequestMapping("/api/regras")
public class RegraController {

    @Autowired
    private RegraService regraService;

    @GetMapping("listarTodas")
    public List<Regra> listarTodas() {
        return regraService.listarTodas();
    }
    
    @GetMapping("/permitidas")
    public List<Regra> listarRegrasPermitidasParaUsuario() {
        List<Regra> regras = regraService.listarRegrasPermitidasParaUsuario();
        return regras;
    }
    
}
