package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.VerbindungDAO;
import dto.VerbindungDTO;
import entity.Verbindung;

@Named("verbindungBean")
@ApplicationScoped
@ManagedBean
public class VerbindungBean {

	@Inject
	VerbindungDAO verbindungDAO;
	
	public List<VerbindungDTO> getAllVerbindungs() {
		// Listen aufbauen, um diese durchgehen zu können
		List<Verbindung> verbindungs = new ArrayList<Verbindung>();
		List<VerbindungDTO> verbindungDTOs = new ArrayList<VerbindungDTO>();
		verbindungs = verbindungDAO.getAll();
		// Verbindung Entity wird zu Verbindung DTO
		verbindungs.forEach((verbindung) -> verbindungDTOs.add(new VerbindungDTO(verbindung)));
		
		return verbindungDTOs;
	}
	
	public VerbindungDTO getVerbindungByID(int id) {
		VerbindungDTO verbindungDTO;
		Optional<Verbindung> verbindung = verbindungDAO.get(id);
		if(verbindung.isPresent()) {
			verbindungDTO = new VerbindungDTO(verbindung.get());
			
			return verbindungDTO;
		} else {
			System.out.println("Keine Verbindung mit der ID " + id + " gefunden.");
			return null;
		}
	}
	
}