package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dao.VerbindungDAO;
import dto.BuslinieDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import dto.VerbindungDTO;
import util.NotificationUtils;
import util.SessionUtils;

@Named("linienabfolgeBean")
@ApplicationScoped
public class LinienabfolgeBean {

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;

	@Inject
	VerbindungDAO verbindungDAO;

	@Inject
	HaltestelleDAO haltestelleDAO;

	@Inject
	BuslinieDAO buslinieDAO;

	LinienabfolgeDTO linienabfolge;

	BuslinieDTO buslinieEditDTO;
	BuslinieDTO buslinieHDTO;
	BuslinieDTO buslinieRDTO;

	VerbindungDTO verbindungDTO;

	int hid;
	int bid;
	int bidSel;

	int dauer;

	@PostConstruct
	public void init() {
		linienabfolge = new LinienabfolgeDTO();
		bid = Integer.parseInt(SessionUtils.getSession().getAttribute("bid").toString());

		buslinieEditDTO = buslinieDAO.get(bid);
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		buslinieDTOs = buslinieDAO.getByNummer(buslinieEditDTO.getNummer());
		// ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = buslinieDTOs.get(0);
		buslinieRDTO = buslinieDTOs.get(1);

		verbindungDTO = new VerbindungDTO();
	}

	public void onPageLoad() {
		linienabfolge = new LinienabfolgeDTO();
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));

		buslinieEditDTO = buslinieDAO.get(bid);
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		buslinieDTOs = buslinieDAO.getByNummer(buslinieEditDTO.getNummer());
		// ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = buslinieDTOs.get(0);
		buslinieRDTO = buslinieDTOs.get(1);

		getAllLinienabfolgenBid();
	}

	public BuslinieDTO getBuslinieEditDTO() {
		return buslinieEditDTO;
	}

	public void setBuslinieEditDTO(BuslinieDTO buslinieEditDTO) {
		this.buslinieEditDTO = buslinieEditDTO;
	}

	public BuslinieDTO getBuslinieHDTO() {
		return buslinieHDTO;
	}

	public void setBuslinieHDTO(BuslinieDTO buslinieHDTO) {
		this.buslinieHDTO = buslinieHDTO;
	}

	public BuslinieDTO getBuslinieRDTO() {
		return buslinieRDTO;
	}

	public void setBuslinieRDTO(BuslinieDTO buslinieRDTO) {
		this.buslinieRDTO = buslinieRDTO;
	}

	public int getDauer() {
		return dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public List<HaltestelleDTO> getAllHaltestellen() {
		return haltestelleDAO.getAll();
	}
	
	public List<LinienabfolgeDTO> getAllLinienabfolgen() {
		return linienabfolgeDAO.getAll();
	}

	public List<LinienabfolgeDTO> getAllLinienabfolgenBid() {
		if (bid == buslinieHDTO.getBid()) {
			return linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
		} else {
			return linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "DESC");
		}
	}

	public void add() {
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		linienabfolgeDTOs = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");

		if (haltestelleDAO.getAll().isEmpty()) {
			NotificationUtils.showMessage(false, 2, "linien:dauer", "Keine Haltestelle",
					"Bitte legen Sie zuerst Haltestellen an.");
			return;
		}

		if (dauer == 0) {
			NotificationUtils.showMessage(false, 2, "linien:dauer", "Dauer 0", "Bitte geben Sie eine Dauer an.");
			return;
		}

		if (bid == buslinieHDTO.getBid()) {
			hAdd(linienabfolgeDTOs);
		} else {
			rAdd(linienabfolgeDTOs);
		}

	}

	public void hAdd(List<LinienabfolgeDTO> linienabfolgeDTOs) {
		LinienabfolgeDTO linienabfolgeDTO = new LinienabfolgeDTO();
		// Erstes Linienabfolgeelement
		if (linienabfolgeDTOs.isEmpty()) {

			// Buslinienwechsel während erstellung "kompensieren"
			if (bidSel != bid) {
				bidSel = bid;
				verbindungDTO.setHaltestelleSDTO(haltestelleDAO.get(hid));
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Starthaltestelle hinzugefügt",
						"Bitte wählen Sie nun die erste Zielhaltestelle und die Dauer aus.");
				return;
			}

			verbindungDTO.setHaltestelleEDTO(haltestelleDAO.get(hid));

			if (verbindungDTO.getHaltestelleSDTO().getHid() == verbindungDTO.getHaltestelleEDTO().getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen",
						"Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}

			verbindungDTO.setDauer(dauer);
			checkVerbindung(verbindungDTO);

			linienabfolgeDTO.setPosition(0);
			linienabfolgeDTO.setBuslinieHDTO(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeDTO.setBuslinieRDTO(buslinieDAO.get(buslinieRDTO.getBid()));

			// Speichern der Linienabfolge
			try {
				linienabfolgeDTO.setVerbindungDTO(verbindungDAO.getByHaltestellen(verbindungDTO.getHaltestelleSDTO().getHid(),verbindungDTO.getHaltestelleEDTO().getHid()));
				linienabfolgeDAO.save(linienabfolgeDTO);
				dauer = 0;
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolge 0 erfolgreich hinzugefügt",
						"Linienabfolgeelement erfolgreich hinzugefügt.");
			} catch (EJBException e) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler",
						"Es ist ein unerwarteter Fehler aufgetreten.");
			}
			return;
			// n-tes Linienabfolgeelement
		} else {
			// HaltestelleStart ist die Endhaltestelle des vorherigen Elements
			HaltestelleDTO haltestelleSDTO = linienabfolgeDTOs.get(linienabfolgeDTOs.size() - 1).getVerbindungDTO()
					.getHaltestelleEDTO();
			HaltestelleDTO haltestelleEDTO = haltestelleDAO.get(hid);
			if (haltestelleSDTO.getHid() == haltestelleEDTO.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen",
						"Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}

			if (linienabfolgeDTOs.get(0).getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleEDTO.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren",
						"Die Haltestelle ist in der Abfolge bereits enthalten.");
				return;
			}

			for (LinienabfolgeDTO l : linienabfolgeDTOs) {
				if (l.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleEDTO.getHid()) {
					NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren",
							"Die Haltestelle ist in der Abfolge bereits enthalten.");
					return;
				}
			}
			verbindungDTO = new VerbindungDTO();
			verbindungDTO.setHaltestelleSDTO(haltestelleSDTO);
			verbindungDTO.setHaltestelleEDTO(haltestelleEDTO);
			verbindungDTO.setDauer(dauer);

			checkVerbindung(verbindungDTO);

			linienabfolgeDTO.setBuslinieHDTO(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeDTO.setBuslinieRDTO(buslinieDAO.get(buslinieRDTO.getBid()));
			linienabfolgeDTO.setPosition(linienabfolgeDTOs.get(linienabfolgeDTOs.size() - 1).getPosition() + 1);

			try {
				linienabfolgeDTO.setVerbindungDTO(verbindungDAO.getByHaltestellen(verbindungDTO.getHaltestelleSDTO().getHid(),verbindungDTO.getHaltestelleEDTO().getHid()));
				linienabfolgeDAO.save(linienabfolgeDTO);
				dauer = 0;
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolgeerfolgreich hinzugefügt",
						"Haltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler",
						"Es ist ein unerwarteter Fehler aufgetreten.");
			}
		}
	}

	public void rAdd(List<LinienabfolgeDTO> linienabfolgeDTOs) {
		LinienabfolgeDTO linienabfolgeDTO = new LinienabfolgeDTO();

		// Erstes Linienabfolgeelement
		if (linienabfolgeDTOs.isEmpty()) {

			// Buslinienwechsel während erstellung "kompensieren"
			if (bidSel != bid) {
				bidSel = bid;
				verbindungDTO.setHaltestelleEDTO(haltestelleDAO.get(hid));
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Starthaltestelle hinzugefügt",
						"Bitte wählen Sie nun die erste Zielhaltestelle und die Dauer aus.");
				return;
			}

			verbindungDTO.setHaltestelleSDTO(haltestelleDAO.get(hid));

			if (verbindungDTO.getHaltestelleSDTO().getHid() == verbindungDTO.getHaltestelleEDTO().getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen",
						"Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}

			verbindungDTO.setDauer(dauer);
			checkVerbindung(verbindungDTO);

			linienabfolgeDTO.setPosition(0);
			linienabfolgeDTO.setBuslinieHDTO(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeDTO.setBuslinieRDTO(buslinieDAO.get(buslinieRDTO.getBid()));

			// Speichern der Linienabfolge
			try {
				linienabfolgeDTO.setVerbindungDTO(verbindungDAO.getByHaltestellen(verbindungDTO.getHaltestelleSDTO().getHid(),verbindungDTO.getHaltestelleEDTO().getHid()));
				linienabfolgeDAO.save(linienabfolgeDTO);
				dauer = 0;
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolge 0 erfolgreich hinzugefügt",
						"Starthaltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler",
						"Es ist ein unerwarteter Fehler aufgetreten.");
			}
			return;
			// n-tes Linienabfolgeelement
		} else {
			// HaltestelleStart ist die Endhaltestelle des vorherigen Elements
			HaltestelleDTO haltestelleSDTO = haltestelleDAO.get(hid);
			HaltestelleDTO haltestelleEDTO = linienabfolgeDTOs.get(0).getVerbindungDTO().getHaltestelleSDTO();
			if (haltestelleSDTO.getHid() == haltestelleEDTO.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen",
						"Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}

			if (linienabfolgeDTOs.get(linienabfolgeDTOs.size() - 1).getVerbindungDTO().getHaltestelleEDTO()
					.getHid() == haltestelleSDTO.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren",
						"Die Haltestelle ist in der Abfolge bereits enthalten.");
				return;
			}

			for (LinienabfolgeDTO l : linienabfolgeDTOs) {
				if (l.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleSDTO.getHid()) {
					NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren",
							"Die Haltestelle ist in der Abfolge bereits enthalten.");
					return;
				}
			}
			
			verbindungDTO = new VerbindungDTO();
			verbindungDTO.setHaltestelleSDTO(haltestelleSDTO);
			verbindungDTO.setHaltestelleEDTO(haltestelleEDTO);
			verbindungDTO.setDauer(dauer);

			checkVerbindung(verbindungDTO);

			linienabfolgeDTO.setBuslinieHDTO(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeDTO.setBuslinieRDTO(buslinieDAO.get(buslinieRDTO.getBid()));
			// Erstes Element wählen, davon Position -1
			linienabfolgeDTO.setPosition(linienabfolgeDTOs.get(0).getPosition() - 1);

			try {
				linienabfolgeDTO.setVerbindungDTO(verbindungDAO.getByHaltestellen(verbindungDTO.getHaltestelleSDTO().getHid(),verbindungDTO.getHaltestelleEDTO().getHid()));
				linienabfolgeDAO.save(linienabfolgeDTO);
				dauer = 0;
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolge erfolgreich hinzugefügt",
						"Haltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler",
						"Es ist ein unerwarteter Fehler aufgetreten.");
			}
		}
	}

	private void checkVerbindung(VerbindungDTO v) {
		if (!verbindungDAO.existsByHaltestellen(v.getHaltestelleSDTO().getHid(),
				v.getHaltestelleEDTO().getHid())) {
			verbindungDAO.save(v);
		} else {
			NotificationUtils.showMessage(false, 1, "linien:linienInfo", "Verbindung existent",
					"Verbindung existierte bereits. Alte Dauer übernommen.");		
		}
	}
}