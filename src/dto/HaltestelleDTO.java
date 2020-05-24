package dto;

import java.io.Serializable;

import entity.Haltestelle;
/**
 * DTO für {@link Haltestelle} = Viewmodel fürs Frontend
 *
 * @author Felix & Silas
 *
 */
public class HaltestelleDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int hid;

	private String bezeichnung;

	
	public HaltestelleDTO() {
	}
	
	public HaltestelleDTO(Haltestelle haltestelleEntity) {
		
		this.hid = haltestelleEntity.getHid();
		this.bezeichnung = haltestelleEntity.getBezeichnung();
		
	}
	/**
	 * @return das DTO als Entity
	 */
	public Haltestelle toEntity() {
		Haltestelle haltestelle = new Haltestelle();
		
		haltestelle.setHid(this.hid);
		haltestelle.setBezeichnung(this.bezeichnung);
		
		return haltestelle;
	}
	
	public int getHid() {
		return this.hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
}
