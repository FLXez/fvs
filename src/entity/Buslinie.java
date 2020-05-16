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
	private int bid;

	private int nummer;
	
	private String richtung;

	//bi-directional many-to-one association to Linienabfolge
	@OneToMany(mappedBy="buslinie")
	private List<Linienabfolge> linienabfolgen;
	
	public Buslinie() {
	}

	public int getBid() {
		return this.bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
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
	
	public List<Linienabfolge> getLinienabfolgen() {
		return this.linienabfolgen;
	}

	public void setLinienabfolgen(List<Linienabfolge> linienabfolgen) {
		this.linienabfolgen = linienabfolgen;
	}

	public Linienabfolge addLinienabfolgen(Linienabfolge linienabfolgen) {
		getLinienabfolgen().add(linienabfolgen);
		linienabfolgen.setBuslinie(this);

		return linienabfolgen;
	}

	public Linienabfolge removeLinienabfolgen(Linienabfolge linienabfolgen) {
		getLinienabfolgen().remove(linienabfolgen);
		linienabfolgen.setBuslinie(null);

		return linienabfolgen;
	}

}