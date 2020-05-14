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

@Named("buslinieBean")
@ApplicationScoped
public class BuslinieBean {

	@Inject
	BuslinieDAO buslinieDAO;
	
	BuslinieDTO newBuslinieDTO;
	
	@PostConstruct
	public void init() {
		this.newBuslinieDTO = new BuslinieDTO();
	}

	public void setNewBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.newBuslinieDTO = buslinieDTO;
	}
	
	public BuslinieDTO getNewBuslinieDTO() {
		return newBuslinieDTO;
	}

	public List<BuslinieDTO> getAllBuslinien() {
		List<Buslinie> busliniens = new ArrayList<Buslinie>();
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		busliniens = buslinieDAO.getAll();
		busliniens.forEach((buslinie) -> buslinieDTOs.add(new BuslinieDTO(buslinie)));
		return buslinieDTOs;
	}
	
	public void add() {
		
		if(newBuslinieDTO.getNummer() == 0) {
			NotificationUtils.showMessage(false, 1, "addLine:direction", "Nummer leer", "Bitte vergeben Sie eine Nummer.");
			return;
		}
		if(newBuslinieDTO.getRichtung().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "addLine:direction", "Richtung leer", "Bitte vergeben Sie eine Richtung.");
			return;
		}
		
		if(!buslinieDAO.findByNummerAndRichtung(newBuslinieDTO.getNummer(), newBuslinieDTO.getRichtung())) {
			
			Buslinie buslinie = new Buslinie();
			buslinie.setNummer(this.newBuslinieDTO.getNummer());
			buslinie.setRichtung(this.newBuslinieDTO.getRichtung());
			
			try {
				buslinieDAO.save(buslinie);
				newBuslinieDTO = new BuslinieDTO();
				NotificationUtils.showMessage(false, 1, "addLine:direction", "Buslinie hinzugefügt", "Die Buslinie wurde erfolgreich hinzugefügt.");
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "addLine:direction", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		} else { NotificationUtils.showMessage(false, 1, "addLine:direction", "Buslinie bereits vorhanden", "Diese Buslinie ist bereits vorhanden."); }
	} 
}