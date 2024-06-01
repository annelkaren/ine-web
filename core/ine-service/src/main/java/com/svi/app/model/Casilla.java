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
@Table(name = "casillas")
public class Casilla {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "casillas_id_seq")
	@SequenceGenerator(name = "casillas_id_seq", sequenceName = "public.casillas_id_seq", allocationSize = 1)
	private Integer id;
	
	@Column(name = "seccion")
	private int seccion;
	
	@Column(name = "casillab")
	private int casillab;
	
	@Column(name = "casillac")
	private int casillac;
	
	@Column(name = "casillae")
	private int casillae;
	
	@Column(name = "importante")
	private boolean importante;
	
	@Column(name = "terminada")
	private boolean terminada;
	
	
	@Column(name = "prioridad")
	private int prioridad;
}
