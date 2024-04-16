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
	
	@Column(name = "morena")
	private String morena;
	
	@Column(name = "alianza")
	private String alianza;
	
	@Column(name = "ci")
	private String ci;
	
	@Column(name = "col_1")
	private String col1;
	
	@Column(name = "col_2")
	private String col2;
	
	@Column(name = "col_3")
	private String col3;
	
	@Column(name = "col_4")
	private String col4;
	
	@Column(name = "col_5")
	private String col5;
	
	@Column(name = "col_6")
	private String col6;
	
	@Column(name = "col_7")
	private String col7;
	
	@Column(name = "col_8")
	private String col8;
	
	@Column(name = "col_9")
	private String col9;
	
	@Column(name = "col_10")
	private String col10;
	
	@Column(name = "col_11")
	private String col11;
	
	@Column(name = "col_12")
	private String col12;
	
	@Column(name = "nulos")
	private String nulos;
	
	@Column(name = "total")
	private String total;
}
