package com.amorif.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amorif.dto.response.AnoLetivoDtoResponse;
import com.amorif.entities.AnoLetivo;
import com.amorif.repository.AnoLetivoRepository;
import com.amorif.services.AnoLetivoService;

/**
 * @author osailton
 */

@Service
public class AnoLetivoServiceImpl implements AnoLetivoService {

	private final AnoLetivoRepository anoLetivoRepository;

	public AnoLetivoServiceImpl(AnoLetivoRepository anoLetivoRepository) {
		this.anoLetivoRepository = anoLetivoRepository;
	}

	@Override
	public List<AnoLetivoDtoResponse> listAll() {
		return anoLetivoRepository.findAll().stream().map(e -> dtoFromAnoLetivo(e)).collect(Collectors.toList());
	}

	private AnoLetivoDtoResponse dtoFromAnoLetivo(AnoLetivo anoLetivo) {
		return new AnoLetivoDtoResponse().builder().id(anoLetivo.getId()).anoLetivo(anoLetivo.getAno())
				.status(anoLetivo.isAberto() ? "Aberto" : "Fechado").build();
	}

}
