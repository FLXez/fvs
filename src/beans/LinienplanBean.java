package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	List<LinienabfolgeDTO> linienabfolgeSelected;
	List<String> uhrzeiten;
	String uhrzeitTemp;

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

	public void onPageLoad() {
		buslinieDTO = new BuslinieDTO();
		haltestelleDTO = new HaltestelleDTO();
		bid = Integer.parseInt(SessionUtils.getSession().getAttribute("bid").toString());
		hid = Integer.parseInt(SessionUtils.getSession().getAttribute("hid").toString());
		buslinieDTO = buslinieDAO.get(bid);
		haltestelleDTO = haltestelleDAO.get(hid);
		linienabfolgeSelected = new ArrayList<LinienabfolgeDTO>();
		uhrzeiten = new ArrayList<String>();
		uhrzeitTemp = new String();
		//Workaround
		fid = this.fid;
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
	
	// Workaround
	public void submit() {
		setFid(this.fid); 
	}

	public List<FahrtDTO> getPossibleFahrten() {
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
				if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
					fahrtGoing = true;
				}
				// Linienabfolge Element ist teil der Fahrt!
				if (fahrtGoing) {
					fahrtLinie.add(lDTO);
					// Ende erreicht?
					if (fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
						fahrtGoing = false;
					}
				}
			}
			//
			for (LinienabfolgeDTO flDTO : fahrtLinie) {
				if (flDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()
						|| flDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
					fahrtPossible = true;
				}
			}

			if (fahrtPossible) {
				possibleFahrtDTOs.add(fDTO);
			}
		}
		return possibleFahrtDTOs;
	}

	public List<HaltestelleDTO> getForPath(){
		linienabfolgeSelected = new ArrayList<LinienabfolgeDTO>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		uhrzeiten = new ArrayList<String>();
		uhrzeitTemp = new String();
		
		FahrtDTO fDTO = fahrtDAO.get(fid);
		
		List<LinienabfolgeDTO> lDTOs = new ArrayList<LinienabfolgeDTO>();
		lDTOs = linienabfolgeDAO.getByBuslinieH(bid, "ASC");
		
		boolean returnElement = false;
		boolean fahrtGoing = false;
		
		for (LinienabfolgeDTO lDTO : lDTOs) {

			//Erst ab Start der Fahrt
			if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
				fahrtGoing = true;
			}
			//Workaround
			if(fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() 
					&& fDTO.getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
				uhrzeitTemp = fDTO.getUhrzeit();
			}
			if(fahrtGoing) {
				if(lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
					returnElement = true;
				}
			}
			
			// Sonderfall
			// Linienabfolge Element ist teil der Fahrt!
			if(returnElement) {
				haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleSDTO());
				uhrzeiten.add(uhrzeitTemp);
				System.out.println(uhrzeiten.get(uhrzeiten.size()-1));
			}
			// Zum ende, da sonst Zeit zu früh hochgerechnet wird
			if(fahrtGoing) {
				addUhrzeit(fDTO, lDTO);
			}
			// Ende der Fahrt erreicht?
			if(fDTO.getHaltestelleEDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
				haltestelleDTOs.add(lDTO.getVerbindungDTO().getHaltestelleEDTO());
				uhrzeiten.add(uhrzeitTemp);
				returnElement = false;
				fahrtGoing = false;
			}
		}		 				
		return haltestelleDTOs;
	}
	
	public List<String> getForTime() {
		return uhrzeiten;
	}
	
	public void addUhrzeit(FahrtDTO fDTO, LinienabfolgeDTO lDTO) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		if(uhrzeitTemp.equals("")) {
			try {
				d = df.parse(fDTO.getUhrzeit());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			try {
				d = df.parse(uhrzeitTemp);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}							
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, lDTO.getVerbindungDTO().getDauer());
		uhrzeitTemp = df.format(cal.getTime());
	}
}
