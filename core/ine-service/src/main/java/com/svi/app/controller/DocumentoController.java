package com.svi.app.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.svi.app.dto.CounterDTO;
import com.svi.app.dto.ResponseDTO;
import com.svi.app.dto.SimpleDTO;
import com.svi.app.model.Documento;
import com.svi.app.service.DocumentoSRV;
import com.svi.app.util.CommonMethods;
import com.svi.app.util.GCSManage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/documento")
public class DocumentoController {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentoController.class);

	@Autowired
	private DocumentoSRV documentoSRV;
	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping
	@ResponseBody
	public Page<Documento> findAll(@RequestParam(value = "filter") Integer filter, Pageable pageable) {
		return this.documentoSRV.findAll(filter, pageable);
	}

	@GetMapping(value = "/geyByFilters")
	@ResponseBody
	public Page<Documento> findByEmail(@RequestParam(value = "filter") Integer filter,
			@RequestParam(value = "key") String key, Pageable pageable) {
		return this.documentoSRV.getByFilters(filter, key, pageable);
	}

	@GetMapping(value = "/counter")
	@ResponseBody
	public CounterDTO contador() {
		return this.documentoSRV.contador();
	}

	@PostMapping
	@ResponseBody
	public ResponseEntity<ResponseDTO> save(@RequestBody Documento documento) {
		try {
			return ResponseEntity.ok().body(this.documentoSRV.create(documento));
		} catch (Exception ex) {
			LOG.error("create", ex);
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	public Documento getById(@PathVariable Integer id) {
		return this.documentoSRV.getById(id);
	}

	@PostMapping(value = "/files")
	@ResponseBody
	public ResponseEntity<ResponseDTO> save(@RequestParam(value = "clave", required = true) String clave,
			@RequestParam(value = "file", required = true) MultipartFile file) {
		Path path = Paths.get(System.getProperty("user.home"), "ine", "files");
		try {
			if (!path.toFile().exists()) {// si no existe la ruta
				Files.createDirectories(path);// se genera la ruta
			}

			if (file != null && !file.isEmpty()) {
				String newname = UUID.randomUUID().toString().toUpperCase()
						+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
				String name = CommonMethods.compressionAndUploadFile(file.getInputStream(), newname);

				return ResponseEntity.ok().body(this.documentoSRV.create(createDocument(name, clave)));
			} else {
				return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/file/{filename}")
	@ResponseBody
	public ResponseEntity<SimpleDTO> createTemporalFile(@PathVariable String filename) {
		try {
			GCSManage.downloadObject(filename);
			String path = "file:" + System.getProperty("user.home") + "/ine" + "/files/" + filename;
			Resource resource = resourceLoader.getResource(path);

			SimpleDTO dto = new SimpleDTO();
			dto.setName(FilenameUtils.getExtension(filename));
			dto.setValue(new String(org.apache.commons.codec.binary.Base64
					.encodeBase64(IOUtils.toByteArray(resource.getInputStream()))));

			Path file = Paths.get(System.getProperty("user.home"), "ine", "files");
			Files.deleteIfExists(file.resolve(filename));
			return ResponseEntity.ok().body(dto);
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Documento createDocument(String name, String clave) {
		Documento document = new Documento();
		document.setClave(clave);
		document.setUrl(name);
		document.setIsVoice(Boolean.FALSE);
		return document;
	}
}
