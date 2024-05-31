package com.svi.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "seccion")
public class Seccion {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seccion_id_seq")
	@SequenceGenerator(name = "seccion_id_seq", sequenceName = "public.seccion_id_seq", allocationSize = 1)
	private Integer id;
	
	@Column(name = "seccion")
	private String seccion;

}
