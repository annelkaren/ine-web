package com.svi.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.svi.app.database.FormularioDatabase;
import com.svi.app.dto.CasillasDTO;
import com.svi.app.dto.DataDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.dto.ResultsDTO;
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
				if (doc == null) {
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
					message.setTo(new String[] { "annelkaren@gmail.com", "gustavo.fernandez150@gmail.com" });
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

	@Override
	public ResultsDTO getByCasilla() {
		ResultsDTO dto = new ResultsDTO();
		DataDTO results50 = new DataDTO();
		DataDTO results60 = new DataDTO();
		DataDTO results70 = new DataDTO();
		DataDTO results80 = new DataDTO();
		FormularioDatabase connection = new FormularioDatabase();
		List<DataDTO> list = connection.getVotos(entityManager);

		List<Integer> casillas50 = connection.getCasillas(entityManager, 50);
		List<Integer> casillas60 = connection.getCasillas(entityManager, 60);
		List<Integer> casillas70 = connection.getCasillas(entityManager, 70);
		List<Integer> casillas80 = connection.getCasillas(entityManager, 80);

		CasillasDTO casillaDTO50 = new CasillasDTO();
		CasillasDTO casillaDTO60 = new CasillasDTO();
		CasillasDTO casillaDTO70 = new CasillasDTO();
		CasillasDTO casillaDTO80 = new CasillasDTO();
		for (DataDTO data : list) {
			for (Integer casilla : casillas50) {
				if (data.getClave().trim().startsWith(String.valueOf(casilla))) {
					results50.setPan(results50.getPan() + data.getPan());
					results50.setPri(results50.getPri() + data.getPri());
					results50.setPrd(results50.getPrd() + data.getPrd());
					results50.setPt(results50.getPt() + data.getPt());
					results50.setPv(results50.getPv() + data.getPv());
					results50.setMc(results50.getMc() + data.getMc());
					results50.setPsi(results50.getPsi() + data.getPsi());
					results50.setMorena(results50.getMorena() + data.getMorena());
					results50.setAlianza(results50.getAlianza() + data.getAlianza());
					results50.setFm(results50.getFm() + data.getFm());
					results50.setCol1(results50.getCol1() + data.getCol1());
					results50.setCol2(results50.getCol2() + data.getCol2());
					casillaDTO50.setData(results50);
				}
			}

			for (Integer casilla : casillas60) {
				if (data.getClave().trim().startsWith(String.valueOf(casilla))) {
					results60.setPan(results60.getPan() + data.getPan());
					results60.setPri(results60.getPri() + data.getPri());
					results60.setPrd(results60.getPrd() + data.getPrd());
					results60.setPt(results60.getPt() + data.getPt());
					results60.setPv(results60.getPv() + data.getPv());
					results60.setMc(results60.getMc() + data.getMc());
					results60.setPsi(results60.getPsi() + data.getPsi());
					results60.setMorena(results60.getMorena() + data.getMorena());
					results60.setAlianza(results60.getAlianza() + data.getAlianza());
					results60.setFm(results60.getFm() + data.getFm());
					results60.setCol1(results60.getCol1() + data.getCol1());
					results60.setCol2(results60.getCol2() + data.getCol2());
					casillaDTO60.setData(results60);
				}
			}

			for (Integer casilla : casillas70) {
				if (data.getClave().trim().startsWith(String.valueOf(casilla))) {
					results70.setPan(results70.getPan() + data.getPan());
					results70.setPri(results70.getPri() + data.getPri());
					results70.setPrd(results70.getPrd() + data.getPrd());
					results70.setPt(results70.getPt() + data.getPt());
					results70.setPv(results70.getPv() + data.getPv());
					results70.setMc(results70.getMc() + data.getMc());
					results70.setPsi(results70.getPsi() + data.getPsi());
					results70.setMorena(results70.getMorena() + data.getMorena());
					results70.setAlianza(results70.getAlianza() + data.getAlianza());
					results70.setFm(results70.getFm() + data.getFm());
					results70.setCol1(results70.getCol1() + data.getCol1());
					results70.setCol2(results70.getCol2() + data.getCol2());
					casillaDTO70.setData(results70);
				}
			}

			for (Integer casilla : casillas80) {
				if (data.getClave().trim().startsWith(String.valueOf(casilla))) {
					results80.setPan(results80.getPan() + data.getPan());
					results80.setPri(results80.getPri() + data.getPri());
					results80.setPrd(results80.getPrd() + data.getPrd());
					results80.setPt(results80.getPt() + data.getPt());
					results80.setPv(results80.getPv() + data.getPv());
					results80.setMc(results80.getMc() + data.getMc());
					results80.setPsi(results80.getPsi() + data.getPsi());
					results80.setMorena(results80.getMorena() + data.getMorena());
					results80.setAlianza(results80.getAlianza() + data.getAlianza());
					results80.setFm(results80.getFm() + data.getFm());
					results80.setCol1(results80.getCol1() + data.getCol1());
					results80.setCol2(results80.getCol2() + data.getCol2());
					casillaDTO80.setData(results80);
				}
			}
		}
		casillaDTO50.setTerminado(connection.getTerminada(entityManager, 50));
		casillaDTO60.setTerminado(connection.getTerminada(entityManager, 60));
		casillaDTO70.setTerminado(connection.getTerminada(entityManager, 70));
		casillaDTO80.setTerminado(connection.getTerminada(entityManager, 80));
		dto.setCasilla50(casillaDTO50);
		dto.setCasilla60(casillaDTO60);
		dto.setCasilla70(casillaDTO70);
		dto.setCasilla80(casillaDTO80);
		return dto;
	}

}
