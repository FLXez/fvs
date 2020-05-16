package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the haltestelle database table.
 * 
 */
@Entity
@NamedQuery(name="Haltestelle.findAll", query="SELECT h FROM Haltestelle h")
public class Haltestelle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int hid;

	private String bezeichnung;

	public Haltestelle() {
	}

	public int getHid() {
		return this.hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

}