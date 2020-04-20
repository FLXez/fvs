package dto;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

import entity.Fahrplan;

public class FahrplanDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int fId;

	private Time uhrzeit;

	private String wochentag;
	
	private List<AblaufDTO> ablaufDTOs;

	private BuslinieDTO buslinieDTO;

	public FahrplanDTO() {
	}
	
	public FahrplanDTO(Fahrplan fahrplanEntity) {
		this.fId = fahrplanEntity.getFId();
		this.uhrzeit = fahrplanEntity.getUhrzeit();
		this.wochentag = fahrplanEntity.getWochentag();
		// Bi-Direktionales Mapping ist real Struggle
		// this.ablaufDTOs = new ArrayList<AblaufDTO>();
		// fahrplanEntity.getAblaufs().forEach((ablauf) -> ablaufDTOs.add(new AblaufDTO(ablauf)));
		this.buslinieDTO = new BuslinieDTO(fahrplanEntity.getBuslinie());
	}

	public int getFId() {
		return this.fId;
	}

	public void setFId(int fId) {
		this.fId = fId;
	}

	public Time getUhrzeit() {
		return this.uhrzeit;
	}

	public void setUhrzeit(Time uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public String getWochentag() {
		return this.wochentag;
	}

	public void setWochentag(String wochentag) {
		this.wochentag = wochentag;
	}

	public List<AblaufDTO> getAblaufDTOs() {
		return this.ablaufDTOs;
	}

	public void setAblaufDTOs(List<AblaufDTO> ablaufDTOs) {
		this.ablaufDTOs = ablaufDTOs;
	}

	public AblaufDTO addAblaufDTO(AblaufDTO ablaufDTO) {
		getAblaufDTOs().add(ablaufDTO);
		ablaufDTO.setFahrplanDTO(this);

		return ablaufDTO;
	}

	public AblaufDTO removeAblauf(AblaufDTO ablaufDTO) {
		getAblaufDTOs().remove(ablaufDTO);
		ablaufDTO.setFahrplanDTO(null);

		return ablaufDTO;
	}

	public BuslinieDTO getBuslinieDTO() {
		return this.buslinieDTO;
	}

	public void setBuslinie(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

}
