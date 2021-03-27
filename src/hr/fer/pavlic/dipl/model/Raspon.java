package hr.fer.pavlic.dipl.model;

public class Raspon {

	private double pocGeoSirina;
	private double pocGeoDuzina;
	private double krajGeoSirina;
	private double krajGeoDuzina;
	
	public Raspon(double pocGeoSirina, double pocGeoDuzina, double krajGeoSirina, double krajGeoDuzina) {
		super();
		this.pocGeoSirina = pocGeoSirina;
		this.pocGeoDuzina = pocGeoDuzina;
		this.krajGeoSirina = krajGeoSirina;
		this.krajGeoDuzina = krajGeoDuzina;
	}
	
	public double getPocGeoSirina() {
		return pocGeoSirina;
	}
	
	public void setPocGeoSirina(double pocGeoSirina) {
		this.pocGeoSirina = pocGeoSirina;
	}
	
	public double getPocGeoDuzina() {
		return pocGeoDuzina;
	}
	
	public void setPocGeoDuzina(double pocGeoDuzina) {
		this.pocGeoDuzina = pocGeoDuzina;
	}
	
	public double getKrajGeoSirina() {
		return krajGeoSirina;
	}
	
	public void setKrajGeoSirina(double krajGeoSirina) {
		this.krajGeoSirina = krajGeoSirina;
	}
	
	public double getKrajGeoDuzina() {
		return krajGeoDuzina;
	}
	
	public void setKrajGeoDuzina(double krajGeoDuzina) {
		this.krajGeoDuzina = krajGeoDuzina;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Raspon)) {
			return false;
		}
		
		Raspon other = (Raspon) obj;
		
		return this.pocGeoSirina == other.pocGeoSirina && this.pocGeoDuzina == other.pocGeoDuzina &&
				this.krajGeoSirina == other.krajGeoSirina && this.krajGeoDuzina == other.krajGeoDuzina;
	}
	
}
