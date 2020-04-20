package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the verbindung database table.
 * 
 */
@Entity
@NamedQuery(name="Verbindung.findAll", query="SELECT v FROM Verbindung v")
public class Verbindung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="v_id")
	private int vId;

	private int dauer;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="h_id_ende")
	private Haltestelle haltestelle_ende;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="h_id_start")
	private Haltestelle haltestelle_start;

	public Verbindung() {
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

	public Haltestelle getHaltestelle_ende() {
		return this.haltestelle_ende;
	}

	public void setHaltestelle_ende(Haltestelle haltestelle_ende) {
		this.haltestelle_ende = haltestelle_ende;
	}

	public Haltestelle getHaltestelle_start() {
		return this.haltestelle_start;
	}

	public void setHaltestelle_start(Haltestelle haltestelle_start) {
		this.haltestelle_start = haltestelle_start;
	}

}