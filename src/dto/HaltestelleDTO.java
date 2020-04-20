package dto;

import java.io.Serializable;

import entity.Haltestelle;

public class HaltestelleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int hId;

	private String bezeichnung;

	private double latitude;

	private double longitude;

	public HaltestelleDTO() {
	}
	
	public HaltestelleDTO(Haltestelle haltestelleEntity) {
		
		this.hId = haltestelleEntity.getHId();
		this.bezeichnung = haltestelleEntity.getBezeichnung();
		this.latitude = haltestelleEntity.getLatitude();
		this.longitude = haltestelleEntity.getLongitude();
		
	}
	
	public int getHId() {
		return this.hId;
	}

	public void setHId(int hId) {
		this.hId = hId;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
