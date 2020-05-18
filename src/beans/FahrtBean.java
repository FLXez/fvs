package beans;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dao.LinienabfolgeDAO;
import dao.VerbindungDAO;
import dto.BuslinieDTO;
import dto.FahrtDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import entity.Buslinie;
import entity.Fahrt;
import entity.Linienabfolge;
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
	
	FahrtDTO fahrtDTO;
	
	BuslinieDTO buslinieHDTO;
	BuslinieDTO buslinieRDTO;
	
	LinienabfolgeDTO linienabfolgeDTO;
	
	HaltestelleDTO haltestelleEDTO;
	HaltestelleDTO haltestelleSDTO;
	
	int bid;
	
	int hids;
	int hide;
	
	Time uhrzeit;

	@PostConstruct
	public void init() {
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		Buslinie buslinie = buslinieDAO.get(bid);
		
		List<Buslinie> buslinien = new ArrayList<Buslinie>();		
		buslinien = buslinieDAO.getByNummer(buslinie.getNummer());
		//ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = new BuslinieDTO(buslinien.get(0));
		buslinieRDTO = new BuslinieDTO(buslinien.get(1));	
	}
	
	public void onPageLoad() {
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
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

	public HaltestelleDTO getHaltestelleEDTO() {
		return haltestelleEDTO;
	}

	public void setHaltestelleEDTO(HaltestelleDTO haltestelleEDTO) {
		this.haltestelleEDTO = haltestelleEDTO;
	}

	public HaltestelleDTO getHaltestelleSDTO() {
		return haltestelleSDTO;
	}

	public void setHaltestelleSDTO(HaltestelleDTO haltestelleSDTO) {
		this.haltestelleSDTO = haltestelleSDTO;
	}

	public Time getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(Time uhrzeit) {
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
		linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
		
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();	
		//Hinlinie wird bearbeitet
		if(bid == buslinieHDTO.getBid()) {			
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleS())));			
		} else {
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleE())));
		}
		
		return haltestelleDTOs;
	}
	
	public List<HaltestelleDTO> getAllHaltestellenE() {		
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
		
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();	
		//Hinlinie wird bearbeitet
		if(bid == buslinieHDTO.getBid()) {			
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleE())));			
		} else {
			linienabfolgen.forEach((linienabfolge) -> haltestelleDTOs.add(new HaltestelleDTO(linienabfolge.getVerbindung().getHaltestelleS())));
		}
		
		return haltestelleDTOs;
	}
	
	
	public void add() {
		

	}

}