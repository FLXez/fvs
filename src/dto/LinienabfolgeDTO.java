package dto;

import java.io.Serializable;

import entity.Linienabfolge;;

public class LinienabfolgeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int lid;

	private int position;

	private BuslinieDTO buslinieDTO;

	private VerbindungDTO verbindungDTO;

	public LinienabfolgeDTO() {
	}
	
	public LinienabfolgeDTO(Linienabfolge linienabfolgeEntity) {
		
		this.lid = linienabfolgeEntity.getLid();
		this.position = linienabfolgeEntity.getPosition();
		this.buslinieDTO = new BuslinieDTO(linienabfolgeEntity.getBuslinie());
		this.verbindungDTO = new VerbindungDTO(linienabfolgeEntity.getVerbindung());
	}

	public int getLid() {
		return lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public VerbindungDTO getVerbindungDTO() {
		return verbindungDTO;
	}

	public void setVerbindungDTO(VerbindungDTO verbindungDTO) {
		this.verbindungDTO = verbindungDTO;
	}

}
