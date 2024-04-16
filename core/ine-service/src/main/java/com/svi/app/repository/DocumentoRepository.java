package com.svi.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.svi.app.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

	Page<Documento> findAllByOrderByEstatus(Pageable pageable);

	Page<Documento> findAByIsVoiceOrderByEstatus(Boolean isVoice, Pageable pageable);

	@Query(value = "SELECT * FROM documento WHERE " + "unaccent(LOWER(clave)) ILIKE '%'||unaccent(LOWER(?1))||'%' "
			+ "AND es_voz = ?2", nativeQuery = true)
	Page<Documento> getByFilters(String key, Boolean isVoice, Pageable pageable);

	@Query(value = "SELECT * FROM documento WHERE "
			+ "unaccent(LOWER(clave)) ILIKE '%'||unaccent(LOWER(?1))||'%' ", nativeQuery = true)
	Page<Documento> getAllByFilters(String key, Pageable pageable);

	long countByEstatus(String estatus);

	List<Documento> findByClave(String clave);

	@Query(value = "SELECT nextval('public.documento_id_seq')", nativeQuery = true)
	public Integer getNextValSequence();
}
