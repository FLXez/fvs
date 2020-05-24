package beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dto.BuslinieDTO;
import util.NotificationUtils;
import util.SessionUtils;

/**
 * 
 * CDI-Bean für buslinie.xhtml
 *
 * @author Felix & Silas
 *
 */
@Named("buslinieBean")
@ApplicationScoped
public class BuslinieBean {

	@Inject
	BuslinieDAO buslinieDAO;

	BuslinieDTO buslinieDTO;

	@PostConstruct
	public void init() {
		this.buslinieDTO = new BuslinieDTO();
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public List<BuslinieDTO> getAllBuslinien() {
		return buslinieDAO.getAll();
	}

	/**
	 * Weiterleitung auf linienabfolge.xhtml
	 */
	public String forwardLinienabfolge(String bid) {
		SessionUtils.getSession().setAttribute("bid", bid);
		return "linienabfolge";
	}

	/**
	 * Weiterleitung auf fahrt.xhtml
	 */
	public String forwardFahrt(String bid) {
		SessionUtils.getSession().setAttribute("bid", bid);
		return "fahrt";
	}

	/**
	 * Hinzufügen einer Buslinie
	 */
	public void add() {

		if (!inputOkay()) {
			return;
		}

		BuslinieDTO buslinieDTOH = new BuslinieDTO();
		BuslinieDTO buslinieDTOR = new BuslinieDTO();
		buslinieDTOH.setNummer(buslinieDTO.getNummer());
		buslinieDTOH.setRichtung("H");
		buslinieDTOR.setNummer(buslinieDTO.getNummer());
		buslinieDTOR.setRichtung("R");

		try {
			buslinieDAO.save(buslinieDTOH);
			buslinieDAO.save(buslinieDTOR);
			NotificationUtils.showMessage(false, 1, "addLine:number", "Buslinie hinzugefügt",
					"Die Buslinie wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "addLine:number", "Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}

		buslinieDTO = new BuslinieDTO();
	}

	/**
	 * Überprüft alle Eingaben auf ihre Richtigkeit
	 */
	private boolean inputOkay() {

		if (buslinieDTO.getNummer() == 0) {
			NotificationUtils.showMessage(false, 1, "addLine:number", "Nummer leer", "Bitte vergeben Sie eine Nummer.");
			return false;
		}

		if (!buslinieDAO.existsByNummer(buslinieDTO.getNummer()).isEmpty()) {
			NotificationUtils.showMessage(false, 1, "addLine:number", "Buslinie bereits vorhanden",
					"Diese Buslinie ist bereits vorhanden.");
			return false;
		}

		return true;
	}
}