package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.BuslinieDTO;
import entity.Buslinie;

@Stateless
@LocalBean
public class BuslinieDAO implements DAO<Buslinie, BuslinieDTO> {

	@PersistenceContext
	EntityManager em;
	
	FahrplanDAO fahrplanDAO;
	
    @Override
	public Optional<Buslinie> get(int id) {
		return Optional.ofNullable(em.find(Buslinie.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Buslinie> getAll() {
		Query q = em.createQuery("SELECT b FROM Buslinie b");
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
