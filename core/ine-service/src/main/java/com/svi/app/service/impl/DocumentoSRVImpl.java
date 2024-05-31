package com.svi.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.svi.app.dto.CounterDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.exception.DuplicityException;
import com.svi.app.model.Casilla;
import com.svi.app.model.Documento;
import com.svi.app.repository.CasillaRepository;
import com.svi.app.repository.DocumentoRepository;
import com.svi.app.service.DocumentoSRV;
import com.svi.app.util.StaticsConstants;

@Service
public class DocumentoSRVImpl implements DocumentoSRV {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentoSRVImpl.class);

	@Autowired
	private DocumentoRepository documentoRepository;

	@Autowired
	private CasillaRepository casillaRepository;

	@Override
	public Page<Documento> findAll(Integer filter, Pageable pageable) {
		if (filter.equals(1)) {// Todos
			return this.documentoRepository.findAllByOrderByEstatus(pageable);
		} else {
			boolean isVoice = filter.equals(3) ? true : false;
			return this.documentoRepository.findAByIsVoiceOrderByEstatus(isVoice, pageable);
		}
	}

	@Override
	public Page<Documento> getByFilters(Integer filter, String key, Pageable pageable) {
		if (filter.equals(1)) {// Todos
			return this.documentoRepository.getAllByFilters(key, pageable);
		} else {
			boolean isVoice = filter.equals(3) ? true : false;
			return this.documentoRepository.getByFilters(key, isVoice, pageable);
		}
	}

	@Override
	public CounterDTO contador() {
		CounterDTO counter = new CounterDTO();
		counter.noIniciado = this.casillaRepository.countByTerminada(false);
		counter.validado = this.casillaRepository.countByTerminada(true);
		return counter;
	}

	@Override
	public ResponseDTO<Documento> create(Documento entity) throws DuplicityException {
		if (this.documentoRepository.findByClave(entity.getClave()).isEmpty()) {
			if (validateCasilla(entity.getClave().toUpperCase())) {
				try {
					entity.setEstatus(StaticsConstants.STATUS_NO_INICIADO);
					entity.setClave(entity.getClave().toUpperCase());
					this.documentoRepository.save(entity);
					return new ResponseDTO<>(StaticsConstants.MESSAGE_CORRECT, StaticsConstants.SAVED_MESSAGE);
				} catch (Exception e) {
					LOG.error("create", e);
					throw e;
				}
			} else {
				throw new DuplicityException(StaticsConstants.CASILLA_NOT_FOUND);
			}
		} else {
			throw new DuplicityException(StaticsConstants.DUPLICATED_KEY);
		}
	}

	private boolean validateCasilla(String cadena) {
		int casillaC = 0;
		int casillaB = 0;
		int casillaE = 0;
		int seccion = Integer.parseInt(cadena.substring(0, 4));
		if (cadena.toLowerCase().endsWith("c")) {
			casillaC = Integer.parseInt(String.valueOf(cadena.charAt(4)));
		} else if (cadena.toLowerCase().endsWith("b")) {
			casillaB = Integer.parseInt(String.valueOf(cadena.charAt(4)));
		} else {
			casillaE = Integer.parseInt(String.valueOf(cadena.charAt(4)));
		}
		Casilla entity = this.casillaRepository.findBySeccionAndCasillabAndCasillacAndCasillae(seccion, casillaB,
				casillaC, casillaE);
		if (entity != null) {
			return true;
		}
		return false;
	}

	@Override
	public Documento createVoiceDocument(String clave) {
		Documento doc = new Documento();
		doc.setEstatus(StaticsConstants.STATUS_CAPTURA_1);
		doc.setIsVoice(Boolean.TRUE);
		doc.setClave(clave.toUpperCase());
		if (validateCasilla(clave)) {
			if (this.documentoRepository.findByClave(clave.toUpperCase()).isEmpty()) {
				return this.documentoRepository.save(doc);
			}
		}
		return null;

	}

	@Override
	public Documento getById(Integer id) {
		return this.documentoRepository.findById(id).orElse(null);
	}
}
