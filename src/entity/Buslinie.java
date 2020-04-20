package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the buslinie database table.
 * 
 */
@Entity
@NamedQuery(name="Buslinie.findAll", query="SELECT b FROM Buslinie b")
public class Buslinie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="b_id")
	private int bId;

	private int nummer;

	private String richtung;

	//bi-directional many-to-one association to Fahrplan
	@OneToMany(mappedBy="buslinie")
	private List<Fahrplan> fahrplans;

	public Buslinie() {
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

	public List<Fahrplan> getFahrplans() {
		return this.fahrplans;
	}

	public void setFahrplans(List<Fahrplan> fahrplans) {
		this.fahrplans = fahrplans;
	}

	public Fahrplan addFahrplan(Fahrplan fahrplan) {
		getFahrplans().add(fahrplan);
		fahrplan.setBuslinie(this);

		return fahrplan;
	}

	public Fahrplan removeFahrplan(Fahrplan fahrplan) {
		getFahrplans().remove(fahrplan);
		fahrplan.setBuslinie(null);

		return fahrplan;
	}

}