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
import entity.Buslinie;
import entity.Fahrt;
import entity.Linienabfolge;
import util.NotificationUtils;
import util.SessionUtils;

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
	
	FahrtDTO fahrtDTO;
	
	BuslinieDTO buslinieHDTO;
	BuslinieDTO buslinieRDTO;
	
	LinienabfolgeDTO linienabfolgeDTO;
	
	HaltestelleDTO haltestelleEDTO;
	HaltestelleDTO haltestelleSDTO;

	//Frontend listen
	List<HaltestelleDTO> feHaltestelleSDTOs;
	List<HaltestelleDTO> feHaltestelleEDTOs;
	
	int bid;
	
	int hids;
	int hide;
	
	String uhrzeit;
	Pattern uP;
	Matcher m;
	

	@PostConstruct
	public void init() {
		uP = Pattern.compile("([0-1][0-9]|[2][0-3]):([0-5][0-9])");
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		Buslinie buslinie = buslinieDAO.get(bid);
		
		List<Buslinie> buslinien = new ArrayList<Buslinie>();		
		buslinien = buslinieDAO.getByNummer(buslinie.getNummer());
		//ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = new BuslinieDTO(buslinien.get(0));
		buslinieRDTO = new BuslinieDTO(buslinien.get(1));
		
		feHaltestelleEDTOs = new ArrayList<HaltestelleDTO>();
		feHaltestelleSDTOs = new ArrayList<HaltestelleDTO>();
	}
	
	public void onPageLoad() {
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));		
		Buslinie buslinie = buslinieDAO.get(bid);
		
		List<Buslinie> buslinien = new ArrayList<Buslinie>();		
		buslinien = buslinieDAO.getByNummer(buslinie.getNummer());
		//ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = new BuslinieDTO(buslinien.get(0));
		buslinieRDTO = new BuslinieDTO(buslinien.get(1));	
		
		getAllFahrtenBid();
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public FahrtDTO getFahrtDTO() {
		return fahrtDTO;
	}

	public void setFahrtDTO(FahrtDTO fahrtDTO) {
		this.fahrtDTO = fahrtDTO;
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

	public LinienabfolgeDTO getLinienabfolgeDTO() {
		return linienabfolgeDTO;
	}

	public void setLinienabfolgeDTO(LinienabfolgeDTO linienabfolgeDTO) {
		this.linienabfolgeDTO = linienabfolgeDTO;
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
		List<Fahrt> fahrten = new ArrayList<Fahrt>();
		List<FahrtDTO> fahrtDTOs = new ArrayList<FahrtDTO>();
		fahrten = fahrtDAO.getAll();
		fahrten.forEach((fahrt) -> fahrtDTOs.add(new FahrtDTO(fahrt)));
		return fahrtDTOs;
	}
	
	public List<FahrtDTO> getAllFahrtenBid() {
		//Liste aufbauen, um diese durchgehen zu können
		List<Fahrt> fahrten = new ArrayList<Fahrt>();
		List<FahrtDTO> fahrtDTOs = new ArrayList<FahrtDTO>();
		fahrten = fahrtDAO.getByBuslinie(bid);
		fahrten.forEach((fahrt) -> fahrtDTOs.add(new FahrtDTO(fahrt)));
		return fahrtDTOs;
	}
	
	public List<HaltestelleDTO> getAllHaltestellenS() {		
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		
		//Hinlinie wird bearbeitet
		if(bid == buslinieHDTO.getBid()) {			
			linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleS())));			
		} else {
			linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "DESC");
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleE())));
		}
		
		feHaltestelleSDTOs = haltestelleDTOs;
		
		return haltestelleDTOs;
	}
	
	public List<HaltestelleDTO> getAllHaltestellenE() {		
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();		
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		
		//Hinlinie wird bearbeitet
		if(bid == buslinieHDTO.getBid()) {
			linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleE())));			
		} else {
			linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "DESC");
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleS())));
		}
		
		feHaltestelleEDTOs = haltestelleDTOs;

		return haltestelleDTOs;
	}
	
	
	public void add() {
		if(linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC").isEmpty()) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Keine Linienabfolge", "Bitte erstellen Sie zuerst eine Linienabfolge.");
			return;
		}
		
		m = uP.matcher(uhrzeit);
		if(!m.matches()) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Uhrzeit ungültig", "Bitte geben Sie eine gültige Uhrzeit an.");
			return;
		}
		
		List<Fahrt> fahrten = new ArrayList<Fahrt>();
		fahrten = fahrtDAO.getByBuslinie(bid);

		//haltestelle aus Liste ziehen, damit via indexOf findbar
		for(HaltestelleDTO haltestelle : feHaltestelleSDTOs) {
			if(haltestelle.getHid() == hids) {
				haltestelleSDTO = haltestelle;
			}
		}

		//haltestelle aus Liste ziehen, damit via indexOf findbar
		for(HaltestelleDTO haltestelle : feHaltestelleEDTOs) {
			if(haltestelle.getHid() == hide) {
				haltestelleEDTO = haltestelle;
			}
		}
		
		if(feHaltestelleSDTOs.indexOf(haltestelleSDTO) > feHaltestelleEDTOs.indexOf(haltestelleEDTO)) {
			NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Starthaltestelle nach Zielhaltestelle", "Die Starthaltestelle darf sich nicht nach der Zielhaltestelle befinden.");
			return;							
		}
		
		
		for(Fahrt fahrt : fahrten) {
			if(fahrt.getHaltestelleE().getHid() == haltestelleEDTO.getHid() && fahrt.getHaltestelleS().getHid() == haltestelleSDTO.getHid() && fahrt.getUhrzeit().equals(uhrzeit)) {
				NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Fahrt bereits vorhanden", "Diese Fahrt ist bereits vorhanden.");
				return;				
			}
		}
		
		Fahrt fahrt = new Fahrt();
		fahrt.setBuslinie(buslinieDAO.get(bid));
		fahrt.setUhrzeit(uhrzeit);
		fahrt.setHaltestelleS(haltestelleDAO.get(hids));
		fahrt.setHaltestelleE(haltestelleDAO.get(hide));
		
		try {
			fahrtDAO.save(fahrt);
			NotificationUtils.showMessage(false, 1, "fahrt:uhrzeit", "Fahrt hinzugefügt", "Die Fahrt wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "fahrt:uhrzeit", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
	}

}