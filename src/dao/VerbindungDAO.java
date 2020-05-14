package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.VerbindungDTO;
import entity.Verbindung;


@Stateless
@LocalBean
public class VerbindungDAO implements DAO<Verbindung, VerbindungDTO> {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Optional<Verbindung> get(int id) {
		return Optional.ofNullable(em.find(Verbindung.class, id));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Verbindung> getAll() {
		Query q = em.createQuery("SELECT v FROM Verbindung v");
		
		return q.getResultList();	
	}

	@Override
	public boolean save(Verbindung verbindung) {
		em.persist(verbindung);
		return true;
		
	}

	@Override
	public boolean update(Verbindung verbindung, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)		
		em.merge(verbindung);
		return true;
		
	}

	@Override
	public boolean delete(Verbindung verbindung) {
		em.remove(verbindung);
		return true;
		
	}
}
