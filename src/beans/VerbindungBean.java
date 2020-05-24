package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.VerbindungDAO;
import dto.VerbindungDTO;
import entity.Haltestelle;
import util.NotificationUtils;

/**
 * 
 * CDI-Bean für alte verbindung.xhtml
 * @deprecated verbindungen werden über linienabfolge.xhtml erstellt
 *
 * @author Felix & Silas
 *
 */
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
		return verbindungDAO.getAll();
	}

	public VerbindungDTO getVerbindungByID(int id) {
		return verbindungDAO.get(id);
	}

	public void add() {

		if (!inputOkay()) {
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

		if (!verbindungDAO.existsByHaltestellen(haltestelleStart.getHid(), haltestelleEnde.getHid())) {

			try {
				verbindungDAO.save(newVerbindungDTO);
				newVerbindungDTO = new VerbindungDTO();
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Verbindung hinzugefügt",
						"Die Verbindung wurde erfolgreich hinzugefügt.");
			} catch (EJBException e) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler",
						"Es ist ein unerwarteter Fehler aufgetreten.");
			}
		} else {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Verbindung existiert bereits",
					"Diese Verbindung existiert bereits.");
		}
	}

	/**
	 * Überprüft alle Eingaben auf ihre Richtigkeit
	 */
	private boolean inputOkay() {

		if (newVerbindungDTO.getHaltestelleSDTO() == null || newVerbindungDTO.getHaltestelleEDTO() == null) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Haltestelle leer",
					"Bitte geben Sie beide Haltestellen an.");
			return false;
		}

		if (newVerbindungDTO.getHaltestelleSDTO() == newVerbindungDTO.getHaltestelleEDTO()) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Haltestellen identsich",
					"Bitte wählen Sie zwei verschiedene Haltestellen aus.");
			return false;
		}

		if (newVerbindungDTO.getDauer() == 0) {
			NotificationUtils.showMessage(false, 1, "linien:dauer", "Dauer 0", "Bitte geben Sie eine Dauer an.");
			return false;
		}

		return true;
	}
}