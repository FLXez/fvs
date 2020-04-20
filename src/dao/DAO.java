package dao;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, DTO> {
	
	Optional<Entity> get(int id);
	
	List<Entity> getAll();
	
	void save(Entity entity);
	
	void update(Entity entity, String[] parms);
	
	void delete(Entity entity);

}
