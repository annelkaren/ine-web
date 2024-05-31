package com.svi.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.svi.app.model.Seccion;
import com.svi.app.service.SeccionSRV;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/seccion")
public class SeccionController {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionController.class);
	
	@Autowired
	private SeccionSRV seccionSRV;
	
	@GetMapping(value = "/all")
	@ResponseBody
	public List<Seccion> getAll() {
		return this.seccionSRV.getAll();
	}
}
