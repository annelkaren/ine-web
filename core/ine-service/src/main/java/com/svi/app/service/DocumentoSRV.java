package com.svi.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.svi.app.dto.CounterDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.exception.DuplicityException;
import com.svi.app.model.Documento;

public interface DocumentoSRV {

	Page<Documento> findAll(Integer filter, Pageable pageable);
	
	Page<Documento> getByFilters(Integer filter, String key, Pageable pageable);

	CounterDTO contador();
	
	ResponseDTO<Documento> create(Documento entity) throws DuplicityException;
	
	Documento createVoiceDocument();
	
	Documento getById(Integer id);
}
