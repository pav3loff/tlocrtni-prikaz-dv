package hr.fer.pavlic.dipl.model;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class ZastitnoUze {
	
	private int idZastitnogUzeta;
	private List<Integer> idSt; // identifikatori spojnih točaka na koje se spaja
	private List<SpojnaTocka> spojneTocke; // polje koordinata u sustavu WGS84
	
	public ZastitnoUze() {
		super();
	}

	public ZastitnoUze(int idZastitnogUzeta, List<Integer> idSt) {
		super();
		this.idZastitnogUzeta = idZastitnogUzeta;
		this.idSt = idSt;
		this.spojneTocke = new LinkedList<>();
	}
	
	public ZastitnoUze(JSONObject zastitnoUzeJson) {
		if(!(zastitnoUzeJson.isNull("idZastitnogUzeta"))) {
			this.setIdZastitnogUzeta(zastitnoUzeJson.getInt("idZastitnogUzeta"));
		}
		
		if(!(zastitnoUzeJson.isNull("idSt"))) {
			JSONArray idStJson = zastitnoUzeJson.getJSONArray("idSt");
			
			this.idSt = new LinkedList<>();
			
			for(int i = 0; i < idStJson.length(); i++) {
				int idStzu = idStJson.getInt(i);
				
				this.idSt.add(idStzu);
			}
		}
		
		this.spojneTocke = new LinkedList<>();
	}

	public int getIdZastitnogUzeta() {
		return idZastitnogUzeta;
	}

	public void setIdZastitnogUzeta(int idZastitnogUzeta) {
		this.idZastitnogUzeta = idZastitnogUzeta;
	}

	public List<Integer> getIdSt() {
		return idSt;
	}

	public void setIdSt(List<Integer> idSt) {
		this.idSt = idSt;
	}
	
	public List<SpojnaTocka> getSpojneTocke() {
		return spojneTocke;
	}

	public void setSpojneTocke(List<SpojnaTocka> spojneTocke) {
		this.spojneTocke = spojneTocke;
	}
	
	public void updateKoordinateStzu(List<Stup> stupovi) {
		// redom proci kroz sve id-eve st i za svaki pronaci koordinate
		for(Integer idSt : this.idSt) {
			for(Stup stup : stupovi) {
				for(SpojnaTocka st : stup.getSpojneTockeZu()) {
					if(idSt == st.getIdSt()) {
						this.spojneTocke.add(st);
					}
				}
			}
		}
	}
	
	public JSONObject getAsJson() {
		JSONObject zastitnoUzeJson = new JSONObject();
		
		zastitnoUzeJson.put("idZastitnogUzeta", this.idZastitnogUzeta);
		
		JSONArray koordinateSpojistaJson = new JSONArray();
		
		for(SpojnaTocka st : spojneTocke) {
			koordinateSpojistaJson.put(st.getAsSimpleJson());
		}
		
		zastitnoUzeJson.put("koordinateSpojista", koordinateSpojistaJson);
		
		return zastitnoUzeJson;
	}
	
	public void getAsOsmXmlElement(Element root) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Element zastitnoUzeWay = root.addElement("way")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("timestamp", timestamp.toString());
		
		zastitnoUzeWay.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idZastitnogUzeta));
		zastitnoUzeWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "zastitno_uze");
		
		for(SpojnaTocka st : this.spojneTocke) {
			zastitnoUzeWay.addElement("nd").addAttribute("ref", Long.toString(st.getUid()));
		}
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof ZastitnoUze) {
			ZastitnoUze other = (ZastitnoUze) object;
			
			return this.idZastitnogUzeta == other.idZastitnogUzeta;
		}
		
		return false;
	}

}
