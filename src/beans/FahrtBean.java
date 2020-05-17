package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dto.BuslinieDTO;
import dto.FahrtDTO;
import entity.Fahrt;
import util.SessionUtils;

@Named("fahrtBean")
@ApplicationScoped
public class FahrtBean {

	@Inject
	FahrtDAO fahrtDAO;
	
	@Inject
	BuslinieDAO buslinieDAO;
	
	BuslinieDTO buslinieDTO;
	
	int bid;
	

	@PostConstruct
	public void init() {
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		buslinieDTO = new BuslinieDTO(buslinieDAO.get(bid));
	}
	
	public void onPageLoad() {
		bid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("bid"));
		buslinieDTO = new BuslinieDTO(buslinieDAO.get(bid));
		getAllFahrtenBid();
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
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
	
	public void add() {
		

	}

}