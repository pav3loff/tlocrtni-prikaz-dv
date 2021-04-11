package hr.fer.pavlic.dipl.model;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

public class Dalekovod {
	
	private int idDalekovoda;
	private int napon;
	private List<Vodic> vodici;
	
	public Dalekovod() {
		super();
	}

	public Dalekovod(int idDalekovoda, int napon, List<Vodic> vodici) {
		super();
		this.idDalekovoda = idDalekovoda;
		this.napon = napon;
		this.vodici = vodici;
	}
	
	public Dalekovod(JSONObject dalekovodJson) {
		if(!(dalekovodJson.isNull("idDalekovoda"))) {
			this.setIdDalekovoda(dalekovodJson.getInt("idDalekovoda"));
		}
		
		if(!(dalekovodJson.isNull("napon"))) {
			this.setNapon(dalekovodJson.getInt("napon"));
		}
		
		if(!(dalekovodJson.isNull("vodici"))) {
			JSONArray vodiciJson = dalekovodJson.getJSONArray("vodici");
			
			this.vodici = new LinkedList<>();
			
			for(int i = 0; i < vodiciJson.length(); i++) {
				JSONObject vodicJson = vodiciJson.getJSONObject(i);
				
				this.vodici.add(new Vodic(vodicJson));
			}
		}
	}

	public int getIdDalekovoda() {
		return idDalekovoda;
	}

	public void setIdDalekovoda(int idDalekovoda) {
		this.idDalekovoda = idDalekovoda;
	}

	public int getNapon() {
		return napon;
	}

	public void setNapon(int napon) {
		this.napon = napon;
	}

	public List<Vodic> getVodici() {
		return vodici;
	}

	public void setVodici(List<Vodic> vodici) {
		this.vodici = vodici;
	}
	
	public JSONObject getAsJson() {
		JSONObject dalekovodJson = new JSONObject();
		
		dalekovodJson.put("idDalekovoda", this.idDalekovoda);
		dalekovodJson.put("napon", this.napon);
		
		JSONArray vodiciJson = new JSONArray();
		for(Vodic vodic : this.vodici) {
			vodiciJson.put(vodic.getAsJson());
		}
		
		dalekovodJson.put("vodici", vodiciJson);
		
		return dalekovodJson;
	}
	
	public void getAsOsmXmlElement(Element parent) {
		for(Vodic vodic : this.vodici) {
			vodic.getAsOsmXmlElement(parent, idDalekovoda, napon);
		}
	}

}
