package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.HaltestelleDAO;
import dao.LinienabfolgeDAO;
import dto.BuslinieDTO;
import dto.HaltestelleDTO;
import dto.LinienabfolgeDTO;
import util.SessionUtils;

@Named("linienhaltestellenBean")
@ApplicationScoped
public class LinienhaltestellenBean {

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;

	@Inject
	HaltestelleDAO haltestelleDAO;

	// Haltestelle mit der die Seite aufgerufen wird
	HaltestelleDTO haltestelleDTO;
	
	int hid;

	@PostConstruct
	public void init() {
		haltestelleDTO = new HaltestelleDTO();
		hid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("hid"));
		haltestelleDTO = haltestelleDAO.get(hid);
	}

	public void onPageLoad() {
		haltestelleDTO = new HaltestelleDTO();
		hid = Integer.parseInt((String) SessionUtils.getSession().getAttribute("hid"));
		haltestelleDTO = haltestelleDAO.get(hid);
	}
	
	public HaltestelleDTO getHaltestelleDTO() {
		return haltestelleDTO;
	}

	public void setHaltestelleDTO(HaltestelleDTO haltestelleDTO) {
		this.haltestelleDTO = haltestelleDTO;
	}

	public List<BuslinieDTO> getAllByHid() {
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		linienabfolgeDTOs = linienabfolgeDAO.getByHid(haltestelleDTO.getHid(), "ASC");
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();

		for (LinienabfolgeDTO l : linienabfolgeDTOs) {
			buslinieDTOs.add(l.getBuslinieHDTO());	
			buslinieDTOs.add(l.getBuslinieRDTO());
		}
		return buslinieDTOs;
	}
}
