package dao;

import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.AblaufDTO;
import entity.Ablauf;

@Stateless
@LocalBean
public class AblaufDAO implements DAO<Ablauf, AblaufDTO> {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public Optional<Ablauf> get(int id) {
		
		return Optional.ofNullable(em.find(Ablauf.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ablauf> getAll() {
		Query q = em.createQuery("SELECT a FROM Ablauf a");
		return q.getResultList();
	}

	@Override
	public void save(Ablauf ablauf) {
		em.persist(ablauf);
	}

	@Override
	public void update(Ablauf ablauf, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)
		em.merge(ablauf);
		
	}

	@Override
	public void delete(Ablauf ablauf) {
		em.remove(ablauf);
		
	}
}
