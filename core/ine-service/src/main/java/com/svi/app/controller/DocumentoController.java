package com.svi.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.svi.app.model.Documento;
import com.svi.app.service.DocumentoSRV;

import java.io.IOException;
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

	@GetMapping
	@ResponseBody
	public Page<Documento> findAll(@RequestParam(value = "filter") Integer filter, Pageable pageable) {
		return this.documentoSRV.findAll(filter, pageable);
	}

	@GetMapping(value = "/geyByFilters")
	@ResponseBody
	public Page<Documento> findByEmail(
			@RequestParam(value = "filter") Integer filter,
			@RequestParam(value = "key") String key, 
			Pageable pageable) {
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
    public ResponseEntity<String> createTemporalFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        Path path = Paths.get(System.getProperty("user.home"), "svi", "files", "temp");
        try {
            if (!path.toFile().exists()) {//si no existe la ruta
                Files.createDirectories(path);//se genera la ruta
            }
            if (file != null && !file.isEmpty()) {
                //filename:/home/xxx/svi/temp/UUID.ext
                String newname = UUID.randomUUID().toString().toUpperCase()
                        + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                Files.copy(file.getInputStream(), path
                        .resolve(newname));
                return ResponseEntity.ok(newname);
            }
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return ResponseEntity.badRequest().body(String.valueOf(file));
    }
}
