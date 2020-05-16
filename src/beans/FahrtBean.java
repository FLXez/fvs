package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrtDAO;
import dto.BuslinieDTO;
import dto.FahrtDTO;
import entity.Fahrt;

@Named("fahrplanBean")
@ApplicationScoped
public class FahrtBean {

	@Inject
	FahrtDAO fahrtDAO;

	FahrtDTO neuFahrplanDTO;
	BuslinieDTO zugeodneteBuslinieDTO;

	@PostConstruct
	public void init() {
		this.neuFahrplanDTO = new FahrtDTO();
		this.zugeodneteBuslinieDTO = new BuslinieDTO();
	}

	public void setNeuFahrplanDTO(FahrtDTO neuFahrplanDTO) {
		this.neuFahrplanDTO = neuFahrplanDTO;
	}

	public FahrtDTO getNeuFahrplanDTO() {
		return neuFahrplanDTO;
	}

	public void setZugeodneteBuslinieDTO(BuslinieDTO zugeodneteBuslinieDTO) {
		this.zugeodneteBuslinieDTO = zugeodneteBuslinieDTO;
	}

	public BuslinieDTO getZugeodneteBuslinieDTO() {
		return zugeodneteBuslinieDTO;
	}

	public List<FahrtDTO> getAll() {
		List<Fahrt> fahrten = new ArrayList<Fahrt>();
		List<FahrtDTO> fahrtDTOs = new ArrayList<FahrtDTO>();
		fahrten = fahrtDAO.getAll();
		fahrten.forEach((fahrt) -> fahrtDTOs.add(new FahrtDTO(fahrt)));
		return fahrtDTOs;
	}

	public void add() {

	}

}