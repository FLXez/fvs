package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * JPA für Tabelle buslinie
 * 
 * @author Felix & Silas
 * 
 */
@Entity
@NamedQuery(name="Buslinie.findAll", query="SELECT b FROM Buslinie b")
public class Buslinie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int bid;

	private int nummer;

	private String richtung;

	//bi-directional many-to-one association to Fahrt
	@OneToMany(mappedBy="buslinie")
	private List<Fahrt> fahrten;

	public Buslinie() {
	}

	public int getBid() {
		return this.bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
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

	public List<Fahrt> getFahrten() {
		return this.fahrten;
	}

	public void setFahrten(List<Fahrt> fahrten) {
		this.fahrten = fahrten;
	}

	public Fahrt addFahrten(Fahrt fahrten) {
		getFahrten().add(fahrten);
		fahrten.setBuslinie(this);

		return fahrten;
	}

	public Fahrt removeFahrten(Fahrt fahrten) {
		getFahrten().remove(fahrten);
		fahrten.setBuslinie(null);

		return fahrten;
	}

}