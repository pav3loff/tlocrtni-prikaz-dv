package hr.fer.pavlic.dipl.utmwgstransf;

public class WgsCoordinate {
	
	private double geoSirina;
	private double geoDuzina;
	
	public WgsCoordinate(double geoSirina, double geoDuzina) {
		super();
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
	}
	
	public double getGeoSirina() {
		return geoSirina;
	}
	
	public void setGeoSirina(double geoSirina) {
		this.geoSirina = geoSirina;
	}
	
	public double getGeoDuzina() {
		return geoDuzina;
	}
	
	public void setGeoDuzina(double geoDuzina) {
		this.geoDuzina = geoDuzina;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof WgsCoordinate)) {
			return false;
		}
		
		WgsCoordinate other = (WgsCoordinate) obj;
		
		return this.geoSirina == other.geoSirina && this.geoDuzina == other.geoDuzina;
	}
	
	@Override
	public String toString() {
		return geoSirina + " " + geoDuzina;
	}

}
