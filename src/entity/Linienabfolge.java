package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the linienabfolge database table.
 * 
 */
@Entity
@NamedQuery(name="Linienabfolge.findAll", query="SELECT l FROM Linienabfolge l")
public class Linienabfolge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int lid;

	private int position;

	//uni-directional many-to-one association to Buslinie
	@ManyToOne
	@JoinColumn(name="bidh")
	private Buslinie buslinieH;

	//uni-directional many-to-one association to Buslinie
	@ManyToOne
	@JoinColumn(name="bidr")
	private Buslinie buslinieR;

	//bi-directional many-to-one association to Verbindung
	@ManyToOne
	@JoinColumn(name="vid")
	private Verbindung verbindung;

	public Linienabfolge() {
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Buslinie getBuslinieH() {
		return this.buslinieH;
	}

	public void setBuslinieH(Buslinie buslinieH) {
		this.buslinieH = buslinieH;
	}

	public Buslinie getBuslinieR() {
		return this.buslinieR;
	}

	public void setBuslinieR(Buslinie buslinieR) {
		this.buslinieR = buslinieR;
	}

	public Verbindung getVerbindung() {
		return this.verbindung;
	}

	public void setVerbindung(Verbindung verbindung) {
		this.verbindung = verbindung;
	}

}