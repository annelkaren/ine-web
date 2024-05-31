package com.svi.app.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svi.app.model.Seccion;
import com.svi.app.repository.SeccionRepository;
import com.svi.app.service.SeccionSRV;

@Service
public class SeccionSRVImpl implements SeccionSRV {

	private static final Logger LOG = LoggerFactory.getLogger(SeccionSRVImpl.class);

	@Autowired
	private SeccionRepository seccionRepository;
	
	@Override
	public List<Seccion> getAll() {
		return this.seccionRepository.findAllByOrderBySeccionAsc();
	}
	
	
}
