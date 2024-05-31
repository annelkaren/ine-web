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
@Table(name = "formulario")
public class Formulario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formulario_id_seq")
	@SequenceGenerator(name = "formulario_id_seq", sequenceName = "public.formulario_id_seq", allocationSize = 1)
	private Integer id;

	@Column(name = "doc_id")
	private Integer docId;
	
	@Column(name = "pan")
	private String pan;

	@Column(name = "pri")
	private String pri;

	@Column(name = "prd")
	private String prd;
	
	@Column(name = "pt")
	private String pt;
	
	@Column(name = "pv")
	private String pv;
	
	@Column(name = "mc")
	private String mc;
	
	@Column(name = "psi")
	private String psi;
	
	@Column(name = "morena")
	private String morena;
	
	@Column(name = "alianza")
	private String alianza;
	
	@Column(name = "fm")
	private String fm;
	
	@Column(name = "col_1")
	private String col1;
	
	@Column(name = "col_2")
	private String col2;
	
	@Column(name = "col_3")
	private String col3;
	
	@Column(name = "col_4")
	private String col4;
	
	@Column(name = "nulos")
	private String nulos;
	
	@Column(name = "total")
	private String total;
	
	@Column(name = "counter")
	private int counter;
}
