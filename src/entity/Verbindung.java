package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * JPA für Tabelle verbindung
 * 
 * @author Felix & Silas
 * 
 */
@Entity
@NamedQuery(name="Verbindung.findAll", query="SELECT v FROM Verbindung v")
public class Verbindung implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int vid;

	private int dauer;

	//bi-directional many-to-one association to Linienabfolge
	@OneToMany(mappedBy="verbindung")
	private List<Linienabfolge> linienabfolgen;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="hidE")
	private Haltestelle haltestelleE;

	//uni-directional many-to-one association to Haltestelle
	@ManyToOne
	@JoinColumn(name="hidS")
	private Haltestelle haltestelleS;

	public Verbindung() {
	}

	public int getVid() {
		return this.vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getDauer() {
		return this.dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public List<Linienabfolge> getLinienabfolgen() {
		return this.linienabfolgen;
	}

	public void setLinienabfolgen(List<Linienabfolge> linienabfolgen) {
		this.linienabfolgen = linienabfolgen;
	}

	public Linienabfolge addLinienabfolgen(Linienabfolge linienabfolgen) {
		getLinienabfolgen().add(linienabfolgen);
		linienabfolgen.setVerbindung(this);

		return linienabfolgen;
	}

	public Linienabfolge removeLinienabfolgen(Linienabfolge linienabfolgen) {
		getLinienabfolgen().remove(linienabfolgen);
		linienabfolgen.setVerbindung(null);

		return linienabfolgen;
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