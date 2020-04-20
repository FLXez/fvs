package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.List;


/**
 * The persistent class for the fahrplan database table.
 * 
 */
@Entity
@NamedQuery(name="Fahrplan.findAll", query="SELECT f FROM Fahrplan f")
public class Fahrplan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="f_id")
	private int fId;

	private Time uhrzeit;

	private String wochentag;

	//bi-directional many-to-one association to Ablauf
	@OneToMany(mappedBy="fahrplan")
	private List<Ablauf> ablaufs;

	//bi-directional many-to-one association to Buslinien
	@ManyToOne
	@JoinColumn(name="b_id")
	private Buslinien buslinien;

	public Fahrplan() {
	}

	public int getFId() {
		return this.fId;
	}

	public void setFId(int fId) {
		this.fId = fId;
	}

	public Time getUhrzeit() {
		return this.uhrzeit;
	}

	public void setUhrzeit(Time uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public String getWochentag() {
		return this.wochentag;
	}

	public void setWochentag(String wochentag) {
		this.wochentag = wochentag;
	}

	public List<Ablauf> getAblaufs() {
		return this.ablaufs;
	}

	public void setAblaufs(List<Ablauf> ablaufs) {
		this.ablaufs = ablaufs;
	}

	public Ablauf addAblauf(Ablauf ablauf) {
		getAblaufs().add(ablauf);
		ablauf.setFahrplan(this);

		return ablauf;
	}

	public Ablauf removeAblauf(Ablauf ablauf) {
		getAblaufs().remove(ablauf);
		ablauf.setFahrplan(null);

		return ablauf;
	}

	public Buslinien getBuslinien() {
		return this.buslinien;
	}

	public void setBuslinien(Buslinien buslinien) {
		this.buslinien = buslinien;
	}

}