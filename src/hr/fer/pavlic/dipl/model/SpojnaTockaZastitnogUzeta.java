package hr.fer.pavlic.dipl.model;

import org.dom4j.Element;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class SpojnaTockaZastitnogUzeta {

	private int idStzu;
	private double x;
	private double y;
	private double z;
	private double geoSirina;
	private double geoDuzina;
	
	public SpojnaTockaZastitnogUzeta(int idStzu, double x, double y, double z, double geoSirina, double geoDuzina) {
		super();
		this.idStzu = idStzu;
		this.x = x;
		this.y = y;
		this.z = z;
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
	}
	
	public SpojnaTockaZastitnogUzeta(SpojnaTockaZastitnogUzeta spojnaTockaZu) {
		super();
		this.idStzu = spojnaTockaZu.idStzu;
		this.x = spojnaTockaZu.x;
		this.y = spojnaTockaZu.y;
		this.z = spojnaTockaZu.z;
		this.geoSirina = spojnaTockaZu.geoSirina;
		this.geoDuzina = spojnaTockaZu.geoDuzina;
	}

	public SpojnaTockaZastitnogUzeta(JSONObject spojnaTockaZuJson) {
		if(!(spojnaTockaZuJson.isNull("idStzu"))) {
			this.idStzu = spojnaTockaZuJson.getInt("idStzu");
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

	public int getIdStzu() {
		return idStzu;
	}

	public void setIdStzu(int idStzu) {
		this.idStzu = idStzu;
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
		
		stzuJson.put("idStzu", this.idStzu);
		stzuJson.put("x", this.x);
		stzuJson.put("y", this.y);
		stzuJson.put("z", this.z);
		stzuJson.put("geoSirina", this.geoSirina);
		stzuJson.put("geoDuzina", this.geoDuzina);
		
		return stzuJson;
	}
	
	public Element getAsOsmXmlElement(Element parent) {
		Element stzuNode = parent.addElement("node")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("lat", Double.toString(this.geoSirina))
				.addAttribute("lon", Double.toString(this.geoDuzina));

		stzuNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "stzu");
		stzuNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idStzu));
		stzuNode.addElement("tag").addAttribute("k", "x").addAttribute("v", Double.toString(this.x));
		stzuNode.addElement("tag").addAttribute("k", "y").addAttribute("v", Double.toString(this.y));
		stzuNode.addElement("tag").addAttribute("k", "z").addAttribute("v", Double.toString(this.z));
		
		return parent;
	}
	
}
