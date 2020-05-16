package beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.LinienabfolgeDAO;
import dto.LinienabfolgeDTO;
import entity.Linienabfolge;

@Named("ablaufBean")
@ApplicationScoped
public class LinienabfolgeBean {

	@Inject
	LinienabfolgeDAO linienabfolgeDAO;	
	
	public List<LinienabfolgeDTO> getAllLinienabfolgen() { 
		// Listen aufbauen, um diese durchgehen zu können
		List<Linienabfolge> linienabfolgen = new ArrayList<Linienabfolge>();
		List<LinienabfolgeDTO> linienabfolgeDTOs = new ArrayList<LinienabfolgeDTO>();
		linienabfolgen = linienabfolgeDAO.getAll();
		// Verbindung Entity wird zu Verbindung DTO
		linienabfolgen.forEach((linienabfolge) -> linienabfolgeDTOs.add(new LinienabfolgeDTO(linienabfolge)));
		
		return linienabfolgeDTOs;
	}
	
}