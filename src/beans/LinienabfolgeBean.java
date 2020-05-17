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
import entity.Haltestelle;
import entity.Linienabfolge;
import entity.Verbindung;
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
	
	BuslinieDTO buslinie;
	
	VerbindungDTO verbindungDTO;
	
	int hid;
	int bid;
	int bidSel;
	
	int dauer;
	
	@PostConstruct
	public void init() {
		linienabfolge = new LinienabfolgeDTO();
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		buslinie = new BuslinieDTO(buslinieDAO.get(bid));
		verbindungDTO = new VerbindungDTO();
	}
	
	public void refresh() {
		linienabfolge = new LinienabfolgeDTO();
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		buslinie = new BuslinieDTO(buslinieDAO.get(bid));		
		getAllLinienabfolgenBid();
		System.out.println(bid);
	}
	
	public BuslinieDTO getBuslinie() {
		return buslinie;
	}


	public void setBuslinie(BuslinieDTO buslinie) {
		this.buslinie = buslinie;
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


	public LinienabfolgeDTO getLinienabfolge() {
		return linienabfolge;
	}


	public void setLinienabfolge(LinienabfolgeDTO linienabfolge) {
		this.linienabfolge = linienabfolge;
	}


	public List<LinienabfolgeDTO> getAllLinienabfolgen() { 
		// Listen aufbauen, um diese durchgehen zu können
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		linienabfolgen = linienabfolgeDAO.getAll();
		// Verbindung Entity wird zu Verbindung DTO
		linienabfolgen.forEach((linienabfolge) -> linienabfolgeDTOs.add(new LinienabfolgeDTO(linienabfolge)));
		
		return linienabfolgeDTOs;
	}
	
	public List<LinienabfolgeDTO> getAllLinienabfolgenBid() {
		// Listen aufbauen, um diese durchgehen zu können
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		linienabfolgen = linienabfolgeDAO.getByBuslinie(bid);
		linienabfolgen.forEach((linienabfolge) -> linienabfolgeDTOs.add(new LinienabfolgeDTO(linienabfolge)));
		
		return linienabfolgeDTOs;		
	}
	
	public void add() {		
		refresh();
		//Was ist mit Rücklinien?
		Linienabfolge linienabfolgeEntity = new Linienabfolge();
		
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		linienabfolgen = linienabfolgeDAO.getByBuslinie(bid);
		
		//Erstes Linienabfolgeelement
		if(linienabfolgen.isEmpty()) {
			
			//Buslinienwechsel während erstellung "kompensieren"
			if(bidSel != bid) {
				bidSel = bid;
				verbindungDTO.setHaltestelleSDTO(new HaltestelleDTO(haltestelleDAO.get(hid)));
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Starthaltestelle hinzugefügt", "Bitte wählen Sie nun die erste Zielhaltestelle und die Dauer aus.");				
				return;
			}

			verbindungDTO.setHaltestelleEDTO(new HaltestelleDTO(haltestelleDAO.get(hid)));
			
			if(verbindungDTO.getHaltestelleSDTO() == verbindungDTO.getHaltestelleEDTO()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen", "Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}
			
			//Haltestellen parsen
			Haltestelle haltestelleS = new Haltestelle();
			haltestelleS.setBezeichnung(verbindungDTO.getHaltestelleSDTO().getBezeichnung());
			haltestelleS.setHid(verbindungDTO.getHaltestelleSDTO().getHid());
			Haltestelle haltestelleE = new Haltestelle();
			haltestelleE.setBezeichnung(verbindungDTO.getHaltestelleEDTO().getBezeichnung());
			haltestelleE.setHid(verbindungDTO.getHaltestelleEDTO().getHid());
			
			if(!verbindungDAO.findByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid())) {
				//Verbindung erzeugen, da nonexistent
				Verbindung verbindungEntity = new Verbindung();
				verbindungEntity.setHaltestelleS(haltestelleS);
				verbindungEntity.setHaltestelleE(haltestelleE);
				verbindungEntity.setDauer(dauer);
				verbindungDAO.save(verbindungEntity);
			}
			
			linienabfolgeEntity.setPosition(0);			
			linienabfolgeEntity.setBuslinie(buslinieDAO.get(bid));
			
			//Speichern der Linienabfolge
			try {
				linienabfolgeEntity.setVerbindung(verbindungDAO.getByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid()));
				linienabfolgeDAO.save(linienabfolgeEntity);	
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolge 0 erfolgreich hinzugefügt", "Starthaltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
			return;
		//n-tes Linienabfolgeelement
		} else {
			//HaltestelleStart ist die Endhaltestelle des vorherigen Elements
			Haltestelle haltestelleS = linienabfolgen.get(linienabfolgen.size()-1).getVerbindung().getHaltestelleE();
			Haltestelle haltestelleE = haltestelleDAO.get(hid);
			if(haltestelleS == haltestelleE) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen", "Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}
			
			//Überprüfung ob Verbindung bereits vorhanden
			if(!verbindungDAO.findByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid())) {
				Verbindung verbindungEntity = new Verbindung();
				verbindungEntity.setHaltestelleS(haltestelleS);
				verbindungEntity.setHaltestelleE(haltestelleE);
				verbindungEntity.setDauer(dauer);
				verbindungDAO.save(verbindungEntity);
			}

			linienabfolgeEntity.setBuslinie(buslinieDAO.get(bid));
			//Position beginnt bei 0, somit ist bei einem Linienabfolge die Size = 1 und kann somit fürs nächste als
			//Position genutzt werden
			linienabfolgeEntity.setPosition(linienabfolgen.size());

			try {
				linienabfolgeEntity.setVerbindung(verbindungDAO.getByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid()));
				linienabfolgeDAO.save(linienabfolgeEntity);		
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolgeerfolgreich hinzugefügt", "Haltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		}
	}
	
}