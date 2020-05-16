package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the fahrt database table.
 * 
 */
@Entity
@NamedQuery(name="Fahrt.findAll", query="SELECT f FROM Fahrt f")
public class Fahrt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int fid;

	private Time uhrzeit;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="hidE")
	private Haltestelle haltestelleE;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="hidS")
	private Haltestelle haltestelleS;

	//uni-directional many-to-one association to Buslinie
	@ManyToOne
	@JoinColumn(name="bid")
	private Buslinie buslinie;
	
	public Fahrt() {
	}

	public int getFid() {
		return this.fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public Time getUhrzeit() {
		return this.uhrzeit;
	}

	public void setUhrzeit(Time uhrzeit) {
		this.uhrzeit = uhrzeit;
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
	
	public Buslinie getBuslinie() {
		return this.buslinie;
	}

	public void setBuslinie(Buslinie buslinie) {
		this.buslinie = buslinie;
	}

}