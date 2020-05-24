package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dto.FahrplanDTO;
import dto.FahrtDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import util.NotificationUtils;
import util.SessionUtils;

/**
 * 
 * CDI-Bean für fahrplan.xhtml
 *
 * @author Felix & Silas
 *
 */
@Named("fahrplanBean")
@ApplicationScoped
public class FahrplanBean {

	@Inject
	BuslinieDAO buslinieDAO;

	@Inject
	HaltestelleDAO haltestelleDAO;

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;

	@Inject
	FahrtDAO fahrtDAO;

	List<FahrtDTO> possibleFahrtDTOs;

	HaltestelleDTO haltestelleDTO;
	int hid;

	String uhrzeit;
	int Zeithorizont;
	String uhrzeitHorizont;

	String uhrzeitCalc;
	
	Pattern uP;
	Matcher m;

	/**
	 * Initiales festlegen von Werten 
	 */
	@PostConstruct
	public void init() {
		uhrzeitCalc = "";
		uP = Pattern.compile("([0-1][0-9]|[2][0-3]):([0-5][0-9])");
	}
	/**
	 * Reset von uhrzeitCalc bei Seitenaufruf 
	 */
	public void onPageLoad() {
		uhrzeitCalc = "";
	}

	public HaltestelleDTO getHaltestelleDTO() {
		return haltestelleDTO;
	}

	public void setHaltestellDTO(HaltestelleDTO haltestelleDTO) {
		this.haltestelleDTO = haltestelleDTO;
	}

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public int getZeithorizont() {
		return Zeithorizont;
	}

	public void setZeithorizont(int zeithorizont) {
		Zeithorizont = zeithorizont;
	}

	public List<HaltestelleDTO> getAllHaltestellen() {
		return haltestelleDAO.getAll();
	}
	
	/**
	 * Weiterleitung auf linienabfolge.xhtml 
	 */	
	public String forwardLinienabfolge(String bid) {
		SessionUtils.getSession().setAttribute("bid", bid);
		return "linienabfolge";
	}

	/**
	 * Display der Tabelle bei Knopfdruck
	 * Dafür unter anderem Zeitrechnung 
	 */
	public void display() {

		if(!inputOkay()) {
			return;
		}
		haltestelleDTO = haltestelleDAO.get(hid);

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = new Date();

		try {
			d = df.parse(uhrzeit);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, Zeithorizont);
		uhrzeitHorizont = df.format(cal.getTime());
	}

	/**
	 * Aufbauen der Fahrplanliste, welche in der Tabelle dargestellt wird 
	 */
	public List<FahrplanDTO> fahrplanInfo() {
		
		List<FahrplanDTO> fahrplanDTOs = new ArrayList<FahrplanDTO>();
		List<FahrtDTO> allFahrtDTOs = new ArrayList<FahrtDTO>();
		// Sämtliche Fahrten werden analysiert
		allFahrtDTOs = fahrtDAO.getAll();
	
		for (FahrtDTO fDTO : allFahrtDTOs) {
			// Reset der benötigten Variablen
			List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
			// fahrtPossible = Gesuchte Haltestelle in Fahrt enthalten
			// fahrtGoing = Aktuelle Haltestelle der Linienabfolge in Fahrt enthalten
			boolean fahrtPossible = false;
			boolean fahrtGoing = false;
			
			// Hinlinie
			if(fDTO.getBuslinieDTO().getRichtung().equals("H")) {
				// Linienabfolgen ASCENDING, da somit in richtiger Reihenfolge für die Abarbeitung
				linienabfolgeDTOs = linienabfolgeDAO.getByBuslinieH(fDTO.getBuslinieDTO().getBid(), "ASC");
				//Elemente durchgehen
				for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
					// Anfang der Fahrt in der Abfolge finden, ab da geht die Fahrt los
					if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
						fahrtGoing = true;
					}
					// Linienabfolge Element ist teil der Fahrt!
					if (fahrtGoing) {
						// Uhrzeit berechnen - somit "hochrechnen" wann der Bus an den einzelnen Haltestellen ankommt
						calcUhrzeit(fDTO, lDTO);
						// Ende der Fahrt erreicht, Hochrechnung ab da stoppen, fahrt ist aber möglich, da gesuchte Haltestelle gefunden
						if(lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
							fahrtPossible = true;
							fahrtGoing = false;
						}
					}
				}	
			// Rücklinie, siehe vorherige Kommentare (bloß umgekehrte Logik)
			// Während Hinlinie wie folgt fährt: sDTO1 -> eDTO1 = sDTO2 -> eDTO2
			// Fährt Rücklinie: eDTO2 -> sDTO2 = eDTO1 -> sDTO1 
			} else {
				// Linienabfolge DESCENDING,  da somit in richtiger Reihenfolge für die Abarbeitung
				linienabfolgeDTOs = linienabfolgeDAO.getByBuslinieR(fDTO.getBuslinieDTO().getBid(), "DESC");	
				
				for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
					// Anfang der Fahrt in der Abfolge finden - StartDTO einer Fahrt ist bei Rücklinien EndDTO der Linienabfolge
					if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
						fahrtGoing = true;
					}
					if (fahrtGoing) {
						
						calcUhrzeit(fDTO, lDTO);
						// Dadurch wird StartDTO als "Ziel" angefahren
						if(lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
							fahrtPossible = true;
							fahrtGoing = false;
						}
					}
				}								
			}
			/**
			 * Gesuchte Haltestelle ist in der Fahrt enthalten (in dem Abschnitt der Linienabfolge)
			 * Somit Zeitrechnung duchführen um zu überprüfen, ob sich die Fahrt im Horizont befindet
			 * Wenn ja, Verpsätung berechnen und Hinzufügen zur Liste
			 */
			if (fahrtPossible) {
				Date d1 = new Date();
				Date d2 = new Date();
				Date d3 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				try {
					d1 = df.parse(uhrzeit);
					d2 = df.parse(uhrzeitCalc);
					d3 = df.parse(uhrzeitHorizont);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if((d1.before(d2) || d1.equals(d2)) && (d3.after(d2) || d3.equals(d2))) {
					int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);	
					fahrplanDTOs.add(new FahrplanDTO(fDTO.getBuslinieDTO(), fDTO.getHaltestelleEDTO(), uhrzeitCalc, Integer.toString(randomNum)));						
				}
			}
			//Reset uhrzeitCalc für nächste Fahrt
			uhrzeitCalc = "";
		}		
		//Liste sortieren und zurückgeben
		Comparator<FahrplanDTO> compareByUhrzeit = (FahrplanDTO o1, FahrplanDTO o2) ->
        o1.getUhrzeit().compareTo(o2.getUhrzeit());
		Collections.sort(fahrplanDTOs, compareByUhrzeit);
		return fahrplanDTOs;
	}

	/**
	 * Berechnen von Zeiten 
	 */
	public void calcUhrzeit(FahrtDTO fDTO, LinienabfolgeDTO lDTO) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		// Erster Aufruf der Methode mit der Fahrt, somit leerer uhrzeitCalc
		if (uhrzeitCalc.equals("")) {
			try {
				d = df.parse(fDTO.getUhrzeit());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		// Jeder weitere Aufruf
		} else {
			try {
				d = df.parse(uhrzeitCalc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, lDTO.getVerbindungDTO().getDauer());
		uhrzeitCalc = df.format(cal.getTime());
	}
	
	/**
	 * Überprüfung, ob Eingaben okay sind 
	 */
	private boolean inputOkay() {
		
		m = uP.matcher(uhrzeit);
		if (!m.matches() && !uhrzeit.equals("")) {
			NotificationUtils.showMessage(false, 2, "fahrplan:zeithorizont", "Ungültiger Zeithorizont",
					"Bitte geben Sie eine gültige Uhrzeit an.");
			return false;	
		}

		if(hid == 0) {
			NotificationUtils.showMessage(false, 2, "fahrplan:zeithorizont", "Keine Haltestelle",
					"Bitte wählen Sie eine Haltestelle aus.");
			return false;
		}
			
		if(Zeithorizont < 0) {
			NotificationUtils.showMessage(false, 2, "fahrplan:zeithorizont", "Ungültiger Zeithorizont",
					"Bitte geben Sie eine Zahl größer gleich 0 an.");
			return false;			
		}		
		return true;
	}
}
