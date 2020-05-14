package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrplanDAO;
import dto.BuslinieDTO;
import dto.FahrplanDTO;
import entity.Buslinie;
import entity.Fahrplan;

@Named("fahrplanBean")
@ApplicationScoped
public class FahrplanBean {

	@Inject
	FahrplanDAO fahrplanDAO;

	FahrplanDTO neuFahrplanDTO;
	BuslinieDTO zugeodneteBuslinieDTO;

	@PostConstruct
	public void init() {
		this.neuFahrplanDTO = new FahrplanDTO();
		this.zugeodneteBuslinieDTO = new BuslinieDTO();
	}

	public void setNeuFahrplanDTO(FahrplanDTO neuFahrplanDTO) {
		this.neuFahrplanDTO = neuFahrplanDTO;
	}
	
	public FahrplanDTO getNeuFahrplanDTO() {
		return neuFahrplanDTO;
	}	

	public void setZugeodneteBuslinieDTO(BuslinieDTO zugeodneteBuslinieDTO) {
		this.zugeodneteBuslinieDTO = zugeodneteBuslinieDTO;
	}

	public BuslinieDTO getZugeodneteBuslinieDTO() {
		return zugeodneteBuslinieDTO;
	}

	public List<FahrplanDTO> getAll() {
		List<Fahrplan> fahrplans = new ArrayList<Fahrplan>();
		List<FahrplanDTO> fahrplanDTOs = new ArrayList<FahrplanDTO>();
		fahrplans = fahrplanDAO.getAll();
		fahrplans.forEach((fahrplan) -> fahrplanDTOs.add(new FahrplanDTO(fahrplan)));
		return fahrplanDTOs;
	}
	
	public void add() {
		Fahrplan fahrplan = new Fahrplan();	
		
	}
	
}