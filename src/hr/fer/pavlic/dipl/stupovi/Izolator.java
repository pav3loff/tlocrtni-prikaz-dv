package hr.fer.pavlic.dipl.stupovi;

import org.json.JSONObject;

public class Izolator {
	
	private int idIzolatora;
	private double spojnaTockaIzolatoraX;
	private double spojnaTockaIzolatoraY;
	private double spojnaTockaIzolatoraZ;
	private double spojnaTockaVodicaX;
	private double spojnaTockaVodicaY;
	private double spojnaTockaVodicaZ;
	private double kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
	private String materijal;
	private String izvedba;
	private int brojClanaka;
	private double spojnaTockaIzolatoraGeoSirina;
	private double spojnaTockaIzolatoraGeoDuzina;
	private double spojnaTockaVodicaGeoSirina;
	private double spojnaTockaVodicaGeoDuzina;
	
	public Izolator(int idIzolatora, double spojnaTockaIzolatoraX, double spojnaTockaIzolatoraY,
			double spojnaTockaIzolatoraZ, double spojnaTockaVodicaX, double spojnaTockaVodicaY,
			double spojnaTockaVodicaZ, double kutIzmedjuSpojneTockeVodicaIRavnineKonzole, String materijal,
			String izvedba, int brojClanaka) {
		super();
		this.idIzolatora = idIzolatora;
		this.spojnaTockaIzolatoraX = spojnaTockaIzolatoraX;
		this.spojnaTockaIzolatoraY = spojnaTockaIzolatoraY;
		this.spojnaTockaIzolatoraZ = spojnaTockaIzolatoraZ;
		this.spojnaTockaVodicaX = spojnaTockaVodicaX;
		this.spojnaTockaVodicaY = spojnaTockaVodicaY;
		this.spojnaTockaVodicaZ = spojnaTockaVodicaZ;
		this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
		this.materijal = materijal;
		this.izvedba = izvedba;
		this.brojClanaka = brojClanaka;
		this.spojnaTockaIzolatoraGeoSirina = this.spojnaTockaIzolatoraGeoDuzina = 
				this.spojnaTockaVodicaGeoSirina = this.spojnaTockaVodicaGeoDuzina = 0;
	}
	
	public Izolator(JSONObject izolatorJson) {
		if(!(izolatorJson.isNull("idIzolatora"))) {
			this.idIzolatora = izolatorJson.getInt("idIzolatora");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatoraX"))) {
			this.spojnaTockaIzolatoraX = izolatorJson.getDouble("spojnaTockaIzolatoraX");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatoraY"))) {
			this.spojnaTockaIzolatoraY = izolatorJson.getDouble("spojnaTockaIzolatoraY");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatoraZ"))) {
			this.spojnaTockaIzolatoraZ = izolatorJson.getDouble("spojnaTockaIzolatoraZ");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodicaX"))) {
			this.spojnaTockaVodicaX = izolatorJson.getDouble("spojnaTockaVodicaX");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodicaY"))) {
			this.spojnaTockaVodicaY = izolatorJson.getDouble("spojnaTockaVodicaY");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodicaZ"))) {
			this.spojnaTockaVodicaZ = izolatorJson.getDouble("spojnaTockaVodicaZ");
		}
		
		if(!(izolatorJson.isNull("kutIzmedjuSpojneTockeVodicaIRavnineKonzole"))) {
			this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = izolatorJson.getDouble("kutIzmedjuSpojneTockeVodicaIRavnineKonzole");
		}
		
		if(!(izolatorJson.isNull("materijal"))) {
			this.materijal = izolatorJson.getString("materijal");
		}
		
		if(!(izolatorJson.isNull("izvedba"))) {
			this.izvedba = izolatorJson.getString("izvedba");
		}
		
		if(!(izolatorJson.isNull("brojClanaka"))) {
			this.brojClanaka = izolatorJson.getInt("brojClanaka");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatoraGeoSirina"))) {
			this.spojnaTockaIzolatoraGeoSirina = izolatorJson.getDouble("spojnaTockaIzolatoraGeoSirina");
		} else {
			this.spojnaTockaIzolatoraGeoSirina = 0;
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatoraGeoDuzina"))) {
			this.setSpojnaTockaIzolatoraGeoDuzina(izolatorJson.getDouble("spojnaTockaIzolatoraGeoDuzina"));
		} else {
			this.spojnaTockaIzolatoraGeoDuzina = 0;
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodicaGeoSirina"))) {
			this.spojnaTockaVodicaGeoSirina = izolatorJson.getDouble("spojnaTockaVodicaGeoSirina");
		} else {
			this.spojnaTockaVodicaGeoSirina = 0;
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodicaGeoDuzina"))) {
			this.spojnaTockaVodicaGeoDuzina = izolatorJson.getDouble("spojnaTockaVodicaGeoDuzina");
		} else {
			this.spojnaTockaVodicaGeoDuzina = 0;
		}
	}

	public int getIdIzolatora() {
		return idIzolatora;
	}

	public void setIdIzolatora(int idIzolatora) {
		this.idIzolatora = idIzolatora;
	}

	public double getSpojnaTockaIzolatoraX() {
		return spojnaTockaIzolatoraX;
	}

	public void setSpojnaTockaIzolatoraX(double spojnaTockaIzolatoraX) {
		this.spojnaTockaIzolatoraX = spojnaTockaIzolatoraX;
	}

	public double getSpojnaTockaIzolatoraY() {
		return spojnaTockaIzolatoraY;
	}

	public void setSpojnaTockaIzolatoraY(double spojnaTockaIzolatoraY) {
		this.spojnaTockaIzolatoraY = spojnaTockaIzolatoraY;
	}

	public double getSpojnaTockaIzolatoraZ() {
		return spojnaTockaIzolatoraZ;
	}

	public void setSpojnaTockaIzolatoraZ(double spojnaTockaIzolatoraZ) {
		this.spojnaTockaIzolatoraZ = spojnaTockaIzolatoraZ;
	}

	public double getSpojnaTockaVodicaX() {
		return spojnaTockaVodicaX;
	}

	public void setSpojnaTockaVodicaX(double spojnaTockaVodicaX) {
		this.spojnaTockaVodicaX = spojnaTockaVodicaX;
	}

	public double getSpojnaTockaVodicaY() {
		return spojnaTockaVodicaY;
	}

	public void setSpojnaTockaVodicaY(double spojnaTockaVodicaY) {
		this.spojnaTockaVodicaY = spojnaTockaVodicaY;
	}

	public double getSpojnaTockaVodicaZ() {
		return spojnaTockaVodicaZ;
	}

	public void setSpojnaTockaVodicaZ(double spojnaTockaVodicaZ) {
		this.spojnaTockaVodicaZ = spojnaTockaVodicaZ;
	}

	public double getKutIzmedjuSpojneTockeVodicaIRavnineKonzole() {
		return kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
	}

	public void setKutIzmedjuSpojneTockeVodicaIRavnineKonzole(double kutIzmedjuSpojneTockeVodicaIRavnineKonzole) {
		this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
	}

	public String getMaterijal() {
		return materijal;
	}

	public void setMaterijal(String materijal) {
		this.materijal = materijal;
	}

	public String getIzvedba() {
		return izvedba;
	}

	public void setIzvedba(String izvedba) {
		this.izvedba = izvedba;
	}

	public int getBrojClanaka() {
		return brojClanaka;
	}

	public void setBrojClanaka(int brojClanaka) {
		this.brojClanaka = brojClanaka;
	}
	
	public double getSpojnaTockaIzolatoraGeoSirina() {
		return spojnaTockaIzolatoraGeoSirina;
	}

	public void setSpojnaTockaIzolatoraGeoSirina(double spojnaTockaIzolatoraGeoSirina) {
		this.spojnaTockaIzolatoraGeoSirina = spojnaTockaIzolatoraGeoSirina;
	}

	public double getSpojnaTockaIzolatoraGeoDuzina() {
		return spojnaTockaIzolatoraGeoDuzina;
	}

	public void setSpojnaTockaIzolatoraGeoDuzina(double spojnaTockaIzolatoraGeoDuzina) {
		this.spojnaTockaIzolatoraGeoDuzina = spojnaTockaIzolatoraGeoDuzina;
	}

	public double getSpojnaTockaVodicaGeoSirina() {
		return spojnaTockaVodicaGeoSirina;
	}

	public void setSpojnaTockaVodicaGeoSirina(double spojnaTockaVodicaGeoSirina) {
		this.spojnaTockaVodicaGeoSirina = spojnaTockaVodicaGeoSirina;
	}

	public double getSpojnaTockaVodicaGeoDuzina() {
		return spojnaTockaVodicaGeoDuzina;
	}

	public void setSpojnaTockaVodicaGeoDuzina(double spojnaTockaVodicaGeoDuzina) {
		this.spojnaTockaVodicaGeoDuzina = spojnaTockaVodicaGeoDuzina;
	}

	public JSONObject getJson() {
		JSONObject izolatorJson = new JSONObject();
		
		izolatorJson.put("idIzolatora", this.idIzolatora);
		izolatorJson.put("spojnaTockaIzolatoraX", this.spojnaTockaIzolatoraX);
		izolatorJson.put("spojnaTockaIzolatoraY", this.spojnaTockaIzolatoraY);
		izolatorJson.put("spojnaTockaIzolatoraZ", this.spojnaTockaIzolatoraZ);
		izolatorJson.put("spojnaTockaVodicaX", this.spojnaTockaVodicaX);
		izolatorJson.put("spojnaTockaVodicaY", this.spojnaTockaVodicaY);
		izolatorJson.put("spojnaTockaVodicaZ", this.spojnaTockaVodicaZ);
		izolatorJson.put("kutIzmedjuSpojneTockeVodicaIRavnineKonzole", this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		izolatorJson.put("materijal", this.materijal);
		izolatorJson.put("izvedba", this.izvedba);
		izolatorJson.put("brojClanaka", this.brojClanaka);
		izolatorJson.put("spojnaTockaIzolatoraGeoSirina", this.spojnaTockaIzolatoraGeoSirina);
		izolatorJson.put("spojnaTockaIzolatoraGeoDuzina", this.spojnaTockaIzolatoraGeoDuzina);
		izolatorJson.put("spojnaTockaVodicaGeoSirina", this.spojnaTockaVodicaGeoSirina);
		izolatorJson.put("spojnaTockaVodicaGeoDuzina", this.spojnaTockaVodicaGeoDuzina);
		
		return izolatorJson;
	}

}
