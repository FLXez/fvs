package dto;

public class FahrplanDTO {
	
	BuslinieDTO buslinieDTO;
	
	HaltestelleDTO zielHaltestelleDTO;
	
	String uhrzeit;
	
	String verspaetung;
	
	
	public FahrplanDTO() {
		
	}
	
	public FahrplanDTO(BuslinieDTO b, HaltestelleDTO h, String u, String v) {
		this.buslinieDTO = b;
		this.zielHaltestelleDTO = h;
		this.uhrzeit = u;
		this.verspaetung = v;
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public HaltestelleDTO getZielHaltestelleDTO() {
		return zielHaltestelleDTO;
	}

	public void setZielHaltestelleDTO(HaltestelleDTO zielHaltestelleDTO) {
		this.zielHaltestelleDTO = zielHaltestelleDTO;
	}

	public String getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(String uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public String getVerspaetung() {
		return verspaetung;
	}

	public void setVerspaetung(String verspaetung) {
		this.verspaetung = verspaetung;
	}
	
	

}
