package com.svi.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.app.model.Casilla;
import com.svi.app.repository.CasillaRepository;
import com.svi.app.service.CasillaSRV;

@Service
public class CasillaSRVImpl implements CasillaSRV {

	@Autowired
	private CasillaRepository casillaRepository;

	@Override
	public List<Casilla> getAll() {
		return this.casillaRepository.findAllByOrderBySeccionAscCasillabDescCasillacAsc();
	}

}
