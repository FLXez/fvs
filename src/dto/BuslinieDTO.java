package dto;

import java.io.Serializable;
import java.util.List;

import entity.Buslinie;

public class BuslinieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int bId;

	private int nummer;

	private String richtung;

	private List<FahrplanDTO> fahrplanDTOs;

	public BuslinieDTO() {
	}
	
	public BuslinieDTO(Buslinie BuslinieEntity) {
		this.bId = BuslinieEntity.getBId();
		this.nummer = BuslinieEntity.getNummer();
		this.richtung = BuslinieEntity.getRichtung();
		// Bi-Direktionales Mapping ist real Struggle
		// this.fahrplanDTOs = new ArrayList<FahrplanDTO>();
		// BuslinieEntity.getFahrplans().forEach((fahrplan) -> fahrplanDTOs.add(new FahrplanDTO(fahrplan)));
	}

	public int getBId() {
		return this.bId;
	}

	public void setBId(int bId) {
		this.bId = bId;
	}

	public int getNummer() {
		return this.nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public String getRichtung() {
		return this.richtung;
	}

	public void setRichtung(String richtung) {
		this.richtung = richtung;
	}

	public List<FahrplanDTO> getFahrplanDTOs() {
		return this.fahrplanDTOs;
	}

	public void setFahrplans(List<FahrplanDTO> fahrplanDTOs) {
		this.fahrplanDTOs = fahrplanDTOs;
	}

	public FahrplanDTO addFahrplanDTO(FahrplanDTO fahrplanDTO) {
		getFahrplanDTOs().add(fahrplanDTO);
		fahrplanDTO.setBuslinie(this);

		return fahrplanDTO;
	}

	public FahrplanDTO removeFahrplan(FahrplanDTO fahrplanDTO) {
		getFahrplanDTOs().remove(fahrplanDTO);
		fahrplanDTO.setBuslinie(null);

		return fahrplanDTO;
	}

}
