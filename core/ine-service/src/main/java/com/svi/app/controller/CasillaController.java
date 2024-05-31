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

import com.svi.app.model.Casilla;
import com.svi.app.service.CasillaSRV;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/casilla")
public class CasillaController {

	private static final Logger LOG = LoggerFactory.getLogger(CasillaController.class);
	
	@Autowired
	private CasillaSRV casillaSRV;
	
	@GetMapping
	@ResponseBody
	public List<Casilla> findAll() {
		return this.casillaSRV.getAll();
	}
}
