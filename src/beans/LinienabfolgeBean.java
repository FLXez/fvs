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
import entity.Buslinie;
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
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		
		Buslinie buslinie = buslinieDAO.get(bid);
		List<Buslinie> buslinien = new ArrayList<Buslinie>();		
		buslinien = buslinieDAO.getByNummer(buslinie.getNummer());
		//ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = new BuslinieDTO(buslinien.get(0));
		buslinieRDTO = new BuslinieDTO(buslinien.get(1));
		
		verbindungDTO = new VerbindungDTO();
	}
	
	public void onPageLoad() {
		linienabfolge = new LinienabfolgeDTO();
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		
		Buslinie buslinie = buslinieDAO.get(bid);
		List<Buslinie> buslinien = new ArrayList<Buslinie>();		
		buslinien = buslinieDAO.getByNummer(buslinie.getNummer());
		//ORDER BY richtung - somit erst H, dann R
		buslinieHDTO = new BuslinieDTO(buslinien.get(0));
		buslinieRDTO = new BuslinieDTO(buslinien.get(1));
		
		getAllLinienabfolgenBid();
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
		if(bid == buslinieHDTO.getBid()) {
			linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");			
		} else {
			linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "DESC");
		}
		linienabfolgen.forEach((linienabfolge) -> linienabfolgeDTOs.add(new LinienabfolgeDTO(linienabfolge)));
		
		return linienabfolgeDTOs;		
	}
	
	public void add() {		
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		linienabfolgen = linienabfolgeDAO.getByBuslinien(buslinieHDTO.getBid(), buslinieRDTO.getBid(), "ASC");
		
		if(dauer == 0) {
			NotificationUtils.showMessage(false, 2, "linien:dauer", "Dauer 0", "Bitte geben Sie eine Dauer an.");
			return;
		}
		
		if(bid == buslinieHDTO.getBid()) {
			hAdd(linienabfolgen);		
			dauer = 0;
		} else {
			rAdd(linienabfolgen);
			dauer = 0;
		}
		
	}
	
	public void hAdd(List<Linienabfolge> linienabfolgen) {
		Linienabfolge linienabfolgeEntity = new Linienabfolge();
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
			
			if(verbindungDTO.getHaltestelleSDTO().getHid() == verbindungDTO.getHaltestelleEDTO().getHid()) {
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
			
			checkVerbindung(haltestelleS, haltestelleE);
			
			linienabfolgeEntity.setPosition(0);			
			linienabfolgeEntity.setBuslinieH(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeEntity.setBuslinieR(buslinieDAO.get(buslinieRDTO.getBid()));
			
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
			if(haltestelleS.getHid() == haltestelleE.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen", "Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}
			
			if(linienabfolgen.get(0).getVerbindung().getHaltestelleS().getHid() == haltestelleE.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren", "Die Haltestelle ist in der Abfolge bereits enthalten.");
				return;
			}
			
			for (Linienabfolge linienabfolge : linienabfolgen) {
				if(linienabfolge.getVerbindung().getHaltestelleE().getHid() == haltestelleE.getHid()) {
					NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren", "Die Haltestelle ist in der Abfolge bereits enthalten.");
					return;
				}
			}
			
			checkVerbindung(haltestelleS, haltestelleE);

			linienabfolgeEntity.setBuslinieH(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeEntity.setBuslinieR(buslinieDAO.get(buslinieRDTO.getBid()));
			linienabfolgeEntity.setPosition(linienabfolgen.get(linienabfolgen.size()-1).getPosition()+1);

			try {
				linienabfolgeEntity.setVerbindung(verbindungDAO.getByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid()));
				linienabfolgeDAO.save(linienabfolgeEntity);		
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolgeerfolgreich hinzugefügt", "Haltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		}
	}	
	
	public void rAdd(List<Linienabfolge> linienabfolgen) {
		Linienabfolge linienabfolgeEntity = new Linienabfolge();
		
		//Erstes Linienabfolgeelement
		if(linienabfolgen.isEmpty()) {
			
			//Buslinienwechsel während erstellung "kompensieren"
			if(bidSel != bid) {
				bidSel = bid;
				verbindungDTO.setHaltestelleEDTO(new HaltestelleDTO(haltestelleDAO.get(hid)));
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Starthaltestelle hinzugefügt", "Bitte wählen Sie nun die erste Zielhaltestelle und die Dauer aus.");				
				return;
			}

			verbindungDTO.setHaltestelleSDTO(new HaltestelleDTO(haltestelleDAO.get(hid)));
			
			if(verbindungDTO.getHaltestelleSDTO().getHid() == verbindungDTO.getHaltestelleEDTO().getHid()) {
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
			
			checkVerbindung(haltestelleS, haltestelleE);
			
			linienabfolgeEntity.setPosition(0);					
			linienabfolgeEntity.setBuslinieH(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeEntity.setBuslinieR(buslinieDAO.get(buslinieRDTO.getBid()));
			
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
			Haltestelle haltestelleS = haltestelleDAO.get(hid);
			Haltestelle haltestelleE = linienabfolgen.get(0).getVerbindung().getHaltestelleS();
			if(haltestelleS.getHid() == haltestelleE.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Identische Haltestellen", "Bitte wählen Sie eine andere Haltestelle aus.");
				return;
			}
			
			if(linienabfolgen.get(linienabfolgen.size()-1).getVerbindung().getHaltestelleE().getHid() == haltestelleS.getHid()) {
				NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren", "Die Haltestelle ist in der Abfolge bereits enthalten.");
				return;
			}
			
			for (Linienabfolge linienabfolge : linienabfolgen) {
				if(linienabfolge.getVerbindung().getHaltestelleS().getHid() == haltestelleS.getHid()) {
					NotificationUtils.showMessage(false, 2, "linien:dauer", "Haltestelle bereits angefahren", "Die Haltestelle ist in der Abfolge bereits enthalten.");
					return;
				}
			}

			checkVerbindung(haltestelleS, haltestelleE);

			linienabfolgeEntity.setBuslinieH(buslinieDAO.get(buslinieHDTO.getBid()));
			linienabfolgeEntity.setBuslinieR(buslinieDAO.get(buslinieRDTO.getBid()));
			//Erstes Element wählen, davon Position -1
			linienabfolgeEntity.setPosition(linienabfolgen.get(0).getPosition()-1);

			try {
				linienabfolgeEntity.setVerbindung(verbindungDAO.getByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid()));
				linienabfolgeDAO.save(linienabfolgeEntity);		
				NotificationUtils.showMessage(false, 1, "linien:dauer", "Linienabfolgeerfolgreich hinzugefügt", "Haltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "linien:dauer", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		}
	}	
	
	private void checkVerbindung(Haltestelle haltestelleS, Haltestelle haltestelleE) {
		if(!verbindungDAO.findByHaltestellen(haltestelleS.getHid(), haltestelleE.getHid())) {
			Verbindung verbindungEntity = new Verbindung();
			verbindungEntity.setHaltestelleS(haltestelleS);
			verbindungEntity.setHaltestelleE(haltestelleE);
			verbindungEntity.setDauer(dauer);
			verbindungDAO.save(verbindungEntity);
		}
	}
}