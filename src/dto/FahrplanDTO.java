package dto;

public class FahrplanDTO {
	
	BuslinieDTO buslinieDTO;
	
	HaltestelleDTO zielhaltestelleDTO;
	
	String uhrzeit;
	
	String verspaetung;
	
	String uhrzeitSort;
	
	
	public FahrplanDTO() {
		
	}
	
	public FahrplanDTO(BuslinieDTO b, HaltestelleDTO h, String u, String v, String us) {
		this.buslinieDTO = b;
		this.zielhaltestelleDTO = h;
		this.uhrzeit = u;
		this.verspaetung = v;
		this.uhrzeitSort = us;
	}

	public BuslinieDTO getBuslinieDTO() {
		return buslinieDTO;
	}

	public void setBuslinieDTO(BuslinieDTO buslinieDTO) {
		this.buslinieDTO = buslinieDTO;
	}

	public HaltestelleDTO getZielhaltestelleDTO() {
		return zielhaltestelleDTO;
	}

	public void setZielhaltestelleDTO(HaltestelleDTO zielhaltestelleDTO) {
		this.zielhaltestelleDTO = zielhaltestelleDTO;
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

	public String getUhrzeitSort() {
		return uhrzeitSort;
	}

	public void setUhrzeitSort(String uhrzeitSort) {
		this.uhrzeitSort = uhrzeitSort;
	}
}
