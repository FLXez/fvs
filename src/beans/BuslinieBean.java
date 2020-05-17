package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dto.BuslinieDTO;
import entity.Buslinie;
import util.NotificationUtils;
import util.SessionUtils;

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
		List<Buslinie> busliniens = new ArrayList<Buslinie>();
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		busliniens = buslinieDAO.getAll();
		busliniens.forEach((buslinie) -> buslinieDTOs.add(new BuslinieDTO(buslinie)));
		return buslinieDTOs;
	}
	
	public String forwardLinienabfolge(String bid) {
		SessionUtils.getSession().setAttribute("bid", bid);
		return "linienabfolge";
	}
	
	public String forwardFahrt(String bid) {
		SessionUtils.getSession().setAttribute("bid", bid);
		return "fahrt";
	}
	
	public void add() {
		
		if(buslinieDTO.getNummer() == 0) {
			NotificationUtils.showMessage(false, 1, "addLine:number", "Nummer leer", "Bitte vergeben Sie eine Nummer.");
			return;
		}
		
		if(!buslinieDAO.existsByNummer(buslinieDTO.getNummer()).isEmpty()) {
			NotificationUtils.showMessage(false, 1, "addLine:number", "Buslinie bereits vorhanden", "Diese Buslinie ist bereits vorhanden."); 
			return; 
		}
		
		Buslinie buslinie = new Buslinie();
		buslinie.setNummer(this.buslinieDTO.getNummer());
		buslinie.setRichtung("H");
		
		try {
			buslinieDAO.save(buslinie);
			NotificationUtils.showMessage(false, 1, "addLine:number", "Buslinie H hinzugefügt", "Die Buslinie (Hinfahrt) wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "addLine:number", "Unerwarteter Fehler (H)", "Es ist ein unerwarteter Fehler aufgetreten."); }

		buslinie.setRichtung("R");
		
		try {
			buslinieDAO.save(buslinie);
			buslinieDTO = new BuslinieDTO();
			NotificationUtils.showMessage(false, 1, "addLine:number", "Buslinie R hinzugefügt", "Die Buslinie (Hinfahrt und Rückfahrt) wurde erfolgreich hinzugefügt.");
		} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "addLine:number", "Unerwarteter Fehler (R)", "Es ist ein unerwarteter Fehler aufgetreten."); } 
	} 
}