package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dto.BuslinieDTO;
import dto.FahrtDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import util.SessionUtils;

@Named("linienplanBean")
@ApplicationScoped
public class LinienplanBean {
	
	@Inject
	BuslinieDAO buslinieDAO;
	
	@Inject
	HaltestelleDAO haltestelleDAO;
	
	@Inject
	LinienabfolgeDAO linienabfolgeDAO;
	
	@Inject
	FahrtDAO fahrtDAO;
	
	BuslinieDTO buslinieDTO;
	HaltestelleDTO haltestelleDTO;
	
	int fid;
	
	int bid;
	int hid;
	
	@PostConstruct
	public void init() {
		buslinieDTO = new BuslinieDTO();
		haltestelleDTO = new HaltestelleDTO();
		bid = (int) SessionUtils.getSession().getAttribute("bid");
		hid = (int) SessionUtils.getSession().getAttribute("hid");
		buslinieDTO = buslinieDAO.get(bid);
		haltestelleDTO = haltestelleDAO.get(hid);
	}
	
	public void onPageLoad(){
		buslinieDTO = new BuslinieDTO();
		haltestelleDTO = new HaltestelleDTO();
		bid = (int) SessionUtils.getSession().getAttribute("bid");
		hid = (int) SessionUtils.getSession().getAttribute("hid");
		buslinieDTO = buslinieDAO.get(bid);
		haltestelleDTO = haltestelleDAO.get(hid);
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public HaltestelleDTO getHaltestelleDTO() {
		return haltestelleDTO;
	}

	public void setHaltestelleDTO(HaltestelleDTO haltestelleDTO) {
		this.haltestelleDTO = haltestelleDTO;
	}
	
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public List<FahrtDTO> getPossibleFahrten(){
		List<FahrtDTO> possibleFahrtDTOs = new ArrayList<FahrtDTO>();
		
		List<FahrtDTO> allFahrtDTOs = new ArrayList<FahrtDTO>();
		allFahrtDTOs = fahrtDAO.getByBuslinie(buslinieDTO.getBid());
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		linienabfolgeDTOs = linienabfolgeDAO.getAll("ASC");
		
		// Hinlinie -- TODO if rücklinie
		for (FahrtDTO fDTO : allFahrtDTOs) {
			boolean fahrtPossible = false;
			boolean fahrtGoing = false;
			List<LinienabfolgeDTO> fahrtLinie = new ArrayList<LinienabfolgeDTO>();
			for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
				// Anfang der Fahrt in der Abfolge finden
				if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
					fahrtGoing = true;
				}
				// Linienabfolge Element ist teil der Fahrt!
				if(fahrtGoing) {
					fahrtLinie.add(lDTO);
					// Ende erreicht?
					if(fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
						fahrtGoing = false;
					}
				}			
			}
			// 
			for (LinienabfolgeDTO flDTO : fahrtLinie) {
				if(flDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid() || flDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
					fahrtPossible = true;
				}
			}
			
			if(fahrtPossible) {
				possibleFahrtDTOs.add(fDTO);				
			}			
		}
		return possibleFahrtDTOs;
	}
	
	
}
