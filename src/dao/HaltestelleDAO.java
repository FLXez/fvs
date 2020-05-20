package dao;

import java.util.ArrayList;
import java.util.List;

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
	public HaltestelleDTO get(int id) {		
		return new HaltestelleDTO(em.find(Haltestelle.class, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HaltestelleDTO> getAll() {		
		Query q = em.createQuery("SELECT h FROM Haltestelle h");
		List<Haltestelle> haltestelleEntities = new ArrayList<Haltestelle>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		
		haltestelleEntities = q.getResultList();
		haltestelleEntities.forEach((haltestelleEntity) -> haltestelleDTOs.add(new HaltestelleDTO(haltestelleEntity)));
		
		return haltestelleDTOs;
	}
	
	public boolean existsByBezeichnung(String bezeichnung) {
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
	public void save(HaltestelleDTO haltestelleDTO) {
		em.persist(haltestelleDTO.toEntity());
	}

	@Override
	public void update(HaltestelleDTO haltestelleDTO, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)
		em.merge(haltestelleDTO.toEntity());
	}

	@Override
	public void delete(HaltestelleDTO haltestelleDTO) {
		em.remove(haltestelleDTO.toEntity());
	}
}
