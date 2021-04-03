package hr.fer.pavlic.dipl.model;

import org.dom4j.Element;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class Izolator {
	
	private final static double DECIMALNO_MJESTO = 100000000.0;
	private int idIzolatora;
	private double kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
	private String materijal;
	private String izvedba;
	private int brojClanaka;
	private double geoSirina;
	private double geoDuzina;
	private double otklon;
	private SpojnaTocka sti;
	private SpojnaTocka stv;
	private long uid;
	
	public Izolator(int idIzolatora, double kutIzmedjuSpojneTockeVodicaIRavnineKonzole, 
			String materijal, String izvedba, int brojClanaka, SpojnaTocka sti, SpojnaTocka stv) {
		super();
		this.idIzolatora = idIzolatora;
		this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
		this.materijal = materijal;
		this.izvedba = izvedba;
		this.brojClanaka = brojClanaka;
		this.geoSirina = this.geoDuzina = 0;
		this.otklon = 0;
		this.sti = sti;
		this.stv = stv;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}
	
	public Izolator(Izolator izolator) {
		super();
		this.idIzolatora = izolator.idIzolatora;
		this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole = izolator.kutIzmedjuSpojneTockeVodicaIRavnineKonzole;
		this.materijal = izolator.materijal;
		this.izvedba = izolator.izvedba;
		this.brojClanaka = izolator.brojClanaka;
		this.geoSirina = this.geoDuzina = 0;
		this.otklon = 0;
		this.sti = izolator.sti;
		this.stv = izolator.stv;
		this.uid = izolator.uid;
	}

	public Izolator(JSONObject izolatorJson) {
		if(!(izolatorJson.isNull("idIzolatora"))) {
			this.idIzolatora = izolatorJson.getInt("idIzolatora");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatora"))) {
			JSONObject stiJson = izolatorJson.getJSONObject("spojnaTockaIzolatora");
			
			this.sti = new SpojnaTocka(stiJson);
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodica"))) {
			JSONObject stvJson = izolatorJson.getJSONObject("spojnaTockaVodica");
			
			this.stv = new SpojnaTocka(stvJson);
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
		
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public int getIdIzolatora() {
		return idIzolatora;
	}

	public void setIdIzolatora(int idIzolatora) {
		this.idIzolatora = idIzolatora;
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
	
	public SpojnaTocka getSti() {
		return sti;
	}

	public void setSti(SpojnaTocka sti) {
		this.sti = sti;
	}

	public SpojnaTocka getStv() {
		return stv;
	}

	public void setStv(SpojnaTocka stv) {
		this.stv = stv;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public JSONObject getJson() {
		JSONObject izolatorJson = new JSONObject();
		izolatorJson.put("idIzolatora", this.idIzolatora);
		izolatorJson.put("kutIzmedjuSpojneTockeVodicaIRavnineKonzole", this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		izolatorJson.put("materijal", this.materijal);
		izolatorJson.put("izvedba", this.izvedba);
		izolatorJson.put("brojClanaka", this.brojClanaka);
		izolatorJson.put("geoSirina", this.geoSirina);
		izolatorJson.put("geoDuzina", this.geoDuzina);
		izolatorJson.put("otklon", this.otklon);
		izolatorJson.put("spojnaTockaIzolatora", this.sti.getJson());
		izolatorJson.put("spojnaTockaVodica", this.stv.getJson());
		
		return izolatorJson;
	}
	
	public void getAsOsmXmlElement(Element parent) {
		this.updateLatLong();
		
		Element izolatorNode = parent.addElement("node")
								.addAttribute("id", Long.toString(this.uid))
								.addAttribute("version", "1")
								.addAttribute("lat", Double.toString(this.geoSirina))
								.addAttribute("lon", Double.toString(this.geoDuzina));
		
		izolatorNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "izolator");
		izolatorNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idIzolatora));
		izolatorNode.addElement("tag").addAttribute("k", "materijal").addAttribute("v", this.materijal);
		izolatorNode.addElement("tag").addAttribute("k", "izvedba").addAttribute("v", this.izvedba);
		izolatorNode.addElement("tag").addAttribute("k", "brojClanaka").addAttribute("v", Integer.toString(this.brojClanaka));
		
		this.sti.getAsOsmXmlElement(parent);
		
		this.stv.getAsOsmXmlElement(parent);
	}
	
	/**
	 * Geo. širina izolatora dobiva se kao aritmeti�?ka sredina geo. širine STI i geo. širine STV
	 * Geo. dužina izolatora dobiva se kao arirmeti�?ka sredina geo. dužine STI i geo. dužine STV
	 */
	public void updateLatLong() {
		this.geoSirina = Math.round(
				((this.sti.getGeoSirina() + this.stv.getGeoSirina()) / 2) * DECIMALNO_MJESTO) / DECIMALNO_MJESTO;
		this.geoDuzina = Math.round(
				((this.sti.getGeoDuzina() + this.stv.getGeoDuzina()) / 2) * DECIMALNO_MJESTO) / DECIMALNO_MJESTO;
	}
	
	/**
	 * Otklon je kut kojeg centralna os izolatora zatvara s ravninom stupa
	 * Dobiva se pomoću orijentacije stupa te kuta između spojne to�?ke vodi�?a i ravnine konzole
	 */
	public void updateOtklon(double orijentacija) {
		double otklon;
		
		if(orijentacija >= 0) {
			otklon = 180 - Math.abs(orijentacija);
		} else {
			otklon = Math.abs(orijentacija);
		}
		
		if(this.stv.getZ() >= 0) {
			otklon -= 90 - Math.abs(this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		} else {
			otklon += 90 - Math.abs(this.kutIzmedjuSpojneTockeVodicaIRavnineKonzole);
		}
		
		this.otklon = otklon;
	}
	
	@Override
	public String toString() {
		return String.format("---[IZOLATOR] ID: %d\n", this.idIzolatora);
	}

}
