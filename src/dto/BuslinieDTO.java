package dto;

import java.io.Serializable;
import java.util.List;

import entity.Buslinie;

public class BuslinieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int bid;

	private int nummer;
	
	private String richtung;

	private List<LinienabfolgeDTO> LinienabfolgeDTOs;

	public BuslinieDTO() {
	}
	
	public BuslinieDTO(Buslinie buslinie) {
		this.bid = buslinie.getBid();
		this.nummer = buslinie.getNummer();
		this.richtung = buslinie.getRichtung();
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getNummer() {
		return nummer;
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

	public List<LinienabfolgeDTO> getLinienabfolgeDTOs() {
		return LinienabfolgeDTOs;
	}

	public void setLinienabfolgeDTOs(List<LinienabfolgeDTO> linienabfolgeDTOs) {
		LinienabfolgeDTOs = linienabfolgeDTOs;
	}


}
