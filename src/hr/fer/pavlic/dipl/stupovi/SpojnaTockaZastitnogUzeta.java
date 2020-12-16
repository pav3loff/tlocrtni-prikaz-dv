package hr.fer.pavlic.dipl.stupovi;

import org.json.JSONObject;

public class SpojnaTockaZastitnogUzeta {

	private int idSpojneTockeZastitnogUzeta;
	private double spojnaTockaZastitnogUzetaX;
	private double spojnaTockaZastitnogUzetaY;
	private double spojnaTockaZastitnogUzetaZ;
	private double spojnaTockaZastitnogUzetaGeoSirina;
	private double spojnaTockaZastitnogUzetaGeoDuzina;
	
	public SpojnaTockaZastitnogUzeta(int idSpojneTockeZastitnogUzeta, double spojnaTockaZastitnogUzetaX,
			double spojnaTockaZastitnogUzetaY, double spojnaTockaZastitnogUzetaZ) {
		super();
		this.idSpojneTockeZastitnogUzeta = idSpojneTockeZastitnogUzeta;
		this.spojnaTockaZastitnogUzetaX = spojnaTockaZastitnogUzetaX;
		this.spojnaTockaZastitnogUzetaY = spojnaTockaZastitnogUzetaY;
		this.spojnaTockaZastitnogUzetaZ = spojnaTockaZastitnogUzetaZ;
		this.spojnaTockaZastitnogUzetaGeoSirina = this.spojnaTockaZastitnogUzetaGeoDuzina = 0;
	}
	
	public SpojnaTockaZastitnogUzeta(JSONObject spojnaTockaZastitnogUzetaJson) {
		if(!(spojnaTockaZastitnogUzetaJson.isNull("idSpojneTockeZastitnogUzeta"))) {
			this.idSpojneTockeZastitnogUzeta = spojnaTockaZastitnogUzetaJson.getInt("idSpojneTockeZastitnogUzeta");
		}
		
		if(!(spojnaTockaZastitnogUzetaJson.isNull("spojnaTockaZastitnogUzetaX"))) {
			this.spojnaTockaZastitnogUzetaX = spojnaTockaZastitnogUzetaJson.getDouble("spojnaTockaZastitnogUzetaX");
		}
		
		if(!(spojnaTockaZastitnogUzetaJson.isNull("spojnaTockaZastitnogUzetaY"))) {
			this.spojnaTockaZastitnogUzetaY = spojnaTockaZastitnogUzetaJson.getDouble("spojnaTockaZastitnogUzetaY");
		}
		
		if(!(spojnaTockaZastitnogUzetaJson.isNull("spojnaTockaZastitnogUzetaZ"))) {
			this.spojnaTockaZastitnogUzetaZ = spojnaTockaZastitnogUzetaJson.getDouble("spojnaTockaZastitnogUzetaZ");
		}
		
		if(!(spojnaTockaZastitnogUzetaJson.isNull("spojnaTockaZastitnogUzetaGeoSirina"))) {
			this.spojnaTockaZastitnogUzetaGeoSirina = spojnaTockaZastitnogUzetaJson.getDouble("spojnaTockaZastitnogUzetaGeoSirina");
		} else {
			this.spojnaTockaZastitnogUzetaGeoSirina = 0;
		}
		
		if(!(spojnaTockaZastitnogUzetaJson.isNull("spojnaTockaZastitnogUzetaGeoDuzina"))) {
			this.spojnaTockaZastitnogUzetaGeoDuzina = spojnaTockaZastitnogUzetaJson.getDouble("spojnaTockaZastitnogUzetaGeoDuzina");
		} else {
			this.spojnaTockaZastitnogUzetaGeoDuzina = 0;
		}
	}

	public int getIdSpojneTockeZastitnogUzeta() {
		return idSpojneTockeZastitnogUzeta;
	}

	public void setIdSpojneTockeZastitnogUzeta(int idSpojneTockeZastitnogUzeta) {
		this.idSpojneTockeZastitnogUzeta = idSpojneTockeZastitnogUzeta;
	}

	public double getSpojnaTockaZastitnogUzetaX() {
		return spojnaTockaZastitnogUzetaX;
	}

	public void setSpojnaTockaZastitnogUzetaX(double spojnaTockaZastitnogUzetaX) {
		this.spojnaTockaZastitnogUzetaX = spojnaTockaZastitnogUzetaX;
	}

	public double getSpojnaTockaZastitnogUzetaY() {
		return spojnaTockaZastitnogUzetaY;
	}

	public void setSpojnaTockaZastitnogUzetaY(double spojnaTockaZastitnogUzetaY) {
		this.spojnaTockaZastitnogUzetaY = spojnaTockaZastitnogUzetaY;
	}

	public double getSpojnaTockaZastitnogUzetaZ() {
		return spojnaTockaZastitnogUzetaZ;
	}

	public void setSpojnaTockaZastitnogUzetaZ(double spojnaTockaZastitnogUzetaZ) {
		this.spojnaTockaZastitnogUzetaZ = spojnaTockaZastitnogUzetaZ;
	}
	
	public double getSpojnaTockaZastitnogUzetaGeoSirina() {
		return spojnaTockaZastitnogUzetaGeoSirina;
	}

	public void setSpojnaTockaZastitnogUzetaGeoSirina(double spojnaTockaZastitnogUzetaGeoSirina) {
		this.spojnaTockaZastitnogUzetaGeoSirina = spojnaTockaZastitnogUzetaGeoSirina;
	}

	public double getSpojnaTockaZastitnogUzetaGeoDuzina() {
		return spojnaTockaZastitnogUzetaGeoDuzina;
	}

	public void setSpojnaTockaZastitnogUzetaGeoDuzina(double spojnaTockaZastitnogUzetaGeoDuzina) {
		this.spojnaTockaZastitnogUzetaGeoDuzina = spojnaTockaZastitnogUzetaGeoDuzina;
	}

	public JSONObject getAsJson() {
		JSONObject spojnaTockaZastitnogUzetaJson = new JSONObject();
		
		spojnaTockaZastitnogUzetaJson.put("idSpojneTockaZastitnogUzeta", this.idSpojneTockeZastitnogUzeta);
		spojnaTockaZastitnogUzetaJson.put("spojnaTockaZastitnogUzetaX", this.spojnaTockaZastitnogUzetaX);
		spojnaTockaZastitnogUzetaJson.put("spojnaTockaZastitnogUzetaY", this.spojnaTockaZastitnogUzetaY);
		spojnaTockaZastitnogUzetaJson.put("spojnaTockaZastitnogUzetaZ", this.spojnaTockaZastitnogUzetaZ);
		spojnaTockaZastitnogUzetaJson.put("spojnaTockaZastitnogUzetaGeoSirina", this.spojnaTockaZastitnogUzetaGeoSirina);
		spojnaTockaZastitnogUzetaJson.put("spojnaTockaZastitnogUzetaGeoDuzina", this.spojnaTockaZastitnogUzetaGeoDuzina);
		
		return spojnaTockaZastitnogUzetaJson;
	}
	
}
