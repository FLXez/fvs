package dto;

import java.io.Serializable;

import entity.Verbindung;

/**
 * DTO für {@link Verbindung} = Viewmodel fürs Frontend
 *
 * @author Felix & Silas
 *
 */
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
	/**
	 * @return das DTO als Entity
	 */
	public Verbindung toEntity() {
		Verbindung verbindung = new Verbindung();
		
		verbindung.setVid(this.vid);
		verbindung.setDauer(this.dauer);
		verbindung.setHaltestelleS(this.haltestelleSDTO.toEntity());
		verbindung.setHaltestelleE(this.haltestelleEDTO.toEntity());
		
		return verbindung;
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
