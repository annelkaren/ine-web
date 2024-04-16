package com.svi.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.app.database.FormularioDatabase;
import com.svi.app.dto.DataDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.model.Documento;
import com.svi.app.model.Formulario;
import com.svi.app.repository.DocumentoRepository;
import com.svi.app.repository.FormularioRepository;
import com.svi.app.service.DocumentoSRV;
import com.svi.app.service.FormularioSRV;
import com.svi.app.util.StaticsConstants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class FormularioSRVImpl implements FormularioSRV {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentoSRVImpl.class);

	@Autowired
	private FormularioRepository formularioRepository;

	@Autowired
	private DocumentoRepository documentoRepository;

	@Autowired
	private DocumentoSRV documentoSRV;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDTO<Formulario> create(Formulario entity) {
		try {
			if (entity.getDocId() == 0) {
				Documento doc = this.documentoSRV.createVoiceDocument();
				entity.setDocId(doc.getId());
			} else {
				Documento doc = this.documentoRepository.findById(entity.getDocId()).orElse(null);
				doc.setEstatus(StaticsConstants.STATUS_CAPTURA_1);
				this.documentoRepository.save(doc);
			}
			this.formularioRepository.save(entity);
			return new ResponseDTO<>(StaticsConstants.MESSAGE_CORRECT, StaticsConstants.SAVED_MESSAGE);
		} catch (Exception e) {
			LOG.error("create", e);
			throw e;
		}
	}

	@Override
	public Formulario getByDocumentId(Integer documentId) {
		return this.formularioRepository.findByDocId(documentId);
	}

	@Override
	public ResponseDTO<Formulario> update(Formulario entity) {
		try {
			this.formularioRepository.save(entity);
			Documento doc = this.documentoRepository.findById(entity.getDocId()).orElse(null);
			doc.setEstatus(StaticsConstants.STATUS_VALIDADO);
			this.formularioRepository.save(entity);
		} catch (Exception e) {
			LOG.error("update", e);
			throw e;
		}
		return null;
	}

	@Override
	public DataDTO results() {
		DataDTO results = new DataDTO();
		FormularioDatabase connection = new FormularioDatabase();
		List<DataDTO> list = connection.getData(entityManager);
		for (DataDTO dto : list) {
			results.setPan(results.getPan() + dto.getPan());
			results.setPri(results.getPri() + dto.getPri());
			results.setPrd(results.getPrd() + dto.getPrd());
			results.setMorena(results.getMorena() + dto.getMorena());
			results.setAlianza(results.getAlianza() + dto.getAlianza());
			results.setCi(results.getCi() + dto.getCi());
		}
		return results;
	}

}
