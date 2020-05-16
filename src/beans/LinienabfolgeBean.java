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
import dto.LinienabfolgeDTO;
import entity.Linienabfolge;
import entity.Verbindung;
import util.NotificationUtils;
import util.SessionUtils;

@Named("linienAbfolgeBean")
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
	
	int hid;
	int bid;
	
	int dauer;
	
	@PostConstruct
	public void init( ) {
		linienabfolge = new LinienabfolgeDTO();
		//Das nochmal prüfen
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
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
	
	public void add() {		
		//Was ist mit Rücklinien?
		Linienabfolge linienabfolgeEntity = new Linienabfolge();
		
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		linienabfolgen = linienabfolgeDAO.getByBuslinie(bid);
		
		if(linienabfolgen.isEmpty()) {
			//Liste ist leer+
			linienabfolgeEntity.setBuslinie(buslinieDAO.get(bid));
			
			Verbindung verbindungEntity = new Verbindung();
			verbindungEntity.setHaltestelleS(haltestelleDAO.get(hid));
			verbindungEntity.setDauer(0);
		
			linienabfolgeEntity.setVerbindung(verbindungEntity);

			linienabfolgeEntity.setPosition(0);
			
			try {				
				linienabfolgeDAO.save(linienabfolgeEntity);		
				NotificationUtils.showMessage(false, 1, "XX:XX", "Linienabfolge 0 erfolgreich hinzugefügt", "Starthaltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "XX:XX", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
			return;
		} else {
			if(linienabfolgen.size() == 1) {
				if(linienabfolgen.get(0).getVerbindung().getHaltestelleE() == null) {
					//hier überprüfen, ob Verbindung schon existiert!
					//TODO verbindungDAO.getByHidsHide()
					linienabfolgen.get(0).getVerbindung().setHaltestelleE(haltestelleDAO.get(hid));
					linienabfolgen.get(0).getVerbindung().setDauer(dauer);
					
					try {
						verbindungDAO.save(linienabfolgen.get(0).getVerbindung());
						linienabfolgeDAO.save(linienabfolgen.get(0));		
						NotificationUtils.showMessage(false, 1, "XX:XX", "Linienabfolge 0 erfolgreich hinzugefügt", "Endhaltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
					} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "XX:XX", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
					return;
				}
			}
			linienabfolgeEntity.setBuslinie(buslinieDAO.get(bid));
			linienabfolgeEntity.setPosition(linienabfolgen.size());
			
			Verbindung verbindungEntity = new Verbindung();
			verbindungEntity.setDauer(dauer);
			verbindungEntity.setHaltestelleS(linienabfolgen.get(linienabfolgen.size()-1).getVerbindung().getHaltestelleE());
			verbindungEntity.setHaltestelleE(haltestelleDAO.get(hid));
			//TODO Verbindungentity in der Form existent?
			
			linienabfolgeEntity.setVerbindung(verbindungEntity);
			linienabfolgeEntity.setPosition(linienabfolgen.size()-1);

			try {
				verbindungDAO.save(verbindungEntity);
				linienabfolgeDAO.save(linienabfolgeEntity);		
				NotificationUtils.showMessage(false, 1, "XX:XX", "Linienabfolgeerfolgreich hinzugefügt", "Haltestelle wurde der Linienabfolge erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "XX:XX", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		}
	}
	
}