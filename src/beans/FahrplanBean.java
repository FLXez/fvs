package beans;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.FahrplanDAO;
import dto.FahrplanDTO;
import entity.Fahrplan;

@Named("fahrplanBean")
@ApplicationScoped
public class FahrplanBean {

	@Inject
	FahrplanDAO fahrplanDAO;
	
	public List<FahrplanDTO> getAll() {
		List<Fahrplan> fahrplans = new ArrayList<Fahrplan>();
		List<FahrplanDTO> fahrplanDTOs = new ArrayList<FahrplanDTO>();
		fahrplans = fahrplanDAO.getAll();
		fahrplans.forEach((fahrplan) -> fahrplanDTOs.add(new FahrplanDTO(fahrplan)));
		return fahrplanDTOs;
	}
	
}