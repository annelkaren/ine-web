package com.svi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svi.app.model.Seccion;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Integer> {

	List<Seccion> findAllByOrderBySeccionAsc();
}
