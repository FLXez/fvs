package dto;

import java.io.Serializable;

import entity.Verbindung;

public class VerbindungDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int vid;

	private int dauer;

	private HaltestelleDTO haltestelleEDTO;

	private HaltestelleDTO haltestelleSDTO;

	public VerbindungDTO() {
		this.haltestelleEDTO = new HaltestelleDTO();
		this.haltestelleSDTO = new HaltestelleDTO();
	}

	public VerbindungDTO(Verbindung verbindungEntity) {

		this.vid = verbindungEntity.getVid();
		this.dauer = verbindungEntity.getDauer();
		this.haltestelleEDTO = new HaltestelleDTO(verbindungEntity.getHaltestelleE());
		this.haltestelleSDTO = new HaltestelleDTO(verbindungEntity.getHaltestelleS());

	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getDauer() {
		return dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
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

}
