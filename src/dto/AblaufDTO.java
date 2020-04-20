package dto;

import java.io.Serializable;

import entity.Ablauf;

public class AblaufDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int aId;

	private int position;

	private FahrplanDTO fahrplanDTO;

	private VerbindungDTO verbindungDTO;

	public AblaufDTO() {
	}
	
	public AblaufDTO(Ablauf ablaufEntity) {
		
		this.aId = ablaufEntity.getAId();
		this.position = ablaufEntity.getPosition();
		this.fahrplanDTO = new FahrplanDTO(ablaufEntity.getFahrplan());
		this.verbindungDTO = new VerbindungDTO(ablaufEntity.getVerbindung());
	}

	public int getAId() {
		return this.aId;
	}

	public void setAId(int aId) {
		this.aId = aId;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public FahrplanDTO getFahrplanDTO() {
		return this.fahrplanDTO;
	}

	public void setFahrplanDTO(FahrplanDTO fahrplanDTO) {
		this.fahrplanDTO = fahrplanDTO;
	}

	public VerbindungDTO getVerbindungDTO() {
		return this.verbindungDTO;
	}

	public void setVerbindungDTO(VerbindungDTO verbindungDTO) {
		this.verbindungDTO = verbindungDTO;
	}
	
}
