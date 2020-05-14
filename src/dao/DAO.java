package dao;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, DTO> {
	
	Optional<Entity> get(int id);
	
	List<Entity> getAll();
	
	boolean save(Entity entity);
	
	boolean update(Entity entity, String[] parms);
	
	boolean delete(Entity entity);

}
