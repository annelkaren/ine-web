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
import com.svi.app.model.Documento;
import com.svi.app.repository.DocumentoRepository;
import com.svi.app.repository.FormularioRepository;
import com.svi.app.service.DocumentoSRV;
import com.svi.app.util.StaticsConstants;

@Service
public class DocumentoSRVImpl implements DocumentoSRV {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentoSRVImpl.class);

	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private FormularioRepository formularioRepository;

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
		counter.noIniciado = this.documentoRepository.countByEstatus(StaticsConstants.STATUS_NO_INICIADO);
		counter.captura1 = this.documentoRepository.countByEstatus(StaticsConstants.STATUS_CAPTURA_1);
		counter.captura2 = this.documentoRepository.countByEstatus("3");
		counter.validado = this.documentoRepository.countByEstatus("4");
		return counter;
	}

	@Override
	public ResponseDTO<Documento> create(Documento entity) throws DuplicityException {
		if (this.documentoRepository.findByClave(entity.getClave()).isEmpty()) {
			try {
				entity.setEstatus(StaticsConstants.STATUS_NO_INICIADO);
				this.documentoRepository.save(entity);
				return new ResponseDTO<>(StaticsConstants.MESSAGE_CORRECT, StaticsConstants.SAVED_MESSAGE);
			} catch (Exception e) {
				LOG.error("create", e);
				throw e;
			}
		} else {
			throw new DuplicityException(StaticsConstants.DUPLICATED_KEY);
		}
	}

	@Override
	public Documento createVoiceDocument() {
		Documento doc = new Documento();
		String formatted = String.format("%06d", this.documentoRepository.getNextValSequence());
		doc.setEstatus(StaticsConstants.STATUS_CAPTURA_1);
		doc.setIsVoice(Boolean.TRUE);
		doc.setClave("VOZ-" + formatted);
		return this.documentoRepository.save(doc);
	}

	@Override
	public Documento getById(Integer id) {
		return this.documentoRepository.findById(id).orElse(null);
	}
}
