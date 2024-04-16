
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
		
		sbQuery.append("SELECT f.doc_id, f.pan, f.pri, f.prd, f.morena, f.alianza, f.ci ");
		sbQuery.append("FROM formulario f ");
		sbQuery.append("INNER JOIN documento doc on f.doc_id = doc.id ");
		sbQuery.append("WHERE doc.estatus = '4' group by f.doc_id, f.pan, f.pri, f.prd, f.morena, f.alianza, f.ci ");

		Query query = entityManager.createNativeQuery(sbQuery.toString());
		List<Object[]> results = query.getResultList();
        for (int i = 0; i < results.size(); i++) {
        	DataDTO dto = new DataDTO();
        	dto.setDoc(Integer.parseInt(results.get(i)[0].toString()));
        	dto.setPan(Integer.parseInt(results.get(i)[1].toString()));
        	dto.setPri(Integer.parseInt(results.get(i)[2].toString()));
        	dto.setPrd(Integer.parseInt(results.get(i)[3].toString()));
        	dto.setMorena(Integer.parseInt(results.get(i)[4].toString()));
        	dto.setAlianza(Integer.parseInt(results.get(i)[5].toString()));
        	dto.setCi(Integer.parseInt(results.get(i)[6].toString()));
            list.add(dto);
        }
		return list;
	}
}
