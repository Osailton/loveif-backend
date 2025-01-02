package com.amorif.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.amorif.dto.request.AnoLetivoDtoRequest;
import com.amorif.dto.response.AnoLetivoDtoResponse;
import com.amorif.entities.AnoLetivo;
import com.amorif.exceptions.InvalidArgumentException;
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
	public AnoLetivoDtoResponse getLastActiveAnoLetivo() {
        return dtoFromAnoLetivo(anoLetivoRepository.getLastActiveAnoLetivo());
    }

	@Override
	public List<AnoLetivoDtoResponse> listAll() {
		return anoLetivoRepository.findAll().stream().map(e -> dtoFromAnoLetivo(e)).collect(Collectors.toList());
	}

	@Override
	public AnoLetivoDtoResponse postAnoLetivo(AnoLetivoDtoRequest anoLetivoRequest) {
		if (anoLetivoRequest.getId() != null) {

			AnoLetivo ano = this.anoLetivoRepository.getReferenceById(anoLetivoRequest.getId());
			if (ano != null) {
				ano.setAno(anoLetivoRequest.getAnoLetivo());
				ano.setAberto(anoLetivoRequest.isAberto());
				ano = anoLetivoRepository.save(ano);
				return this.dtoFromAnoLetivo(ano);
			} else {
				throw new InvalidArgumentException("Ano letivo não existe!");
			}
		} else {
			AnoLetivo ano = this.anoLetivoFromDto(anoLetivoRequest);
			ano = anoLetivoRepository.save(ano);
			return this.dtoFromAnoLetivo(ano);
		}
	}

	private AnoLetivoDtoResponse dtoFromAnoLetivo(AnoLetivo anoLetivo) {
		return AnoLetivoDtoResponse.builder().id(anoLetivo.getId()).anoLetivo(anoLetivo.getAno())
				.status(anoLetivo.isAberto() ? "Aberto" : "Fechado").build();
	}

	private AnoLetivo anoLetivoFromDto(AnoLetivoDtoRequest anoLetivoDtoRequest) {
		return new AnoLetivo.Builder().ano(anoLetivoDtoRequest.getAnoLetivo()).aberto(anoLetivoDtoRequest.isAberto())
				.build();
	}

}
