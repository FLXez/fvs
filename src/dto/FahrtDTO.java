package dto;

import java.io.Serializable;

import entity.Fahrt;
/**
 * DTO für {@link Fahrt} = Viewmodel fürs Frontend
 *
 * @author Felix & Silas
 *
 */
public class FahrtDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int fid;

	private String uhrzeit;

	private HaltestelleDTO haltestelleEDTO;

	private HaltestelleDTO haltestelleSDTO;
	
	private BuslinieDTO buslinieDTO;

	public FahrtDTO() {
	}
	
	public FahrtDTO(Fahrt fahrtEntity) {
		this.fid = fahrtEntity.getFid();
		this.uhrzeit = fahrtEntity.getUhrzeit();
		this.haltestelleEDTO = new HaltestelleDTO(fahrtEntity.getHaltestelleE());
		this.haltestelleSDTO = new HaltestelleDTO(fahrtEntity.getHaltestelleS());
		this.setBuslinieDTO(new BuslinieDTO(fahrtEntity.getBuslinie()));
	}
	/**
	 * @return {@link FahrtDTO} als {@link Fahrt}
	 */
	public Fahrt toEntity() {
		Fahrt fahrt = new Fahrt();
		
		fahrt.setFid(this.fid);
		fahrt.setBuslinie(this.buslinieDTO.toEntity());
		fahrt.setHaltestelleS(this.haltestelleSDTO.toEntity());
		fahrt.setHaltestelleE(this.haltestelleEDTO.toEntity());
		fahrt.setUhrzeit(this.uhrzeit);
		
		return fahrt;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public HaltestelleDTO getHaltestelleEDTO() {
		return haltestelleEDTO;
	}

	public void setHaltestelleEDTO(HaltestelleDTO haltestelleEDTO) {
		this.haltestelleEDTO = haltestelleEDTO;
	}

	public HaltestelleDTO getHaltestelleSDTO() {
		return haltestelleSDTO;
	}

	public void setHaltestelleSDTO(HaltestelleDTO haltestelleSDTO) {
		this.haltestelleSDTO = haltestelleSDTO;
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}


}
