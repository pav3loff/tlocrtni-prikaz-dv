package hr.fer.pavlic.dipl.stupovi;

import org.json.JSONObject;

public class SpojnaTockaZastitnogUzeta {

	private int id;
	private double x;
	private double y;
	private double z;
	private double geoSirina;
	private double geoDuzina;
	
	public SpojnaTockaZastitnogUzeta(int id, double x, double y, double z, double geoSirina, double geoDuzina) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
	}

	public SpojnaTockaZastitnogUzeta(JSONObject spojnaTockaZuJson) {
		if(!(spojnaTockaZuJson.isNull("id"))) {
			this.id = spojnaTockaZuJson.getInt("id");
		}
		
		if(!(spojnaTockaZuJson.isNull("x"))) {
			this.x = spojnaTockaZuJson.getDouble("x");
		}
		
		if(!(spojnaTockaZuJson.isNull("y"))) {
			this.y = spojnaTockaZuJson.getDouble("y");
		}
		
		if(!(spojnaTockaZuJson.isNull("z"))) {
			this.z = spojnaTockaZuJson.getDouble("z");
		}
		
		if(!(spojnaTockaZuJson.isNull("geoSirina"))) {
			this.geoSirina = spojnaTockaZuJson.getDouble("geoSirina");
		} else {
			this.geoSirina = 0;
		}
		
		if(!(spojnaTockaZuJson.isNull("geoDuzina"))) {
			this.geoDuzina = spojnaTockaZuJson.getDouble("geoDuzina");
		} else {
			this.geoDuzina = 0;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
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

	public JSONObject getAsJson() {
		JSONObject stzuJson = new JSONObject();
		
		stzuJson.put("id", this.id);
		stzuJson.put("x", this.x);
		stzuJson.put("y", this.y);
		stzuJson.put("z", this.z);
		stzuJson.put("geoSirina", this.geoSirina);
		stzuJson.put("geoDuzina", this.geoDuzina);
		
		return stzuJson;
	}
	
}
