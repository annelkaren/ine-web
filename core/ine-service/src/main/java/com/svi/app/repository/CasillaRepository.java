package com.svi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svi.app.model.Casilla;

@Repository
public interface CasillaRepository extends JpaRepository<Casilla, Integer> {

	Casilla findBySeccionAndCasillabAndCasillacAndCasillae(int seccion, int b, int c, int e);
	
	long countByTerminada(Boolean terminada); 
	
	List<Casilla> findAllByOrderBySeccionAscCasillabDescCasillacAsc();
}
