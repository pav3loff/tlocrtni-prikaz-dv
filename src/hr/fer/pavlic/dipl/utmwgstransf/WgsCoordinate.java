package hr.fer.pavlic.dipl.utmwgstransf;

import org.json.JSONArray;

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
	
	public JSONArray getJson() {
		JSONArray wgsCoordinateJson = new JSONArray();
		
		wgsCoordinateJson.put(this.geoSirina);
		wgsCoordinateJson.put(this.geoDuzina);
		
		return wgsCoordinateJson;
	}
	
	@Override
	public String toString() {
		return geoSirina + " " + geoDuzina;
	}

}
