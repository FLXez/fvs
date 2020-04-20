package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the buslinien database table.
 * 
 */
@Entity
@NamedQuery(name="Buslinien.findAll", query="SELECT b FROM Buslinien b")
public class Buslinien implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="b_id")
	private int bId;

	private int nummer;

	private String richtung;

	//bi-directional many-to-one association to Fahrplan
	@OneToMany(mappedBy="buslinien")
	private List<Fahrplan> fahrplans;

	public Buslinien() {
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
		fahrplan.setBuslinien(this);

		return fahrplan;
	}

	public Fahrplan removeFahrplan(Fahrplan fahrplan) {
		getFahrplans().remove(fahrplan);
		fahrplan.setBuslinien(null);

		return fahrplan;
	}

}