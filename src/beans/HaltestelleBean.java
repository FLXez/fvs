package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.HaltestelleDAO;
import dto.HaltestelleDTO;
import entity.Haltestelle;
import util.NotificationUtils;

@Named("haltestelleBean")
@ApplicationScoped
public class HaltestelleBean {

	@Inject
	HaltestelleDAO haltestelleDAO;

	HaltestelleDTO newHaltestelleDTO;

	@PostConstruct
	public void init() {
		this.newHaltestelleDTO = new HaltestelleDTO();
	}

	public void setNewHaltestelleDTO(HaltestelleDTO newHaltestelleDTO) {
		this.newHaltestelleDTO = newHaltestelleDTO;
	}

	public HaltestelleDTO getNewHaltestelleDTO() {
		return this.newHaltestelleDTO;
	}

	public List<HaltestelleDTO> getAllHaltestellen() {
		List<Haltestelle> haltestelles = new ArrayList<Haltestelle>();
		List<HaltestelleDTO> haltestelleDTOs = new ArrayList<HaltestelleDTO>();
		haltestelles = haltestelleDAO.getAll();
		haltestelles.forEach((haltestelle) -> haltestelleDTOs.add(new HaltestelleDTO(haltestelle)));

		return haltestelleDTOs;
	}

	public HaltestelleDTO getHaltestelleByID(int id) {
		HaltestelleDTO haltestelleDTO;
		Haltestelle haltestelle = haltestelleDAO.get(id);
			haltestelleDTO = new HaltestelleDTO(haltestelle);

			return haltestelleDTO;
	}

	public void add() {

		if (newHaltestelleDTO.getBezeichnung().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "addStop:description", "Bezeichnung leer",
					"Bitte vergeben Sie eine Bezeichnung.");
			return;
		}

		if (!haltestelleDAO.findByBezeichnung(newHaltestelleDTO.getBezeichnung())) {

			Haltestelle haltestelle = new Haltestelle();
			haltestelle.setBezeichnung(this.newHaltestelleDTO.getBezeichnung());

			try {
				haltestelleDAO.save(haltestelle);
				newHaltestelleDTO = new HaltestelleDTO();
				NotificationUtils.showMessage(false, 0, "addStop:description", "Haltestelle hinzugefügt",
						"Die Haltestelle wurde erfolgreich hinzugefügt.");
			} catch (EJBException e) {
				NotificationUtils.showMessage(false, 2, "addStop:description", "Unerwarteter Fehler",
						"Es ist ein unerwarteter Fehler aufgetreten.");
			}
		} else {
			NotificationUtils.showMessage(false, 1, "addStop:description", "Haltestellenbezeichnung bereits vergeben",
					"Eine Haltestelle mit dieser Bezeichnung existiert bereits.");
		}
	}
}
