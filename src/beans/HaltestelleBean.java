package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.HaltestelleDAO;
import dto.HaltestelleDTO;
import util.NotificationUtils;
import util.SessionUtils;

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
		return haltestelleDAO.getAll();
	}

	public HaltestelleDTO getHaltestelleByID(int id) {
		return haltestelleDAO.get(id);
	}
	
	public String forwardLinienhaltestellen(String hid) {
		SessionUtils.getSession().setAttribute("hid", hid);
		return "linienhaltestellen";
	}

	public void add() {

		if (!inputOkay()) {
			return;
		}

		try {
			haltestelleDAO.save(haltestelleDTO);
			NotificationUtils.showMessage(false, 0, "addStop:description", "Haltestelle hinzugefügt",
					"Die Haltestelle wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "addStop:description", "Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}

		haltestelleDTO = new HaltestelleDTO();
	}

	/**
	 * Überprüft alle Eingaben auf ihre Richtigkeit
	 */
	private boolean inputOkay() {

		if (haltestelleDTO.getBezeichnung().equals("")) {
			NotificationUtils.showMessage(false, 1, "addStop:description", "Bezeichnung leer",
					"Bitte vergeben Sie eine Bezeichnung.");
			return false;
		}

		if (haltestelleDAO.existsByBezeichnung(haltestelleDTO.getBezeichnung())) {
			NotificationUtils.showMessage(false, 1, "addStop:description", "Haltestellenbezeichnung bereits vergeben",
					"Eine Haltestelle mit dieser Bezeichnung existiert bereits.");
			return false;
		}

		return true;
	}
}
