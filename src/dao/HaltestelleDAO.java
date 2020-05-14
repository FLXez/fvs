package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.HaltestelleDTO;
import entity.Haltestelle;

@Stateless
@LocalBean
public class HaltestelleDAO implements DAO<Haltestelle, HaltestelleDTO> {

	@PersistenceContext
	EntityManager em;

	@Override
	public Optional<Haltestelle> get(int id) {
		return Optional.ofNullable(em.find(Haltestelle.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Haltestelle> getAll() {
		Query q = em.createQuery("SELECT h FROM Haltestelle h");
		
		return q.getResultList();
	}

	public boolean findByLatLong(double latitude, double longitude) {
		Query q = em.createQuery("SELECT h.latitude, h.longitude FROM Haltestelle h WHERE h.latitude = '" + latitude + "' AND h.longitude = '" + longitude + "'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean findByBezeichnung(String bezeichnung) {
		Query q = em.createQuery("SELECT h.bezeichnung FROM Haltestelle h WHERE h.bezeichnung = '" + bezeichnung + "'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@Override
	public void save(Haltestelle haltestelle) {
		em.persist(haltestelle);
	}

	@Override
	public void update(Haltestelle haltestelle, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)
		em.merge(haltestelle);
	}

	@Override
	public void delete(Haltestelle haltestelle) {
		em.remove(haltestelle);
	}
}
