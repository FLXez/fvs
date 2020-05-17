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
	public List<Linienabfolge> getByBuslinien(int bidh, int bidr, String sortierung) {
		Query q = em.createQuery("SELECT l FROM Linienabfolge l WHERE l.buslinieH.bid = '" + bidh + "' AND l.buslinieR.bid ='" + bidr + "' ORDER BY l.position " + sortierung + "");	
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
