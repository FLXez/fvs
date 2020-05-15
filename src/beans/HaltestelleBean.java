package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.HaltestelleDAO;
import dto.HaltestelleDTO;
import entity.Haltestelle;
import util.NotificationUtils;

@Named("haltestelleBean")
@ApplicationScoped
public class HaltestelleBean {

	@Inject
	HaltestelleDAO haltestelleDAO;
	
	HaltestelleDTO newHaltestelleDTO;
	
	@PostConstruct
	public void init() {
		this.newHaltestelleDTO = new HaltestelleDTO();
	}
	
	public void setNewHaltestelleDTO(HaltestelleDTO newHaltestelleDTO) {
		this.newHaltestelleDTO = newHaltestelleDTO;
	}
	
	public HaltestelleDTO getNewHaltestelleDTO() {
		return this.newHaltestelleDTO;
	}
	
	public List<HaltestelleDTO> getAllHaltestellen() {
		List<Haltestelle> haltestelles = new ArrayList<Haltestelle>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		haltestelles = haltestelleDAO.getAll();
		haltestelles.forEach((haltestelle) -> haltestelleDTOs.add(new HaltestelleDTO(haltestelle)));
		
		return haltestelleDTOs;
	}
	
	public HaltestelleDTO getHaltestelleByID(int id) {
		HaltestelleDTO haltestelleDTO;
		Optional<Haltestelle> haltestelle = haltestelleDAO.get(id);
		if(haltestelle.isPresent()) {
			haltestelleDTO = new HaltestelleDTO(haltestelle.get());
			
			return haltestelleDTO;
		} else {
			System.out.println("Keine Haltestelle mit der ID " + id + " gefunden.");
			return null;
		}
		
	}
	
	public void add() { 
	
		if(newHaltestelleDTO.getBezeichnung().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "addStop:description", "Bezeichnung leer", "Bitte vergeben Sie eine Bezeichnung.");
			return;
		}
		if(newHaltestelleDTO.getLatitude() == 0 | newHaltestelleDTO.getLongitude() == 0) {
			NotificationUtils.showMessage(false, 1, "addStop:description", "Latitude/Longitude 0", "Latitude/Longitude 0.0 ist nicht zugelassen.");
			return;
		}		
			
		if(!haltestelleDAO.findByBezeichnung(newHaltestelleDTO.getBezeichnung())) {
			
			if(!haltestelleDAO.findByLatLong(newHaltestelleDTO.getLatitude(), newHaltestelleDTO.getLongitude())) {
				
				Haltestelle haltestelle = new Haltestelle();
				haltestelle.setBezeichnung(this.newHaltestelleDTO.getBezeichnung());
				haltestelle.setLatitude(this.newHaltestelleDTO.getLatitude());
				haltestelle.setLongitude(this.newHaltestelleDTO.getLongitude());
				
				try {
					haltestelleDAO.save(haltestelle);
					newHaltestelleDTO = new HaltestelleDTO();
					NotificationUtils.showMessage(false, 0, "addStop:description", "Haltestelle hinzugefügt", "Die Haltestelle wurde erfolgreich hinzugefügt.");				
				} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "addStop:description", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
			} else { NotificationUtils.showMessage(false, 1, "addStop:description", "Haltestellenort bereits vergeben", "An diesem Ort existiert bereits eine Haltestelle."); }			
		} else { NotificationUtils.showMessage(false, 1, "addStop:description", "Haltestellenbezeichnung bereits vergeben", "Eine Haltestelle mit dieser Bezeichnung existiert bereits."); }
	}
}
