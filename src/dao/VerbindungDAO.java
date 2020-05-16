package dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Verbindung;


@Stateless
@LocalBean
public class VerbindungDAO implements DAO<Verbindung> {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Verbindung get(int id) {
		return em.find(Verbindung.class, id);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Verbindung> getAll() {
		Query q = em.createQuery("SELECT v FROM Verbindung v");
		
		return q.getResultList();	
	}
	
	//TODO Verbindung übergeben
	public boolean findByHaltestellen(int hidS, int hidE) {
		Query q = em.createQuery("SELECT v.haltestelleS.hid , v.haltestelleE.hid FROM Verbindung v WHERE v.haltestelleS.hid = '" + hidS + "' AND v.haltestelleE.hid = '" + hidE + "'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public void save(Verbindung verbindung) {
		em.persist(verbindung);
	}

	@Override
	public void update(Verbindung verbindung, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)		
		em.merge(verbindung);
	}

	@Override
	public void delete(Verbindung verbindung) {
		em.remove(verbindung);
	}
}
