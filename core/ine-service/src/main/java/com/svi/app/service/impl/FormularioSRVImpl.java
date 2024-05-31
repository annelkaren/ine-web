package com.svi.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.svi.app.database.FormularioDatabase;
import com.svi.app.dto.DataDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.exception.DuplicityException;
import com.svi.app.model.Casilla;
import com.svi.app.model.Documento;
import com.svi.app.model.Formulario;
import com.svi.app.repository.CasillaRepository;
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
	private CasillaRepository casillaRepository;

	@Autowired
	private DocumentoSRV documentoSRV;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JavaMailSender emailSender;

	@Override
	public ResponseDTO<Formulario> create(Formulario entity) throws DuplicityException {
		try {
			if (entity.getDocId() == 0) {
				Documento doc = this.documentoSRV.createVoiceDocument(entity.getCol4());
				if(doc == null) {
					throw new DuplicityException(StaticsConstants.INVALID_KEY);
				}
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

			Documento doc = this.documentoRepository.findById(entity.getDocId()).orElse(null);
			if (entity.getCounter() == 0 || entity.getCounter() == 5) {
				this.formularioRepository.save(entity);
				this.getCasilla(doc.getClave());
				doc.setEstatus(StaticsConstants.STATUS_VALIDADO);
			} else {
				doc.setEstatus(StaticsConstants.STATUS_NO_INICIADO);
				Formulario form = this.formularioRepository.findByDocId(doc.getId());
				doc.setCounter(entity.getCounter());
				this.formularioRepository.delete(form);
			}
			this.documentoRepository.save(doc);
		} catch (Exception e) {
			LOG.error("update", e);
			throw e;
		}
		return null;
	}

	private void getCasilla(String cadena) {
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
			entity.setTerminada(true);
			if (entity.isImportante()) {// Manda correo
				System.out.println("-------> MANDANDO CORREO ELECTRONICO");
				this.enviarCorreo(cadena);
			}
			this.casillaRepository.save(entity);
		}
	}

	private void enviarCorreo(String cadena) {
		Thread myThread = new Thread() {
			@Override
			public void run() {
				try {
					SimpleMailMessage message = new SimpleMailMessage();
					message.setFrom("qskconsulting@gmail.com");
					message.setTo(new String[] {"annelkaren@gmail.com", "gustavo.fernandez150@gmail.com" });
					message.setSubject("Casilla " + cadena);
					message.setText("La casilla " + cadena + " ha terminado de contabilizar sus votos");
					emailSender.send(message);
				} catch (Exception ex) {
					LOG.error("update", ex);
				}
			}
		};
		myThread.start();
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
			results.setPt(results.getPt() + dto.getPt());
			results.setPv(results.getPv() + dto.getPv());
			results.setMc(results.getMc() + dto.getMc());
			results.setPsi(results.getPsi() + dto.getPsi());
			results.setMorena(results.getMorena() + dto.getMorena());
			results.setAlianza(results.getAlianza() + dto.getAlianza());
			results.setFm(results.getFm() + dto.getFm());
			results.setCol1(results.getCol1() + dto.getCol1());
			results.setCol2(results.getCol2() + dto.getCol2());
		}
		return results;
	}

}
