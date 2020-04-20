package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.AblaufDAO;
import dto.AblaufDTO;
import entity.Ablauf;

@Named("ablaufBean")
@ApplicationScoped
@ManagedBean
public class AblaufBean {

	@Inject
	AblaufDAO ablaufDAO;	
	
	public List<AblaufDTO> getAllAblaufs() { 
		// Listen aufbauen, um diese durchgehen zu können
		List<Ablauf> ablaufs = new ArrayList<Ablauf>();
		List<AblaufDTO> ablaufDTOs = new ArrayList<AblaufDTO>();
		ablaufs = ablaufDAO.getAll();
		// Verbindung Entity wird zu Verbindung DTO
		ablaufs.forEach((ablauf) -> ablaufDTOs.add(new AblaufDTO(ablauf)));
		
		return ablaufDTOs;
	}
	
}