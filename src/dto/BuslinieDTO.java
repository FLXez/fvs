package dto;

import java.io.Serializable;
import java.util.List;

import entity.Buslinie;

/**
 * DTO f�r {@link Buslinie} = Viewmodel f�rs Frontend
 *
 * @author Felix & Silas
 *
 */
public class BuslinieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int bid;

	private int nummer;
	
	private String richtung;
	
	// Feld f�r ausgeschriebene Richtungsangabe f�rs Frontend
	private String richtungText;

	private List<LinienabfolgeDTO> LinienabfolgeDTOs;

	public BuslinieDTO() {
	}
	/**
	 * @return {@link Buslinie} als {@link BuslinieDTO}
	 */
	public BuslinieDTO(Buslinie buslinie) {
		this.bid = buslinie.getBid();
		this.nummer = buslinie.getNummer();
		this.richtung = buslinie.getRichtung();
		if(this.richtung.equals("H")) {
			this.setRichtungText("Hinlinie");			
		} else {
			this.setRichtungText("R�cklinie");
		}
	}
	
	public Buslinie toEntity() {
		Buslinie buslinie = new Buslinie();
		
		buslinie.setBid(this.bid);
		buslinie.setNummer(this.nummer);
		buslinie.setRichtung(this.richtung);
		
		return buslinie;
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

	public String getRichtungText() {
		return richtungText;
	}

	public void setRichtungText(String richtungText) {
		this.richtungText = richtungText;
	}


}
