package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.VerbindungDTO;
import entity.Haltestelle;
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
	
	public boolean findByHaltestellen(Haltestelle haltestelle_start, Haltestelle haltestelle_ende) {
		Query q = em.createQuery("SELECT v.haltestelle_start , v.haltestelle_ende FROM Verbindung v WHERE v.haltestelle_start = '" + haltestelle_start + "' AND v.haltestelle_ende = '" + haltestelle_ende + "'");
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
