package com.svi.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svi.app.dto.DataDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.model.Formulario;
import com.svi.app.service.FormularioSRV;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/formulario")
public class FormularioController {

	private static final Logger LOG = LoggerFactory.getLogger(FormularioController.class);

	@Autowired
	private FormularioSRV formularioSRV;

	@PostMapping
	@ResponseBody
	public ResponseEntity<ResponseDTO> save(@RequestBody Formulario entity) {
		try {
			return ResponseEntity.ok().body(this.formularioSRV.create(entity));
		} catch (Exception ex) {
			LOG.error("create", ex);
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{documentId}")
	@ResponseBody
	public Formulario getByDocumentId(@PathVariable Integer documentId) {
		return this.formularioSRV.getByDocumentId(documentId);
	}
	
	@GetMapping(value = "/graphic")
	@ResponseBody
	public DataDTO results() {
		return this.formularioSRV.results();
	}
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<ResponseDTO> update(@RequestBody Formulario formulario) {
		try {
			return ResponseEntity.ok().body(this.formularioSRV.update(formulario));
		} catch (Exception ex) {
			LOG.error("create", ex);
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
