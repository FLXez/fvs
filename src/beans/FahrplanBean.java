package beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dao.FahrtDAO;
import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dto.FahrplanDTO;
import dto.FahrtDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import util.SessionUtils;

@Named("fahrplanBean")
@ApplicationScoped
public class FahrplanBean {

	@Inject
	BuslinieDAO buslinieDAO;

	@Inject
	HaltestelleDAO haltestelleDAO;

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;

	@Inject
	FahrtDAO fahrtDAO;

	List<FahrtDTO> possibleFahrtDTOs;

	HaltestelleDTO haltestelleDTO;
	int hid;

	String uhrzeit;
	int Zeithorizont;
	String uhrzeitHorizont;

	String uhrzeitCalc;

	@PostConstruct
	public void init() {
		uhrzeitCalc = "";
	}

	public void onPageLoad() {
		uhrzeitCalc = "";
	}

	public HaltestelleDTO getHaltestelleDTO() {
		return haltestelleDTO;
	}

	public void setHaltestellDTO(HaltestelleDTO haltestelleDTO) {
		this.haltestelleDTO = haltestelleDTO;
	}

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public int getZeithorizont() {
		return Zeithorizont;
	}

	public void setZeithorizont(int zeithorizont) {
		Zeithorizont = zeithorizont;
	}

	public List<HaltestelleDTO> getAllHaltestellen() {
		return haltestelleDAO.getAll();
	}
	
	public String forwardLinienabfolge(String bid) {
		SessionUtils.getSession().setAttribute("bid", bid);
		return "linienabfolge";
	}

	public void display() {
		haltestelleDTO = haltestelleDAO.get(hid);

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = new Date();

		try {
			d = df.parse(uhrzeit);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, Zeithorizont);
		uhrzeitHorizont = df.format(cal.getTime());
	}

	public List<FahrplanDTO> fahrplanInfo() {
		List<FahrplanDTO> fahrplanDTOs = new ArrayList<FahrplanDTO>();
		List<FahrtDTO> allFahrtDTOs = new ArrayList<FahrtDTO>();
		allFahrtDTOs = fahrtDAO.getAll();
		// Hinlinie
		for (FahrtDTO fDTO : allFahrtDTOs) {
			List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
			boolean fahrtPossible = false;
			boolean fahrtGoing = false;
			
			if(fDTO.getBuslinieDTO().getRichtung().equals("H")) {
				linienabfolgeDTOs = linienabfolgeDAO.getByBuslinieH(fDTO.getBuslinieDTO().getBid(), "ASC");
				
				for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
					// Anfang der Fahrt in der Abfolge finden
					if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid()) {
						fahrtGoing = true;
					}
					// Linienabfolge Element ist teil der Fahrt!
					if (fahrtGoing) {
						
						calcUhrzeit(fDTO, lDTO);
						
						if(lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid()) {
							fahrtPossible = true;
							fahrtGoing = false;
						}
					}
				}								
			} else {
				linienabfolgeDTOs = linienabfolgeDAO.getByBuslinieR(fDTO.getBuslinieDTO().getBid(), "DESC");	
				
				for (LinienabfolgeDTO lDTO : linienabfolgeDTOs) {
					// Anfang der Fahrt in der Abfolge finden
					if (fDTO.getHaltestelleSDTO().getHid() == lDTO.getVerbindungDTO().getHaltestelleEDTO().getHid()) {
						fahrtGoing = true;
					}
					// Linienabfolge Element ist teil der Fahrt!
					if (fahrtGoing) {
						
						calcUhrzeit(fDTO, lDTO);
						
						if(lDTO.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
							fahrtPossible = true;
							fahrtGoing = false;
						}
					}
				}								
			}
			if (fahrtPossible) {
				Date d1 = new Date();
				Date d2 = new Date();
				Date d3 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				try {
					d1 = df.parse(uhrzeit);
					d2 = df.parse(uhrzeitCalc);
					d3 = df.parse(uhrzeitHorizont);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(d1.before(d2) && d3.after(d2)) {
					int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);	
					fahrplanDTOs.add(new FahrplanDTO(fDTO.getBuslinieDTO(), fDTO.getHaltestelleEDTO(), uhrzeitCalc, Integer.toString(randomNum)));						
				}
				uhrzeitCalc = "";
			}
		}		
		return fahrplanDTOs;
	}

	public void calcUhrzeit(FahrtDTO fDTO, LinienabfolgeDTO lDTO) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		if (uhrzeitCalc.equals("")) {
			try {
				d = df.parse(fDTO.getUhrzeit());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				d = df.parse(uhrzeitCalc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, lDTO.getVerbindungDTO().getDauer());
		uhrzeitCalc = df.format(cal.getTime());
	}
}
