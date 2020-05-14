package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.HaltestelleDAO;
import dto.HaltestelleDTO;
import entity.Haltestelle;

@Named("haltestellenBean")
@ApplicationScoped
public class HaltestellenBean {

	@Inject
	HaltestelleDAO haltestelleDAO;
	
	HaltestelleDTO neuHaltestelleDTO;
	
	
	@PostConstruct
	public void init() {
		this.neuHaltestelleDTO = new HaltestelleDTO();
	}
	
	public void setNeuHaltestelleDTO(HaltestelleDTO neuHaltestelleDTO) {
		this.neuHaltestelleDTO = neuHaltestelleDTO;
	}
	
	public HaltestelleDTO getNeueHaltestelleDTO() {
		return this.neuHaltestelleDTO;
	}
	
	public List<HaltestelleDTO> getAllFahrplans() {
		List<Haltestelle> haltestelles = new ArrayList<Haltestelle>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		haltestelles = haltestelleDAO.getAll();
		haltestelles.forEach((haltestelle) -> haltestelleDTOs.add(new HaltestelleDTO(haltestelle)));
		
		return haltestelleDTOs;
	}
	
	public void add() {
		Haltestelle haltestelle = new Haltestelle();
		haltestelle.setBezeichnung(this.neuHaltestelleDTO.getBezeichnung());
		haltestelle.setLatitude(this.neuHaltestelleDTO.getLatitude());
		haltestelle.setLongitude(this.neuHaltestelleDTO.getLongitude());
		
		try {
			haltestelleDAO.save(haltestelle);
			System.out.println("Haltestelle mit ID:" + haltestelle.getHId());
		} catch (EJBException e) {
			System.out.println("Haltestelle konnte nicht hinzugefügt werden.");
			e.printStackTrace();
		}
	}
}
