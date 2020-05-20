package dao;

import java.util.List;

public interface DAO<Entity, DTO> {
	
	DTO get(int id);
	
	List<DTO> getAll();
	
	void save(DTO dto);
	
	void update(DTO dto, String[] parms);
	
	void delete(DTO dto);
}
