package com.svi.app.service;

import com.svi.app.dto.DataDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.model.Formulario;

public interface FormularioSRV {

	ResponseDTO<Formulario> create(Formulario entity);
	
	Formulario getByDocumentId(Integer documentId);
	
	ResponseDTO<Formulario> update(Formulario entity);
	
	DataDTO results();
}
