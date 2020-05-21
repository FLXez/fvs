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

//	public List<BuslinieDTO> getAllByHid() {
//		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
//		linienabfolgeDTOs = linienabfolgeDAO.getAll();
//		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
//
//		for (LinienabfolgeDTO l : linienabfolgeDTOs) {
//			if (l.getVerbindungDTO().getHaltestelleEDTO().getHid() == haltestelleDTO.getHid() || l.getVerbindungDTO().getHaltestelleSDTO().getHid() == haltestelleDTO.getHid()) {
//				// Überprüfung, ob Buslinie bereits gespeichert.
//				if(buslinieDTOs.get(buslinieDTOs.size()-1).getNummer() != l.getBuslinieHDTO().getNummer()) {
//					// Hin- und Rücklinien teilen sich die Nummer und die Abfolge, deshalb reicht es
//					// eine zu speichern.
//					buslinieDTOs.add(l.getBuslinieHDTO());					
//				}
//			}
//
//		}
//		return buslinieDTOs;
//	}
	
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
		}
		return buslinieDTOs;
	}
}
