package com.svi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.svi.app.model.Formulario;

@Repository
public interface FormularioRepository extends JpaRepository<Formulario, Integer> {

	Formulario findByDocId(Integer docId);
}
