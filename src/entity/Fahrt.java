package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * JPA für Tabelle fahrt
 * 
 * @author Felix & Silas
 * 
 */
@Entity
@NamedQuery(name="Fahrt.findAll", query="SELECT f FROM Fahrt f")
public class Fahrt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int fid;

	private String uhrzeit;

	//bi-directional many-to-one association to Buslinie
	@ManyToOne
	@JoinColumn(name="bid")
	private Buslinie buslinie;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="hidE")
	private Haltestelle haltestelleE;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="hidS")
	private Haltestelle haltestelleS;

	public Fahrt() {
	}

	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getUhrzeit() {
		return this.uhrzeit;
	}

	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public Buslinie getBuslinie() {
		return this.buslinie;
	}

	public void setBuslinie(Buslinie buslinie) {
		this.buslinie = buslinie;
	}

	public Haltestelle getHaltestelleE() {
		return this.haltestelleE;
	}

	public void setHaltestelleE(Haltestelle haltestelleE) {
		this.haltestelleE = haltestelleE;
	}

	public Haltestelle getHaltestelleS() {
		return this.haltestelleS;
	}

	public void setHaltestelleS(Haltestelle haltestelleS) {
		this.haltestelleS = haltestelleS;
	}

}