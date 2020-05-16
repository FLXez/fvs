package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Fahrt;

@Stateless
@LocalBean
public class FahrtDAO implements DAO<Fahrt> {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Fahrt get(int id) {
		return em.find(Fahrt.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fahrt> getAll() {
		Query q = em.createQuery("SELECT f FROM Fahrt f");
		return q.getResultList();
	}

	@Override
	public void save(Fahrt fahrt) {
		em.persist(fahrt);
	}

	@Override
	public void update(Fahrt fahrt, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)		
		em.merge(fahrt);
	}

	@Override
	public void delete(Fahrt fahrt) {
		em.remove(fahrt);
	}
}
