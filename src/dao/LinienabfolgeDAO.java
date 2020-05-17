package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Linienabfolge;

@Stateless
@LocalBean
public class LinienabfolgeDAO implements DAO<Linienabfolge> {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Linienabfolge get(int id) {
		
		return em.find(Linienabfolge.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Linienabfolge> getByBuslinie(int bid) {
		Query q = em.createQuery("SELECT l FROM Linienabfolge l WHERE l.buslinie.bid = '" + bid + "' ORDER BY l.position ASC");	
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Linienabfolge> getAll() {
		Query q = em.createQuery("SELECT l FROM Linienabfolge l");
		return q.getResultList();
	}

	@Override
	public void save(Linienabfolge linienabfolge) {
		em.persist(linienabfolge);
	}

	@Override
	public void update(Linienabfolge linienabfolge, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)
		em.merge(linienabfolge);
	}

	public void update(Linienabfolge linienabfolge) {
		//TODO Parms parsen (siehe UserDAO)
		em.merge(linienabfolge);
	}
	
	@Override
	public void delete(Linienabfolge linienabfolge) {
		em.remove(linienabfolge);		
	}
}
