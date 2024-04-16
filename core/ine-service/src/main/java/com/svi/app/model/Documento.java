package com.svi.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Data
@Entity
@Table(name = "documento")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento_id_seq")
	@SequenceGenerator(name = "documento_id_seq", sequenceName = "public.documento_id_seq", allocationSize = 1)
	private Integer id;

	@Version
	@Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
	private Integer version;

	@Column(name = "clave")
	private String clave;

	@Column(name = "url_doc")
	private String url;

	@Column(name = "estatus")
	private String estatus;

	@Column(name = "es_voz")
	private Boolean isVoice;
}
