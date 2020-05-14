package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dto.BuslinieDTO;
import entity.Buslinie;

@Named("buslinieBean")
@ApplicationScoped
public class BuslinieBean {

	@Inject
	BuslinieDAO buslinieDAO;
	
	BuslinieDTO neuBuslinieDTO;
	
	@PostConstruct
	public void init() {
		this.neuBuslinieDTO = new BuslinieDTO();
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.neuBuslinieDTO = buslinieDTO;
	}
	
	public BuslinieDTO getBuslinieDTO() {
		return neuBuslinieDTO;
	}

	public List<BuslinieDTO> getAll() {
		List<Buslinie> busliniens = new ArrayList<Buslinie>();
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		busliniens = buslinieDAO.getAll();
		busliniens.forEach((buslinie) -> buslinieDTOs.add(new BuslinieDTO(buslinie)));
		return buslinieDTOs;
	}
	
	public void add() {
		Buslinie buslinie = new Buslinie();
		buslinie.setNummer(this.neuBuslinieDTO.getNummer());
		buslinie.setRichtung(this.neuBuslinieDTO.getRichtung());
	} 
}