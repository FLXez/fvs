package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dto.BuslinieDTO;
import dto.FahrtDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import util.SessionUtils;

/**
 * 
 * CDI-Bean für linienplan.xhtml
 *
 * @author Felix & Silas
 *
 */
@Named("linienplanBean")
@ApplicationScoped
public class LinienplanBean {

	@Inject
	BuslinieDAO buslinieDAO;

	@Inject
	HaltestelleDAO haltestelleDAO;

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;

	@Inject
	FahrtDAO fahrtDAO;

	BuslinieDTO buslinieDTO;
	HaltestelleDTO haltestelleDTO;

	List<LinienabfolgeDTO> linienabfolgeSelected;
	List<String> uhrzeiten;
	String uhrzeitTemp;

	int fid;

	int bid;
	int hid;

	// Initialisieren / Initiales setzen von Werten
	@PostConstruct
	public void init() {
		buslinieDTO = new BuslinieDTO();
		haltestelleDTO = new HaltestelleDTO();
		bid = (int) SessionUtils.getSession().getAttribute("bid");
		hid = (int) SessionUtils.getSession().getAttribute("hid");
		buslinieDTO = buslinieDAO.get(bid);
		haltestelleDTO = haltestelleDAO.get(hid);
	}

	// Reset / Set von Werten - Sorgt beim Wiederaufruf der Seite sonst zu Problemen
	public void onPageLoad() {
		buslinieDTO = new BuslinieDTO();
		haltestelleDTO = new HaltestelleDTO();
		bid = Integer.parseInt(SessionUtils.getSession().getAttribute("bid").toString());
		hid = Integer.parseInt(SessionUtils.getSession().getAttribute("hid").toString());
		buslinieDTO = buslinieDAO.get(bid);
		haltestelleDTO = haltestelleDAO.get(hid);
		linienabfolgeSelected = new ArrayList<LinienabfolgeDTO>();
		uhrzeiten = new ArrayList<String>();
		uhrzeitTemp = new String();
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public HaltestelleDTO getHaltestelleDTO() {
		return haltestelleDTO;
	}

	public void setHaltestelleDTO(HaltestelleDTO haltestelleDTO) {
		this.haltestelleDTO = haltestelleDTO;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}
	
	// Workaround, keinen "Fix" in dem Sinne gefunden
	public void submit() {
		setFid(this.fid); 
	}

	// Mögliche Fahrten ermitteln (erster Schritt)
	public List<FahrtDTO> getPossibleFahrten() {
		List<FahrtDTO> allFahrtDTOs = new ArrayList<FahrtDTO>();
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		allFahrtDTOs = fahrtDAO.getByBuslinie(buslinieDTO.getBid());
		
		// Umgekehrte Logik bei Rückrichtung, siehe FahrplanBean e.g.
		if(buslinieDTO.getRichtung().equals("H")) {
			linienabfolgeDTOs = linienabfolgeDAO.getAll("ASC");
			return getPossibleFahrtenH(allFahrtDTOs, linienabfolgeDTOs);
		} else {
			linienabfolgeDTOs = linienabfolgeDAO.getAll("DESC");
			return getPossibleFahrtenR(allFahrtDTOs, linienabfolgeDTOs);
		}
	}
	
	// Fahrten ermitteln - RÜCKLINIE
	public List<FahrtDTO> getPossibleFahrtenR(List<FahrtDTO> allFahrtDTOs, List<LinienabfolgeDTO> linienabfolgeDTOs) {
		List<FahrtDTO> possibleFahrtDTOs = new ArrayList<FahrtDTO>();

		// Rücklinie
		for (FahrtDTO fDTO : allFahrtDTOs) {
			boolean fahrtPossible = false;
			boolean fahrtGoing = false;
			List<LinienabfolgeDTO> fahrtLinie = new ArrayList<LinienabfolgeDTO>();
			for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
				// Anfang der Fahrt in der Abfolge finden
				if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
					fahrtGoing = true;
				}
				// Linienabfolge Element ist teil der Fahrt!
				if (fahrtGoing) {
					fahrtLinie.add(lDTO);
					// Ende erreicht?
					if (fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
						fahrtGoing = false;
					}
				}
			}
			// Überprüfen, ob Haltestelle in dem Abschnitt der Linienabfolge der Fahrt enthalten ist
			for (LinienabfolgeDTO flDTO : fahrtLinie) {
				if (flDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()
						|| flDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
					fahrtPossible = true;
				}
			}
			// Wenn ja, dann möglicher Treffer
			if (fahrtPossible) {
				possibleFahrtDTOs.add(fDTO);
			}
		}		
		return possibleFahrtDTOs;
	}
	
	// Fahrten ermitteln - HINLINIE
	public List<FahrtDTO> getPossibleFahrtenH(List<FahrtDTO> allFahrtDTOs, List<LinienabfolgeDTO> linienabfolgeDTOs) {
		List<FahrtDTO> possibleFahrtDTOs = new ArrayList<FahrtDTO>();
		
		// Hinlinie
		for (FahrtDTO fDTO : allFahrtDTOs) {
			boolean fahrtPossible = false;
			boolean fahrtGoing = false;
			List<LinienabfolgeDTO> fahrtLinie = new ArrayList<LinienabfolgeDTO>();
			for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
				// Anfang der Fahrt in der Abfolge finden
				if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
					fahrtGoing = true;
				}
				// Linienabfolge Element ist teil der Fahrt!
				if (fahrtGoing) {
					fahrtLinie.add(lDTO);
					// Ende erreicht?
					if (fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
						fahrtGoing = false;
					}
				}
			}
			// Überprüfen, ob Haltestelle in dem Abschnitt der Linienabfolge der Fahrt enthalten ist
			for (LinienabfolgeDTO flDTO : fahrtLinie) {
				if (flDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()
						|| flDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
					fahrtPossible = true;
				}
			}
			// Wenn ja, dann möglicher Treffer
			if (fahrtPossible) {
				possibleFahrtDTOs.add(fDTO);
			}
		}		
		return possibleFahrtDTOs;
	}

	// Daten für Tabelle ermitteln, umgekehrte Logik bei Rücklinien
	public List<HaltestelleDTO> getForPath(){
		List<LinienabfolgeDTO> lDTOs = new ArrayList<LinienabfolgeDTO>();
		FahrtDTO fDTO = fahrtDAO.get(fid);
		
		//uhrzeiten Array um Fahrzeiten auszugeben
		//uhrzeiten werden passend zur Haltestelle gespeichert
		uhrzeiten = new ArrayList<String>();
		uhrzeitTemp = new String();
		
		if(buslinieDTO.getRichtung().equals("H")) {
			lDTOs = linienabfolgeDAO.getByBuslinieH(bid, "ASC");
			return getForPathH(fDTO, lDTOs);
		} else {
			lDTOs = linienabfolgeDAO.getByBuslinieR(bid, "DESC");
			return getForPathR(fDTO, lDTOs);
		}
		
	}
	
	public List<HaltestelleDTO> getForPathR(FahrtDTO fDTO, List<LinienabfolgeDTO> lDTOs){
		linienabfolgeSelected = new ArrayList<LinienabfolgeDTO>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();		
		
		boolean returnElement = false;
		boolean fahrtGoing = false;
		
		//Linienabfolge Elemente durchgehen und Analysieren
		for (LinienabfolgeDTO lDTO : lDTOs) {

			//Erst ab Start der Fahrt
			if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
				fahrtGoing = true;
			}
			//Workaround
			if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() 
					&& fDTO.getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
				uhrzeitTemp = fDTO.getUhrzeit();
			}
			if(fahrtGoing) {
				if(lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
					returnElement = true;
				}
			}
			
			// Sonderfall
			// Linienabfolge Element ist teil der Fahrt!
			if(returnElement) {
				haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleEDTO());
				//uhrzeiten Element hinzufügen
				uhrzeiten.add(uhrzeitTemp);
			}
			// Zum ende, da sonst Zeit zu früh hochgerechnet wird
			if(fahrtGoing) {
				addUhrzeit(fDTO, lDTO);
			}
			// Ende der Fahrt erreicht?
			if(fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
				haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleSDTO());
				//uhrzeiten Element hinzufügen
				uhrzeiten.add(uhrzeitTemp);
				returnElement = false;
				fahrtGoing = false;
			}
		}		 				
		return haltestelleDTOs;
	}
	
	public List<HaltestelleDTO> getForPathH(FahrtDTO fDTO, List<LinienabfolgeDTO> lDTOs){
		linienabfolgeSelected = new ArrayList<LinienabfolgeDTO>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		
		boolean returnElement = false;
		boolean fahrtGoing = false;
		
		for (LinienabfolgeDTO lDTO : lDTOs) {

			//Erst ab Start der Fahrt
			if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
				fahrtGoing = true;
			}
			//Workaround
			if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() 
					&& fDTO.getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
				uhrzeitTemp = fDTO.getUhrzeit();
			}
			if(fahrtGoing) {
				if(lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
					returnElement = true;
				}
			}
			
			// Sonderfall
			// Linienabfolge Element ist teil der Fahrt!
			if(returnElement) {
				haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleSDTO());
				uhrzeiten.add(uhrzeitTemp);
			}
			// Zum ende, da sonst Zeit zu früh hochgerechnet wird
			if(fahrtGoing) {
				addUhrzeit(fDTO, lDTO);
			}
			// Ende der Fahrt erreicht?
			if(fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
				haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleEDTO());
				uhrzeiten.add(uhrzeitTemp);
				returnElement = false;
				fahrtGoing = false;
			}
		}		 				
		return haltestelleDTOs;
	}	
	
	//Uhrzeiten Liste ans Frontend
	public List<String> getForTime() {
		return uhrzeiten;
	}
	
	// Uhrzeit hochrechnen, siehe Fahrplanbean
	public void addUhrzeit(FahrtDTO fDTO, LinienabfolgeDTO lDTO) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		if(uhrzeitTemp.equals("")) {
			try {
				d = df.parse(fDTO.getUhrzeit());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			try {
				d = df.parse(uhrzeitTemp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}							
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, lDTO.getVerbindungDTO().getDauer());
		uhrzeitTemp = df.format(cal.getTime());
	}
}
