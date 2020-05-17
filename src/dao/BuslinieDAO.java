package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Buslinie;

@Stateless
@LocalBean
public class BuslinieDAO implements DAO<Buslinie> {

	@PersistenceContext
	EntityManager em;
	
    @Override
	public Buslinie get(int id) {
		return em.find(Buslinie.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Buslinie> getAll() {
		Query q = em.createQuery("SELECT b FROM Buslinie b");
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Buslinie> getByNummer(int nummer) {
		Query q = em.createQuery("SELECT b FROM Buslinie b WHERE b.nummer='" + nummer + "' ORDER BY b.richtung ASC");
		return q.getResultList();
	}
	
	public Buslinie getByNummerRichtung(int nummer, String richtung) {
		Query q = em.createQuery("SELECT b FROM Buslinie b WHERE b.nummer= '" + nummer + "' AND b.richtung='" + richtung + "'", Buslinie.class);
		return (Buslinie) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Buslinie> existsByNummer(int nummer) {
		Query q = em.createQuery("SELECT b.nummer FROM Buslinie b WHERE b.nummer= '" + nummer + "'");
		return q.getResultList();
	}
	
	@Override
	public void save(Buslinie buslinie) {
		em.persist(buslinie);
	}

	@Override
	public void update(Buslinie buslinie, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)
		em.merge(buslinie);
	}

	@Override
	public void delete(Buslinie buslinie) {
		em.remove(buslinie);
	}
}
