package dao;

import java.util.List;

public interface DAO<Entity> {
	
	Entity get(int id);
	
	List<Entity> getAll();
	
	void save(Entity entity);
	
	void update(Entity entity, String[] parms);
	
	void delete(Entity entity);

}
