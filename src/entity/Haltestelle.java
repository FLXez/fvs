package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the haltestellen database table.
 * 
 */
@Entity
@NamedQuery(name="Haltestellen.findAll", query="SELECT h FROM Haltestellen h")
public class Haltestellen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="h_id")
	private int hId;

	private String bezeichnung;

	private double latitude;

	private double longitude;

	public Haltestellen() {
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