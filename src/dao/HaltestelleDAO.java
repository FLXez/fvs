package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
