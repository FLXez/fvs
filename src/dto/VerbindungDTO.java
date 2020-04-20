package dto;

import java.io.Serializable;

import entity.Verbindung;

public class VerbindungDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int vId;

	private int dauer;

	private HaltestelleDTO haltestelle_startDTO;
	
	private HaltestelleDTO haltestelle_endeDTO;

	public VerbindungDTO() {
	}

	public VerbindungDTO(Verbindung verbindungEntity) {
		
		this.vId = verbindungEntity.getVId();
		this.dauer = verbindungEntity.getDauer();
		this.haltestelle_startDTO = new HaltestelleDTO(verbindungEntity.getHaltestelle_start());
		this.haltestelle_endeDTO = new HaltestelleDTO(verbindungEntity.getHaltestelle_ende());
		
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

	public HaltestelleDTO getHaltestelle_endeDTO() {
		return this.haltestelle_endeDTO;
	}

	public void setHaltestelle_endeDTO(HaltestelleDTO haltestelle_endeDTO) {
		this.haltestelle_endeDTO = haltestelle_endeDTO;
	}

	public HaltestelleDTO getHaltestelle_startDTO() {
		return this.haltestelle_startDTO;
	}

	public void setHaltestelle_startDTO(HaltestelleDTO haltestelle_startDTO) {
		this.haltestelle_startDTO = haltestelle_startDTO;
	}

}
