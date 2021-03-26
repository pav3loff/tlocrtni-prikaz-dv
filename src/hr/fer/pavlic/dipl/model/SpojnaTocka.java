package hr.fer.pavlic.dipl.model;

import org.dom4j.Element;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class SpojnaTocka {

	private int idSt;
	private double x;
	private double y;
	private double z;
	private double geoSirina;
	private double geoDuzina;
	private TipSpojneTocke tip;
	
	public SpojnaTocka(int idSt, double x, double y, double z, TipSpojneTocke tip) {
		super();
		this.idSt = idSt;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tip = tip;
		this.geoSirina = 0;
		this.geoDuzina = 0;
	}
	
	public SpojnaTocka(int idSt, double x, double y, double z, double geoSirina, double geoDuzina, TipSpojneTocke tip) {
		super();
		this.idSt = idSt;
		this.x = x;
		this.y = y;
		this.z = z;
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
		this.tip = tip;
	}
	
	public SpojnaTocka(SpojnaTocka spojnaTocka) {
		super();
		this.idSt = spojnaTocka.idSt;
		this.x = spojnaTocka.x;
		this.y = spojnaTocka.y;
		this.z = spojnaTocka.z;
		this.geoSirina = spojnaTocka.geoSirina;
		this.geoDuzina = spojnaTocka.geoDuzina;
		this.tip = spojnaTocka.tip;
	}

	public SpojnaTocka(JSONObject spojnaTockaJson) {
		// O kojem tipu spojne točke se radi vidljivo je iz imena atributa identifikator
		if(!(spojnaTockaJson.isNull("idSti"))) {
			this.idSt = spojnaTockaJson.getInt("idSti");
			this.tip = TipSpojneTocke.STI;
		} else if(!(spojnaTockaJson.isNull("idStv"))) {
			this.idSt = spojnaTockaJson.getInt("idStv");
			this.tip = TipSpojneTocke.STV;
		} else if(!(spojnaTockaJson.isNull("idStzu"))) {
			this.idSt = spojnaTockaJson.getInt("idStzu");
			this.tip = TipSpojneTocke.STZU;
		}
		
		if(!(spojnaTockaJson.isNull("x"))) {
			this.x = spojnaTockaJson.getDouble("x");
		}
		
		if(!(spojnaTockaJson.isNull("y"))) {
			this.y = spojnaTockaJson.getDouble("y");
		}
		
		if(!(spojnaTockaJson.isNull("z"))) {
			this.z = spojnaTockaJson.getDouble("z");
		}
		
		if(!(spojnaTockaJson.isNull("geoSirina"))) {
			this.geoSirina = spojnaTockaJson.getDouble("geoSirina");
		} else {
			this.geoSirina = 0;
		}
		
		if(!(spojnaTockaJson.isNull("geoDuzina"))) {
			this.geoDuzina = spojnaTockaJson.getDouble("geoDuzina");
		} else {
			this.geoDuzina = 0;
		}
	}

	public int getIdSt() {
		return idSt;
	}

	public void setIdSt(int idSt) {
		this.idSt = idSt;
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
		JSONObject stJson = new JSONObject();
		
		if(tip == TipSpojneTocke.STI) {
			stJson.put("idSti", this.idSt);
		} else if(tip == TipSpojneTocke.STV) {
			stJson.put("idStv", this.idSt);
		} else if(tip == TipSpojneTocke.STZU) {
			stJson.put("idStzu", this.idSt);
		}
		
		stJson.put("x", this.x);
		stJson.put("y", this.y);
		stJson.put("z", this.z);
		stJson.put("geoSirina", this.geoSirina);
		stJson.put("geoDuzina", this.geoDuzina);
		
		return stJson;
	}
	
	public Element getAsOsmXmlElement(Element parent) {
		Element stNode = parent.addElement("node")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("lat", Double.toString(this.geoSirina))
				.addAttribute("lon", Double.toString(this.geoDuzina));

		stNode.addElement("tag").addAttribute("k", "type").addAttribute("v", tip.toString());
		stNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idSt));
		stNode.addElement("tag").addAttribute("k", "x").addAttribute("v", Double.toString(this.x));
		stNode.addElement("tag").addAttribute("k", "y").addAttribute("v", Double.toString(this.y));
		stNode.addElement("tag").addAttribute("k", "z").addAttribute("v", Double.toString(this.z));
		
		return parent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SpojnaTocka)) {
			return false;
		}
		
		SpojnaTocka other = (SpojnaTocka) obj;
		
		return this.idSt == other.idSt;
	}
	
}
