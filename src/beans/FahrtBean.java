package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dao.VerbindungDAO;
import dto.BuslinieDTO;
import dto.FahrtDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import util.NotificationUtils;
import util.SessionUtils;

/**
 * 
 * CDI-Bean für fahrt.xhtml
 *
 * @author Felix & Silas
 *
 */
@Named("fahrtBean")
@ApplicationScoped
public class FahrtBean {

	@Inject
	FahrtDAO fahrtDAO;

	@Inject
	BuslinieDAO buslinieDAO;

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;

	@Inject
	VerbindungDAO verbindungDAO;

	@Inject
	HaltestelleDAO haltestelleDAO;

	BuslinieDTO buslinieHDTO;
	BuslinieDTO buslinieRDTO;

	HaltestelleDTO haltestelleEDTO;
	HaltestelleDTO haltestelleSDTO;

	// Frontend listen
	List<HaltestelleDTO> feHaltestelleSDTOs;
	List<HaltestelleDTO> feHaltestelleEDTOs;

	int bid;

	int hids;
	int hide;

	String uhrzeit;
	Pattern uP;
	Matcher m;

	/**
	 * Initiales festlegen von Werten 
	 */
	@PostConstruct
	public void init() {
		uP = Pattern.compile("([0-1][0-9]|[2][0-3]):([0-5][0-9])");
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		BuslinieDTO buslinieDTO = buslinieDAO.get(bid);

		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		buslinieDTOs = buslinieDAO.getByNummer(buslinieDTO.getNummer());
		// ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = buslinieDTOs.get(0);
		buslinieRDTO = buslinieDTOs.get(1);

		feHaltestelleEDTOs = new ArrayList<HaltestelleDTO>();
		feHaltestelleSDTOs = new ArrayList<HaltestelleDTO>();
	}

	/**
	 * Reset / neuladen von Werten bei Seitenaufruf 
	 */
	public void onPageLoad() {
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		BuslinieDTO buslinieDTO = buslinieDAO.get(bid);

		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		buslinieDTOs = buslinieDAO.getByNummer(buslinieDTO.getNummer());
		// ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = buslinieDTOs.get(0);
		buslinieRDTO = buslinieDTOs.get(1);

		getAllFahrtenBid();
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public int getHids() {
		return hids;
	}

	public void setHids(int hids) {
		this.hids = hids;
	}

	public int getHide() {
		return hide;
	}

	public void setHide(int hide) {
		this.hide = hide;
	}

	public List<FahrtDTO> getAll() {
		return fahrtDAO.getAll();
	}

	public List<FahrtDTO> getAllFahrtenBid() {
		return fahrtDAO.getByBuslinie(bid);
	}

	/**
	 * Folgende zwei Methoden ermitteln alle möglichen Start- und Endhaltestellen
	 * Umgekehrte Logik bei Rücklinie....
	 * 
	 * Während Hinlinie wie folgt fährt: sDTO1 -> eDTO1 = sDTO2 -> eDTO2
	 * Fährt Rücklinie: eDTO2 -> sDTO2 = eDTO1 -> sDTO1
	 * 
	 * Deshalb mögliche sDTOs für eine Rücklinienfahrt = eDTOs und vice versa
	 */
	public List<HaltestelleDTO> getAllHaltestellenS() {
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();

		// Hinlinie wird bearbeitet
		if (bid == buslinieHDTO.getBid()) {
			linienabfolgeDTOs = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
			linienabfolgeDTOs.forEach((lDTO) -> haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleSDTO()));
			// Rücklinie wird bearbeitet
		} else {
			linienabfolgeDTOs = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "DESC");
			linienabfolgeDTOs.forEach((lDTO) -> haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleEDTO()));
		}
		// Liste fürs Backend zwischenspeichern
		feHaltestelleSDTOs = haltestelleDTOs;

		return haltestelleDTOs;
	}

	public List<HaltestelleDTO> getAllHaltestellenE() {
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();

		// Hinlinie wird bearbeitet
		if (bid == buslinieHDTO.getBid()) {
			linienabfolgeDTOs = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
			linienabfolgeDTOs.forEach((lDTO) -> haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleEDTO()));
			// Rücklinie wird bearbeitet
		} else {
			linienabfolgeDTOs = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "DESC");
			linienabfolgeDTOs.forEach((lDTO) -> haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleSDTO()));
		}
		// Liste fürs Backend zwischenspeichern
		feHaltestelleEDTOs = haltestelleDTOs;

		return haltestelleDTOs;
	}

	// Fahrt wird hinzugefügt
	public void add() {

		if (!inputOkay()) {
			return;
		}

		FahrtDTO fahrtDTO = new FahrtDTO();
		fahrtDTO.setBuslinieDTO(buslinieDAO.get(bid));
		fahrtDTO.setUhrzeit(uhrzeit);
		fahrtDTO.setHaltestelleSDTO(haltestelleDAO.get(hids));
		fahrtDTO.setHaltestelleEDTO(haltestelleDAO.get(hide));

		try {
			fahrtDAO.save(fahrtDTO);
			NotificationUtils.showMessage(false, 1, "fahrt:uhrzeit", "Fahrt hinzugefügt",
					"Die Fahrt wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}
		uhrzeit = new String();
	}

	/**
	 * Überprüft alle Eingaben auf ihre Richtigkeit
	 */
	private boolean inputOkay() {

		if (linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC").isEmpty()) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Keine Linienabfolge",
					"Bitte erstellen Sie zuerst eine Linienabfolge.");
			return false;
		}

		m = uP.matcher(uhrzeit);
		if (!m.matches()) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Uhrzeit ungültig",
					"Bitte geben Sie eine gültige Uhrzeit an.");
			return false;
		}

		List<FahrtDTO> fahrtDTOs = new ArrayList<FahrtDTO>();
		fahrtDTOs = fahrtDAO.getByBuslinie(bid);

		// haltestelle aus Liste ziehen, damit via indexOf findbar
		for (HaltestelleDTO haltestelle : feHaltestelleSDTOs) {
			if (haltestelle.getHid() == hids) {
				haltestelleSDTO = haltestelle;
			}
		}

		// haltestelle aus Liste ziehen, damit via indexOf findbar
		for (HaltestelleDTO haltestelle : feHaltestelleEDTOs) {
			if (haltestelle.getHid() == hide) {
				haltestelleEDTO = haltestelle;
			}
		}

		if (feHaltestelleSDTOs.indexOf(haltestelleSDTO) > feHaltestelleEDTOs.indexOf(haltestelleEDTO)) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Starthaltestelle nach Zielhaltestelle",
					"Die Starthaltestelle darf sich nicht nach der Zielhaltestelle befinden.");
			return false;
		}

		for (FahrtDTO f : fahrtDTOs) {
			if (f.getHaltestelleEDTO().getHid() == haltestelleEDTO.getHid()
					&& f.getHaltestelleSDTO().getHid() == haltestelleSDTO.getHid() && f.getUhrzeit().equals(uhrzeit)) {
				NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Fahrt bereits vorhanden",
						"Diese Fahrt ist bereits vorhanden.");
				return false;
			}
		}

		return true;
	}

}