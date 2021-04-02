package hr.fer.pavlic.dipl.model;

public class RasponVodicaIzmeduDvaIzolatora {
	
	private Izolator pocIzolator;
	private Izolator krajIzolator;
	
	public RasponVodicaIzmeduDvaIzolatora(Izolator pocIzolator, Izolator krajIzolator) {
		super();
		this.pocIzolator = pocIzolator;
		this.krajIzolator = krajIzolator;
	}

	public Izolator getPocIzolator() {
		return pocIzolator;
	}

	public void setPocIzolator(Izolator pocIzolator) {
		this.pocIzolator = pocIzolator;
	}

	public Izolator getKrajIzolator() {
		return krajIzolator;
	}

	public void setKrajIzolator(Izolator krajIzolator) {
		this.krajIzolator = krajIzolator;
	}

}
