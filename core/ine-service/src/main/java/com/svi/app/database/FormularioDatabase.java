
package com.svi.app.database;

import java.util.ArrayList;
import java.util.List;

import com.svi.app.dto.DataDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class FormularioDatabase {

	public List<DataDTO> getData(EntityManager entityManager) {
		StringBuilder sbQuery = new StringBuilder();
		List<DataDTO> list = new ArrayList<DataDTO>();
		
		sbQuery.append("SELECT f.doc_id, f.pan, f.pri, f.prd, f.pt, f.pv, f.mc, f.psi, f.morena, f.alianza, f.fm, f.col_1, f.col_2 ");
		sbQuery.append("FROM formulario f ");
		sbQuery.append("INNER JOIN documento doc on f.doc_id = doc.id ");
		sbQuery.append("WHERE doc.estatus = '4' group by f.doc_id, f.pan, f.pri, f.prd, f.pt, f.pv, f.mc, f.psi, f.morena, f.alianza, f.fm, f.col_1, f.col_2 ");

		Query query = entityManager.createNativeQuery(sbQuery.toString());
		List<Object[]> results = query.getResultList();
        for (int i = 0; i < results.size(); i++) {
        	DataDTO dto = new DataDTO();
        	dto.setDoc(Integer.parseInt(results.get(i)[0].toString()));
        	dto.setPan(Integer.parseInt(results.get(i)[1].toString()));
        	dto.setPri(Integer.parseInt(results.get(i)[2].toString()));
        	dto.setPrd(Integer.parseInt(results.get(i)[3].toString()));
        	dto.setPt(Integer.parseInt(results.get(i)[4].toString()));
        	dto.setPv(Integer.parseInt(results.get(i)[5].toString()));
        	dto.setMc(Integer.parseInt(results.get(i)[6].toString()));
        	dto.setPsi(Integer.parseInt(results.get(i)[7].toString()));
        	dto.setMorena(Integer.parseInt(results.get(i)[8].toString()));
        	dto.setAlianza(Integer.parseInt(results.get(i)[9].toString()));
        	dto.setFm(Integer.parseInt(results.get(i)[10].toString()));
        	dto.setCol1(Integer.parseInt(results.get(i)[11].toString()));
        	dto.setCol2(Integer.parseInt(results.get(i)[12].toString()));
            list.add(dto);
        }
		return list;
	}
	
	public List<DataDTO> getVotos(EntityManager entityManager) {
		StringBuilder sbQuery = new StringBuilder();
		List<DataDTO> list = new ArrayList<DataDTO>();
		
		sbQuery.append("SELECT doc.clave, f.doc_id, f.pan, f.pri, f.prd, f.pt, f.pv, f.mc, f.psi, f.morena, f.alianza, f.fm, f.col_1, f.col_2 ");
		sbQuery.append("FROM formulario f ");
		sbQuery.append("INNER JOIN documento doc on f.doc_id = doc.id ");
		sbQuery.append("WHERE doc.estatus = '4' group by doc.clave, f.doc_id, f.pan, f.pri, f.prd, f.pt, f.pv, f.mc, f.psi, f.morena, f.alianza, f.fm, f.col_1, f.col_2 ");

		Query query = entityManager.createNativeQuery(sbQuery.toString());
		List<Object[]> results = query.getResultList();
        for (int i = 0; i < results.size(); i++) {
        	DataDTO dto = new DataDTO();
        	dto.setClave(results.get(i)[0].toString());
        	dto.setDoc(Integer.parseInt(results.get(i)[1].toString()));
        	dto.setPan(Integer.parseInt(results.get(i)[2].toString()));
        	dto.setPri(Integer.parseInt(results.get(i)[3].toString()));
        	dto.setPrd(Integer.parseInt(results.get(i)[4].toString()));
        	dto.setPt(Integer.parseInt(results.get(i)[5].toString()));
        	dto.setPv(Integer.parseInt(results.get(i)[6].toString()));
        	dto.setMc(Integer.parseInt(results.get(i)[7].toString()));
        	dto.setPsi(Integer.parseInt(results.get(i)[8].toString()));
        	dto.setMorena(Integer.parseInt(results.get(i)[9].toString()));
        	dto.setAlianza(Integer.parseInt(results.get(i)[10].toString()));
        	dto.setFm(Integer.parseInt(results.get(i)[11].toString()));
        	dto.setCol1(Integer.parseInt(results.get(i)[12].toString()));
        	dto.setCol2(Integer.parseInt(results.get(i)[13].toString()));
            list.add(dto);
        }
		return list;
	}
	
	public List<Integer> getCasillas(EntityManager entityManager, Integer casilla) {
		StringBuilder sbQuery = new StringBuilder();
		
		sbQuery.append("SELECT distinct h.seccion from casillas h where prioridad = " + casilla);
		
		Query query = entityManager.createNativeQuery(sbQuery.toString());
		List<Integer> results = query.getResultList();
		return results;
	}
	
	public boolean getTerminada(EntityManager entityManager, Integer casilla) {
		StringBuilder sbQuery = new StringBuilder();
		
		sbQuery.append("SELECT distinct h.terminada from casillas h where prioridad = " + casilla);
		
		Query query = entityManager.createNativeQuery(sbQuery.toString());
		List<Boolean> results = query.getResultList();
		if(results.size() > 1) {
			return false;
		} else {
			return results.get(0);
		}
	}
}
