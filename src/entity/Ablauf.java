package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ablauf database table.
 * 
 */
@Entity
@NamedQuery(name="Ablauf.findAll", query="SELECT a FROM Ablauf a")
public class Ablauf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="a_id")
	private int aId;

	private int position;

	//bi-directional many-to-one association to Fahrplan
	@ManyToOne
	@JoinColumn(name="f_id")
	private Fahrplan fahrplan;

	//uni-directional many-to-one association to Verbindung
	@ManyToOne
	@JoinColumn(name="v_id")
	private Verbindung verbindung;

	public Ablauf() {
	}

	public int getAId() {
		return this.aId;
	}

	public void setAId(int aId) {
		this.aId = aId;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Fahrplan getFahrplan() {
		return this.fahrplan;
	}

	public void setFahrplan(Fahrplan fahrplan) {
		this.fahrplan = fahrplan;
	}

	public Verbindung getVerbindung() {
		return this.verbindung;
	}

	public void setVerbindung(Verbindung verbindung) {
		this.verbindung = verbindung;
	}

}