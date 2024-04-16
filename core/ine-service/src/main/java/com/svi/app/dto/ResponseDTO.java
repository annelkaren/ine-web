package com.svi.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseDTO<E> {

	private List<E> list;
	private E entity;
	private Integer typeMessage;
	private String codeMessage;

	public ResponseDTO() {

	}

	public ResponseDTO(E data) {
		this.entity = data;
	}

	public ResponseDTO(List<E> data) {
		this.list = data;
	}

	public ResponseDTO(Integer typeMessage, String codeMessage) {
		this.typeMessage = typeMessage;
		this.codeMessage = codeMessage;
	}
}
