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
import util.NotificationUtils;

@Named("haltestelleBean")
@ApplicationScoped
public class HaltestelleBean {

	@Inject
	HaltestelleDAO haltestelleDAO;

	HaltestelleDTO haltestelleDTO;

	@PostConstruct
	public void init() {
		this.haltestelleDTO = new HaltestelleDTO();
	}

	public void setHaltestelleDTO(HaltestelleDTO haltestelleDTO) {
		this.haltestelleDTO = haltestelleDTO;
	}

	public HaltestelleDTO getHaltestelleDTO() {
		return this.haltestelleDTO;
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
		Haltestelle haltestelle = haltestelleDAO.get(id);
		haltestelleDTO = new HaltestelleDTO(haltestelle);

		return haltestelleDTO;
	}

	public void add() {
		
		if(!inputOkay()) {
			return;
		}
		
		Haltestelle haltestelle = new Haltestelle();
		haltestelle.setBezeichnung(haltestelleDTO.getBezeichnung());

		try {
			haltestelleDAO.save(haltestelle);
			NotificationUtils.showMessage(false, 0, "addStop:description", 
					"Haltestelle hinzugefügt",
					"Die Haltestelle wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "addStop:description", 
					"Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}
		
		haltestelleDTO = new HaltestelleDTO();
	}
	
	/**
	 * Überprüft alle Eingaben auf ihre Richtigkeit
	 */
	private boolean inputOkay() {
	
		if (haltestelleDTO.getBezeichnung().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "addStop:description", 
					"Bezeichnung leer",
					"Bitte vergeben Sie eine Bezeichnung.");
			return false;
		}

		if (haltestelleDAO.findByBezeichnung(haltestelleDTO.getBezeichnung())) {
			NotificationUtils.showMessage(false, 1, "addStop:description", 
					"Haltestellenbezeichnung bereits vergeben",
					"Eine Haltestelle mit dieser Bezeichnung existiert bereits.");
			return false;
		}
		
		return true;
	}
}
