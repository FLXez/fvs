package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.VerbindungDAO;
import dto.VerbindungDTO;
import entity.Haltestelle;
import entity.Verbindung;
import util.NotificationUtils;

@Named("verbindungBean")
@ApplicationScoped
public class VerbindungBean {

	@Inject
	VerbindungDAO verbindungDAO;
	@Inject
	HaltestelleBean haltestelleBean;
	
	VerbindungDTO newVerbindungDTO;
	
	@PostConstruct
	public void init() {
		newVerbindungDTO = new VerbindungDTO();
	}

	public VerbindungDTO getNewVerbindungDTO() {
		return newVerbindungDTO;
	}

	public void setNewVerbindungDTO(VerbindungDTO newVerbindungDTO) {
		this.newVerbindungDTO = newVerbindungDTO;
	}

	public List<VerbindungDTO> getAllVerbindungs() {
		// Listen aufbauen, um diese durchgehen zu können
		List<Verbindung> verbindungs = new ArrayList<Verbindung>();
		List<VerbindungDTO> verbindungDTOs = new ArrayList<VerbindungDTO>();
		verbindungs = verbindungDAO.getAll();
		// Verbindung Entity wird zu Verbindung DTO
		verbindungs.forEach((verbindung) -> verbindungDTOs.add(new VerbindungDTO(verbindung)));
		
		return verbindungDTOs;
	}
	
	public VerbindungDTO getVerbindungByID(int id) {
		VerbindungDTO verbindungDTO;
		Optional<Verbindung> verbindung = verbindungDAO.get(id);
		if(verbindung.isPresent()) {
			verbindungDTO = new VerbindungDTO(verbindung.get());
			
			return verbindungDTO;
		} else {
			System.out.println("Keine Verbindung mit der ID " + id + " gefunden.");
			return null;
		}
	}
	
	public void add() {
		
		if(newVerbindungDTO.getHaltestelle_startDTO() == null || newVerbindungDTO.getHaltestelle_endeDTO() == null ) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Haltestelle leer", "Bitte geben Sie beide Haltestellen an.");
			return;
		}
		
		if(newVerbindungDTO.getDauer() == 0) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Dauer 0", "Bitte geben Sie eine Dauer an.");			
			return;
		}
		
		Haltestelle haltestelleStart = new Haltestelle();
		int hids = newVerbindungDTO.getHaltestelle_startDTO().getHId();
		newVerbindungDTO.setHaltestelle_startDTO(haltestelleBean.getHaltestelleByID(hids));
		
		haltestelleStart.setHId(newVerbindungDTO.getHaltestelle_startDTO().getHId());
		haltestelleStart.setBezeichnung(newVerbindungDTO.getHaltestelle_startDTO().getBezeichnung());
		haltestelleStart.setLatitude(newVerbindungDTO.getHaltestelle_startDTO().getLatitude());
		haltestelleStart.setLongitude(newVerbindungDTO.getHaltestelle_startDTO().getLongitude());
		
		Haltestelle haltestelleEnde = new Haltestelle();
		int hide = newVerbindungDTO.getHaltestelle_endeDTO().getHId();
		newVerbindungDTO.setHaltestelle_endeDTO(haltestelleBean.getHaltestelleByID(hide));
		
		haltestelleEnde.setHId(newVerbindungDTO.getHaltestelle_endeDTO().getHId());
		haltestelleEnde.setBezeichnung(newVerbindungDTO.getHaltestelle_endeDTO().getBezeichnung());
		haltestelleEnde.setLatitude(newVerbindungDTO.getHaltestelle_endeDTO().getLatitude());
		haltestelleEnde.setLongitude(newVerbindungDTO.getHaltestelle_endeDTO().getLongitude());
		
		if(!verbindungDAO.findByHaltestellen(haltestelleStart.getHId(), haltestelleEnde.getHId())) {
						
			Verbindung verbindung = new Verbindung();		
			verbindung.setHaltestelle_start(haltestelleStart);
			verbindung.setHaltestelle_ende(haltestelleEnde);
			verbindung.setDauer(newVerbindungDTO.getDauer());
			
			try {
				verbindungDAO.save(verbindung);
				newVerbindungDTO = new VerbindungDTO();
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Verbindung hinzugefügt", "Die Verbindung wurde erfolgreich hinzugefügt.");
			} catch(EJBException e) { NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		} else { NotificationUtils.showMessage(false, 1, "linien:dauer", "Verbindung existiert bereits", "Diese Verbindung existiert bereits."); }
	}	
}