package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the verbindungen database table.
 * 
 */
@Entity
@NamedQuery(name="Verbindungen.findAll", query="SELECT v FROM Verbindungen v")
public class Verbindungen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="v_id")
	private int vId;

	private int dauer;

	//uni-directional many-to-one association to Haltestellen
	@ManyToOne
	@JoinColumn(name="h_id_ende")
	private Haltestellen haltestellen_ende;

	//uni-directional many-to-one association to Haltestellen
	@ManyToOne
	@JoinColumn(name="h_id_start")
	private Haltestellen haltestellen_start;

	public Verbindungen() {
	}

	public int getVId() {
		return this.vId;
	}

	public void setVId(int vId) {
		this.vId = vId;
	}

	public int getDauer() {
		return this.dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public Haltestellen getHaltestellen_ende() {
		return this.haltestellen_ende;
	}

	public void setHaltestellen_ende(Haltestellen haltestellen_ende) {
		this.haltestellen_ende = haltestellen_ende;
	}

	public Haltestellen getHaltestellen_start() {
		return this.haltestellen_start;
	}

	public void setHaltestellen_start(Haltestellen haltestellen_start) {
		this.haltestellen_start = haltestellen_start;
	}

}