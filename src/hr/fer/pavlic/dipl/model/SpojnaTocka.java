package hr.fer.pavlic.dipl.model;

import java.sql.Timestamp;

import org.dom4j.Element;
import org.json.JSONArray;
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
	private long uid;
	
	public SpojnaTocka(int idSt, double x, double y, double z, TipSpojneTocke tip) {
		super();
		this.idSt = idSt;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tip = tip;
		this.geoSirina = 0;
		this.geoDuzina = 0;
		this.uid = Long.valueOf(UidGenerator.getUidString());
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
		this.uid = Long.valueOf(UidGenerator.getUidString());
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
		this.uid = spojnaTocka.uid;
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
		
		this.uid = Long.valueOf(UidGenerator.getUidString());
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

	public TipSpojneTocke getTip() {
		return tip;
	}

	public void setTip(TipSpojneTocke tip) {
		this.tip = tip;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
	public JSONArray getAsSimpleJson() {
		JSONArray stJson = new JSONArray();
		
		stJson.put(this.geoSirina);
		stJson.put(this.geoDuzina);
		
		return stJson;
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
	
	public void getAsOsmXmlElement(Element root, boolean isStupZatezni) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Element stNode = root.addElement("node")
				.addAttribute("id", Long.toString(this.uid))
				.addAttribute("version", "1")
				.addAttribute("timestamp", timestamp.toString())
				.addAttribute("lat", Double.toString(this.geoSirina))
				.addAttribute("lon", Double.toString(this.geoDuzina));

		stNode.addElement("tag").addAttribute("k", "type").addAttribute("v", tip.toString());
		stNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idSt));
		stNode.addElement("tag").addAttribute("k", "st_x").addAttribute("v", Double.toString(this.x));
		stNode.addElement("tag").addAttribute("k", "st_y").addAttribute("v", Double.toString(this.y));
		stNode.addElement("tag").addAttribute("k", "st_z").addAttribute("v", Double.toString(this.z));
		
		if(!isStupZatezni) {
			stNode.addElement("tag").addAttribute("k", "invisible").addAttribute("v", "yes"); // Ako je stup nosivi, izolator, STI i STV su u jednoj tocki pa je STI i STV moguce zanemariti u prikazu
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SpojnaTocka)) {
			return false;
		}
		
		SpojnaTocka other = (SpojnaTocka) obj;
		
		return this.idSt == other.idSt;
	}
	
	@Override
	public String toString() {
		return String.format("------[%s] ID: %d, X: %.2f, Y: %.2f, Z: %.2f, LAT: %f, LONG: %f\n", 
				this.tip.toString(), this.idSt, this.x, this.y, this.z, this.geoSirina, this.geoDuzina);
	}
	
}
