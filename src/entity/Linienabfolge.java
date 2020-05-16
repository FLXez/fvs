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

	//bi-directional many-to-one association to Buslinie
	@ManyToOne
	@JoinColumn(name="bid")
	private Buslinie buslinie;

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

	public Buslinie getBuslinie() {
		return this.buslinie;
	}

	public void setBuslinie(Buslinie buslinie) {
		this.buslinie = buslinie;
	}

	public Verbindung getVerbindung() {
		return this.verbindung;
	}

	public void setVerbindung(Verbindung verbindung) {
		this.verbindung = verbindung;
	}

}