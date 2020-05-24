package dao;

import java.util.List;
/**
 * 
 * EJBs werden im Fahrplanverwaltungssystem als DAOs realisiert
 *
 * @author Felix & Silas
 *
 */
public interface DAO<Entity, DTO> {
	
	DTO get(int id);
	
	List<DTO> getAll();
	
	void save(DTO dto);
	
	void update(DTO dto, String[] parms);
	
	void delete(DTO dto);
}
