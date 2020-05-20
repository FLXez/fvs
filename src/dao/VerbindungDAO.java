package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	public VerbindungDTO get(int id) {
		return new VerbindungDTO(em.find(Verbindung.class, id));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<VerbindungDTO> getAll() {
		Query q = em.createQuery("SELECT v FROM Verbindung v");
		List<Verbindung> verbindungEntities = new ArrayList<Verbindung>();
		List<VerbindungDTO> verbindungDTOs = new ArrayList<VerbindungDTO>();
		
		verbindungEntities = q.getResultList();
		verbindungEntities.forEach((verbindungEntity) -> verbindungDTOs.add(new VerbindungDTO(verbindungEntity)));
		
		return verbindungDTOs;	
	}
	
	public VerbindungDTO getByHaltestelleS(int hidS) {
		Query q = em.createQuery("SELECT v FROM Verbindung v WHERE v.haltestelleS.hid = '" + hidS + "' AND v.haltestelleE.hid = NULL", Verbindung.class);
		return new VerbindungDTO((Verbindung) q.getSingleResult());
	}

	public VerbindungDTO getByHaltestellen(int hidS, int hidE) {
		Query q = em.createQuery("SELECT v FROM Verbindung v WHERE v.haltestelleS.hid = '" + hidS + "' AND v.haltestelleE.hid = '" + hidE + "'", Verbindung.class);
		return new VerbindungDTO((Verbindung) q.getSingleResult());
	}
	
	public boolean existsByHaltestellen(int hidS, int hidE) {
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
	public void save(VerbindungDTO verbindungDTO) {
		em.persist(verbindungDTO.toEntity());
	}

	@Override
	public void update(VerbindungDTO verbindungDTO, String[] parms) {
		//TODO Parms parsen (siehe UserDAO)		
		em.merge(verbindungDTO.toEntity());
	}
	
	public void update(VerbindungDTO verbindungDTO) {	
		em.merge(verbindungDTO.toEntity());
	}

	@Override
	public void delete(VerbindungDTO verbindungDTO) {
		em.remove(verbindungDTO.toEntity());
	}
}
