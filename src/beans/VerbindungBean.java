package beans;

import java.util.ArrayList;
import java.util.List;

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
		Verbindung verbindung = verbindungDAO.get(id);
		verbindungDTO = new VerbindungDTO(verbindung);
			
		return verbindungDTO;
	}
	
	public void add() {
		
		if(newVerbindungDTO.getHaltestelleSDTO() == null || newVerbindungDTO.getHaltestelleEDTO() == null ) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Haltestelle leer", "Bitte geben Sie beide Haltestellen an.");
			return;
		}

		if(newVerbindungDTO.getHaltestelleSDTO() == newVerbindungDTO.getHaltestelleEDTO()) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Haltestellen identsich", "Bitte wählen Sie zwei verschiedene Haltestellen aus.");
			return;
		}
		
		if(newVerbindungDTO.getDauer() == 0) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Dauer 0", "Bitte geben Sie eine Dauer an.");			
			return;
		}
		
		Haltestelle haltestelleStart = new Haltestelle();
		int hids = newVerbindungDTO.getHaltestelleSDTO().getHid();
		newVerbindungDTO.setHaltestelleSDTO(haltestelleBean.getHaltestelleByID(hids));
		
		haltestelleStart.setHid(newVerbindungDTO.getHaltestelleSDTO().getHid());
		haltestelleStart.setBezeichnung(newVerbindungDTO.getHaltestelleSDTO().getBezeichnung());
		
		Haltestelle haltestelleEnde = new Haltestelle();
		int hide = newVerbindungDTO.getHaltestelleEDTO().getHid();
		newVerbindungDTO.setHaltestelleEDTO(haltestelleBean.getHaltestelleByID(hide));
		
		haltestelleEnde.setHid(newVerbindungDTO.getHaltestelleEDTO().getHid());
		haltestelleEnde.setBezeichnung(newVerbindungDTO.getHaltestelleEDTO().getBezeichnung());
		
		if(!verbindungDAO.findByHaltestellen(haltestelleStart.getHid(), haltestelleEnde.getHid())) {
						
			Verbindung verbindung = new Verbindung();		
			verbindung.setHaltestelleS(haltestelleStart);
			verbindung.setHaltestelleE(haltestelleEnde);
			verbindung.setDauer(newVerbindungDTO.getDauer());
			
			try {
				verbindungDAO.save(verbindung);
				newVerbindungDTO = new VerbindungDTO();
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Verbindung hinzugefügt", "Die Verbindung wurde erfolgreich hinzugefügt.");
			} catch(EJBException e) { NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		} else { NotificationUtils.showMessage(false, 1, "linien:dauer", "Verbindung existiert bereits", "Diese Verbindung existiert bereits."); }
	}	
}