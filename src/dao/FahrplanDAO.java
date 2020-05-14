package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.FahrplanDTO;
import entity.Fahrplan;

@Stateless
@LocalBean
public class FahrplanDAO implements DAO<Fahrplan, FahrplanDTO> {

	@PersistenceContext
	EntityManager em;
	 // in Service oder Bean
	AblaufDAO ablaufDAO;
	BuslinieDAO buslinieDAO;
	
	@Override
	public Optional<Fahrplan> get(int id) {
		return Optional.ofNullable(em.find(Fahrplan.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fahrplan> getAll() {
		Query q = em.createQuery("SELECT f FROM Fahrplan f");
		return q.getResultList();
	}

	@Override
	public boolean save(Fahrplan fahrplan) {
		em.persist(fahrplan);
		return true;
		
	}

	@Override
	public boolean update(Fahrplan fahrplan, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)		
		em.merge(fahrplan);
		return true;
		
	}

	@Override
	public boolean delete(Fahrplan fahrplan) {
		em.remove(fahrplan);
		return true;
		
	}
}
