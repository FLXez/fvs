package dto;

import java.io.Serializable;

import entity.Linienabfolge;;

/**
 * DTO für {@link Linienabfolge} = Viewmodel fürs Frontend
 *
 * @author Felix & Silas
 *
 */
public class LinienabfolgeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int lid;

	private int position;

	private BuslinieDTO buslinieHDTO;
	
	private BuslinieDTO buslinieRDTO;

	private VerbindungDTO verbindungDTO;

	public LinienabfolgeDTO() {
	}
	
	public LinienabfolgeDTO(Linienabfolge linienabfolgeEntity) {
		
		this.lid = linienabfolgeEntity.getLid();
		this.position = linienabfolgeEntity.getPosition();
		this.buslinieHDTO = new BuslinieDTO(linienabfolgeEntity.getBuslinieH());
		this.buslinieRDTO = new BuslinieDTO(linienabfolgeEntity.getBuslinieR());
		this.verbindungDTO = new VerbindungDTO(linienabfolgeEntity.getVerbindung());
	}
	/**
	 * @return das DTO als Entity
	 */
	public Linienabfolge toEntity() {
		Linienabfolge linienabfolge = new Linienabfolge();
		
		linienabfolge.setLid(this.lid);
		linienabfolge.setPosition(this.position);
		linienabfolge.setBuslinieH(this.buslinieHDTO.toEntity());
		linienabfolge.setBuslinieR(this.buslinieRDTO.toEntity());
		linienabfolge.setVerbindung(this.verbindungDTO.toEntity());
		
		return linienabfolge;
		
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

	public BuslinieDTO getBuslinieHDTO() {
		return buslinieHDTO;
	}

	public void setBuslinieHDTO(BuslinieDTO buslinieHDTO) {
		this.buslinieHDTO = buslinieHDTO;
	}

	public BuslinieDTO getBuslinieRDTO() {
		return buslinieRDTO;
	}

	public void setBuslinieRDTO(BuslinieDTO buslinieRDTO) {
		this.buslinieRDTO = buslinieRDTO;
	}
	
	public VerbindungDTO getVerbindungDTO() {
		return verbindungDTO;
	}

	public void setVerbindungDTO(VerbindungDTO verbindungDTO) {
		this.verbindungDTO = verbindungDTO;
	}

}
