package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.BuslinieDAO;
import dto.BuslinieDTO;
import entity.Buslinie;

@Named("buslinieBean")
@ApplicationScoped
@ManagedBean
public class BuslinieBean {

	@Inject
	BuslinieDAO buslinieDAO;
	
	public List<BuslinieDTO> getAll() {
		List<Buslinie> busliniens = new ArrayList<Buslinie>();
		List<BuslinieDTO> buslinieDTOs = new ArrayList<BuslinieDTO>();
		busliniens = buslinieDAO.getAll();
		busliniens.forEach((buslinie) -> buslinieDTOs.add(new BuslinieDTO(buslinie)));
		return buslinieDTOs;
	}
	
}