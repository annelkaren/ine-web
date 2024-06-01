package com.svi.app.service;

import com.svi.app.dto.DataDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.dto.ResultsDTO;
import com.svi.app.exception.DuplicityException;
import com.svi.app.model.Formulario;

public interface FormularioSRV {

	ResponseDTO<Formulario> create(Formulario entity) throws DuplicityException;;
	
	Formulario getByDocumentId(Integer documentId);
	
	ResponseDTO<Formulario> update(Formulario entity);
	
	DataDTO results();
	
	ResultsDTO getByCasilla();
}
