package hr.fer.pavlic.dipl.stupovi;

import org.dom4j.Element;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class Izolator {
	
	private final static double DECIMALNO_MJESTO = 100000000.0;
	private int idIzolatora;
	private int idSti;
	private double stiX;
	private double stiY;
	private double stiZ;
	private int idStv;
	private double stvX;
	private double stvY;
	private double stvZ;
	private double kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
	private String materijal;
	private String izvedba;
	private int brojClanaka;
	private double geoSirina;
	private double geoDuzina;
	private double otklon;
	private double stiGeoSirina;
	private double stiGeoDuzina;
	private double stvGeoSirina;
	private double stvGeoDuzina;
	
	public Izolator(int idIzolatora, int idSti, double stiX, double stiY, double stiZ, int idStv, double stvX, double stvY, double stvZ,
			double kutIzmedjuSpojneTockeVodicaIRavnineKonzole, String materijal, String izvedba, int brojClanaka) {
		super();
		this.idIzolatora = idIzolatora;
		this.idSti = idSti;
		this.stiX = stiX;
		this.stiY = stiY;
		this.stiZ = stiZ;
		this.idStv = idStv;
		this.stvX = stvX;
		this.stvY = stvY;
		this.stvZ = stvZ;
		this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
		this.materijal = materijal;
		this.izvedba = izvedba;
		this.brojClanaka = brojClanaka;
		this.geoSirina = this.geoDuzina = 0;
		this.otklon = 0;
		this.stiGeoSirina = this.stiGeoDuzina = this.stvGeoSirina = this.stvGeoDuzina = 0;
	}
	
	public Izolator(Izolator izolator) {
		super();
		this.idIzolatora = izolator.idIzolatora;
		this.idSti = izolator.idSti;
		this.stiX = izolator.stiX;
		this.stiY = izolator.stiY;
		this.stiZ = izolator.stiZ;
		this.idStv = izolator.idStv;
		this.stvX = izolator.stvX;
		this.stvY = izolator.stvY;
		this.stvZ = izolator.stvZ;
		this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = izolator.kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
		this.materijal = izolator.materijal;
		this.izvedba = izolator.izvedba;
		this.brojClanaka = izolator.brojClanaka;
		this.geoSirina = this.geoDuzina = 0;
		this.otklon = 0;
		this.stiGeoSirina = this.stiGeoDuzina = this.stvGeoSirina = this.stvGeoDuzina = 0;
	}

	public Izolator(JSONObject izolatorJson) {
		if(!(izolatorJson.isNull("idIzolatora"))) {
			this.idIzolatora = izolatorJson.getInt("idIzolatora");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatora"))) {
			JSONObject stiJson = izolatorJson.getJSONObject("spojnaTockaIzolatora");
			
			if(!(stiJson.isNull("idSti"))) {
				this.idSti = stiJson.getInt("idSti");
			}
			
			if(!(stiJson.isNull("x"))) {
				this.stiX = stiJson.getDouble("x");
			}
			
			if(!(stiJson.isNull("y"))) {
				this.stiY = stiJson.getDouble("y");
			}
			
			if(!(stiJson.isNull("z"))) {
				this.stiZ = stiJson.getDouble("z");
			}
			
			if(!(stiJson.isNull("geoSirina"))) {
				this.stiGeoSirina = stiJson.getDouble("geoSirina");
			} else {
				this.stiGeoSirina = 0;
			}
			
			if(!(stiJson.isNull("geoDuzina"))) {
				this.stiGeoDuzina = stiJson.getDouble("geoDuzina");
			} else {
				this.stiGeoDuzina = 0;
			}
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodica"))) {
			JSONObject stvJson = izolatorJson.getJSONObject("spojnaTockaVodica");
			
			if(!(stvJson.isNull("idStv"))) {
				this.idStv = stvJson.getInt("idStv");
			}
			
			if(!(stvJson.isNull("x"))) {
				this.stvX = stvJson.getDouble("x");
			}
			
			if(!(stvJson.isNull("y"))) {
				this.stvY = stvJson.getDouble("y");
			}
			
			if(!(stvJson.isNull("z"))) {
				this.stvZ = stvJson.getDouble("z");
			}
			
			if(!(stvJson.isNull("geoSirina"))) {
				this.stvGeoSirina = stvJson.getDouble("geoSirina");
			} else {
				this.stvGeoSirina = 0;
			}
			
			if(!(stvJson.isNull("geoDuzina"))) {
				this.stvGeoDuzina = stvJson.getDouble("geoDuzina");
			} else {
				this.stvGeoDuzina = 0;
			}
		}
		
		if(!(izolatorJson.isNull("kutIzmedjuSpojneTockeVodicaIRavnineKonzole"))) {
			this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = 
					izolatorJson.getDouble("kutIzmedjuSpojneTockeVodicaIRavnineKonzole");
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
		
		if(!(izolatorJson.isNull("geoSirina"))) {
			this.geoSirina = izolatorJson.getDouble("geoSirina");
		} else {
			this.geoSirina = 0;
		}
		
		if(!(izolatorJson.isNull("geoDuzina"))) {
			this.geoDuzina = izolatorJson.getDouble("geoDuzina");
		} else {
			this.geoDuzina = 0;
		}
		
		if(!(izolatorJson.isNull("otklon"))) {
			this.otklon = izolatorJson.getDouble("otklon");
		} else {
			this.otklon = 0;
		}
	}

	public int getIdIzolatora() {
		return idIzolatora;
	}

	public void setIdIzolatora(int idIzolatora) {
		this.idIzolatora = idIzolatora;
	}
	
	public int getIdSti() {
		return idSti;
	}
	
	public void setIdSti(int idSti) {
		this.idSti = idSti;
	}

	public double getStiX() {
		return stiX;
	}

	public void setStiX(double stiX) {
		this.stiX = stiX;
	}

	public double getStiY() {
		return stiY;
	}

	public void setStiY(double stiY) {
		this.stiY = stiY;
	}

	public double getStiZ() {
		return stiZ;
	}

	public void setStiZ(double stiZ) {
		this.stiZ = stiZ;
	}
	
	public int getIdStv() {
		return this.idStv;
	}
	
	public void setIdStv(int idStv) {
		this.idStv = idStv;
	}

	public double getStvX() {
		return stvX;
	}

	public void setStvX(double stvX) {
		this.stvX = stvX;
	}

	public double getStvY() {
		return stvY;
	}

	public void setStvY(double stvY) {
		this.stvY = stvY;
	}

	public double getStvZ() {
		return stvZ;
	}

	public void setStvZ(double stvZ) {
		this.stvZ = stvZ;
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

	public double getOtklon() {
		return otklon;
	}

	public void setOtklon(double otklon) {
		this.otklon = otklon;
	}

	public double getStiGeoSirina() {
		return stiGeoSirina;
	}

	public void setStiGeoSirina(double stiGeoSirina) {
		this.stiGeoSirina = stiGeoSirina;
	}

	public double getStiGeoDuzina() {
		return stiGeoDuzina;
	}

	public void setStiGeoDuzina(double stiGeoDuzina) {
		this.stiGeoDuzina = stiGeoDuzina;
	}

	public double getStvGeoSirina() {
		return stvGeoSirina;
	}

	public void setStvGeoSirina(double stvGeoSirina) {
		this.stvGeoSirina = stvGeoSirina;
	}

	public double getStvGeoDuzina() {
		return stvGeoDuzina;
	}

	public void setStvGeoDuzina(double stvGeoDuzina) {
		this.stvGeoDuzina = stvGeoDuzina;
	}
	
	public JSONObject getJson() {
		JSONObject stiJson = new JSONObject();
		stiJson.put("idSti", this.idSti);
		stiJson.put("x", this.stiX);
		stiJson.put("y", this.stiY);
		stiJson.put("z", this.stiZ);
		stiJson.put("geoSirina", this.stiGeoSirina);
		stiJson.put("geoDuzina", this.stiGeoDuzina);
		
		JSONObject stvJson = new JSONObject();
		stvJson.put("idStv", this.idStv);
		stvJson.put("x", this.stvX);
		stvJson.put("y", this.stvY);
		stvJson.put("z", this.stvZ);
		stvJson.put("geoSirina", this.stvGeoSirina);
		stvJson.put("geoDuzina", this.stvGeoDuzina);
		
		JSONObject izolatorJson = new JSONObject();
		izolatorJson.put("idIzolatora", this.idIzolatora);
		izolatorJson.put("kutIzmedjuSpojneTockeVodicaIRavnineKonzole", this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		izolatorJson.put("materijal", this.materijal);
		izolatorJson.put("izvedba", this.izvedba);
		izolatorJson.put("brojClanaka", this.brojClanaka);
		izolatorJson.put("geoSirina", this.geoSirina);
		izolatorJson.put("geoDuzina", this.geoDuzina);
		izolatorJson.put("otklon", this.otklon);
		izolatorJson.put("spojnaTockaIzolatora", stiJson);
		izolatorJson.put("spojnaTockaVodica", stvJson);
		
		return izolatorJson;
	}
	
	public Element getAsOsmXmlElement(Element parent) {
		Element izolatorNode = parent.addElement("node")
								.addAttribute("id", UidGenerator.getUidString())
								.addAttribute("version", "1")
								.addAttribute("lat", Double.toString(this.geoSirina))
								.addAttribute("lon", Double.toString(this.geoDuzina));
		
		izolatorNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "izolator");
		izolatorNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idIzolatora));
		izolatorNode.addElement("tag").addAttribute("k", "materijal").addAttribute("v", this.materijal);
		izolatorNode.addElement("tag").addAttribute("k", "izvedba").addAttribute("v", this.izvedba);
		izolatorNode.addElement("tag").addAttribute("k", "brojClanaka").addAttribute("v", Integer.toString(this.brojClanaka));
		
		Element stiNode = parent.addElement("node")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("lat", Double.toString(this.getStiGeoSirina()))
				.addAttribute("lon", Double.toString(this.getStiGeoDuzina()));
		
		stiNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "sti");
		stiNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idSti));
		stiNode.addElement("tag").addAttribute("k", "x").addAttribute("v", Double.toString(this.stiX));
		stiNode.addElement("tag").addAttribute("k", "y").addAttribute("v", Double.toString(this.stiY));
		stiNode.addElement("tag").addAttribute("k", "z").addAttribute("v", Double.toString(this.stiZ));
		
		Element stvNode = parent.addElement("node")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("lat", Double.toString(this.getStvGeoSirina()))
				.addAttribute("lon", Double.toString(this.getStvGeoDuzina()));
		
		stvNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "stv");
		stvNode.addElement("tag").addAttribute("k", "id").addAttribute("v",  Integer.toString(this.idStv));
		stvNode.addElement("tag").addAttribute("k", "x").addAttribute("v", Double.toString(this.stvX));
		stvNode.addElement("tag").addAttribute("k", "y").addAttribute("v", Double.toString(this.stvY));
		stvNode.addElement("tag").addAttribute("k", "z").addAttribute("v", Double.toString(this.stvZ));
		
		return parent;
	}
	
	/**
	 * Geo. širina izolatora dobiva se kao aritmetička sredina geo. širine STI i geo. širine STV
	 * Geo. dužina izolatora dobiva se kao arirmetička sredina geo. dužine STI i geo. dužine STV
	 */
	public void updateLatLong() {
		this.geoSirina = Math.round(
				((this.stiGeoSirina + this.stvGeoSirina) / 2) * DECIMALNO_MJESTO) / DECIMALNO_MJESTO;
		this.geoDuzina = Math.round(
				((this.stiGeoDuzina + this.stvGeoDuzina) / 2) * DECIMALNO_MJESTO) / DECIMALNO_MJESTO;
	}
	
	/**
	 * Otklon je kut kojeg centralna os izolatora zatvara s ravninom stupa
	 * Dobiva se pomoću orijentacije stupa te kuta između spojne točke vodiča i ravnine konzole
	 */
	public void updateOtklon(double orijentacija) {
		double otklon;
		
		if(orijentacija >= 0) {
			otklon = 180 - Math.abs(orijentacija);
		} else {
			otklon = Math.abs(orijentacija);
		}
		
		if(this.stvZ >= 0) {
			otklon -= 90 - Math.abs(this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		} else {
			otklon += 90 - Math.abs(this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		}
		
		this.otklon = otklon;
	}

}
