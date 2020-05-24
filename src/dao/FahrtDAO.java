package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.FahrtDTO;
import entity.Fahrt;
/**
 * 
 * DAO für {@link Fahrt} und {@link FahrtDTO}
 *
 * @author Felix & Silas
 *
 */
@Stateless
@LocalBean
public class FahrtDAO implements DAO<Fahrt, FahrtDTO> {

	@PersistenceContext
	EntityManager em;
	
	/**
	 * Fahrt by ID
	 */
	@Override
	public FahrtDTO get(int id) {
		return new FahrtDTO(em.find(Fahrt.class, id));
	}

	/**
	 * Fahrten by Buslinie
	 */
	@SuppressWarnings("unchecked")
	public List<FahrtDTO> getByBuslinie(int bid) {
		Query q = em.createQuery("SELECT f FROM Fahrt f WHERE f.buslinie.bid ='" + bid +"' ORDER BY f.uhrzeit ASC");
		List<Fahrt> fahrtEntities = new ArrayList<Fahrt>();
		List<FahrtDTO> fahrtDTOs = new ArrayList<FahrtDTO>();
		
		fahrtEntities = q.getResultList();
		fahrtEntities.forEach((fahrtEntity) -> fahrtDTOs.add(new FahrtDTO(fahrtEntity)));
		
		return fahrtDTOs;
	}
	
	/**
	 * Alle Fahrten
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FahrtDTO> getAll() {
		Query q = em.createQuery("SELECT f FROM Fahrt f");
		List<Fahrt> fahrtEntities = new ArrayList<Fahrt>();
		List<FahrtDTO> fahrtDTOs = new ArrayList<FahrtDTO>();
		
		fahrtEntities = q.getResultList();
		fahrtEntities.forEach((fahrtEntity) -> fahrtDTOs.add(new FahrtDTO(fahrtEntity)));
		
		return fahrtDTOs;
	}

	@Override
	public void save(FahrtDTO fahrtDTO) {
		em.persist(fahrtDTO.toEntity());
	}

	@Override
	public void update(FahrtDTO fahrtDTO, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)		
		em.merge(fahrtDTO.toEntity());
	}

	@Override
	public void delete(FahrtDTO fahrtDTO) {
		em.remove(fahrtDTO.toEntity());
	}
}
