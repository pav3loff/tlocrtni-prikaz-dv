package hr.fer.pavlic.dipl.model;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class ZastitnoUze {
	
	private int idZastitnogUzeta;
	private List<Integer> idStzu; // identifikatori spojnih točaka zaštitne užadi na koje se spaja
	private List<WgsCoordinate> koordinateStzu; // polje koordinata STZU u sustavu WGS84
	private List<Long> osmXmlStzuUids; // OSM XML way (linije) zahtijevaju reference na čvorove kojima prolaze (čvorovi se referenciraju preko uida)
	
	public ZastitnoUze() {
		super();
	}

	public ZastitnoUze(int idZastitnogUzeta, List<Integer> idStzu) {
		super();
		this.idZastitnogUzeta = idZastitnogUzeta;
		this.idStzu = idStzu;
		this.koordinateStzu = new LinkedList<>();
		this.osmXmlStzuUids = new LinkedList<>();
	}
	
	public ZastitnoUze(JSONObject zastitnoUzeJson) {
		if(!(zastitnoUzeJson.isNull("idZastitnogUzeta"))) {
			this.setIdZastitnogUzeta(zastitnoUzeJson.getInt("idZastitnogUzeta"));
		}
		
		if(!(zastitnoUzeJson.isNull("idStzu"))) {
			JSONArray idStzuJson = zastitnoUzeJson.getJSONArray("idStzu");
			
			this.idStzu = new LinkedList<>();
			
			for(int i = 0; i < idStzuJson.length(); i++) {
				int idStzu = idStzuJson.getInt(i);
				
				this.idStzu.add(idStzu);
			}
		}
		
		this.koordinateStzu = new LinkedList<>();
		this.osmXmlStzuUids = new LinkedList<>();
	}

	public int getIdZastitnogUzeta() {
		return idZastitnogUzeta;
	}

	public void setIdZastitnogUzeta(int idZastitnogUzeta) {
		this.idZastitnogUzeta = idZastitnogUzeta;
	}

	public List<Integer> getIdStzu() {
		return idStzu;
	}

	public void setIdStzu(List<Integer> idStzu) {
		this.idStzu = idStzu;
	}
	
	public List<WgsCoordinate> getKoordinateStzu() {
		return koordinateStzu;
	}

	public void setKoordinateStzu(List<WgsCoordinate> koordinateStzu) {
		this.koordinateStzu = koordinateStzu;
	}

	public List<Long> getOsmXmlStzuUids() {
		return osmXmlStzuUids;
	}

	public void setOsmXmlStzuUids(List<Long> osmXmlStzuUids) {
		this.osmXmlStzuUids = osmXmlStzuUids;
	}
	
	public void updateKoordinateStzu(List<Stup> stupovi) {
		// redom proci kroz sve id-eve stzuova i za svaki pronaci koordinate tog stzua
		for(Integer idStzu : this.idStzu) {
			for(Stup stup : stupovi) {
				for(SpojnaTocka stzu : stup.getSpojneTockeZu()) {
					if(idStzu == stzu.getIdSt()) {
						this.koordinateStzu.add(new WgsCoordinate(stzu.getGeoSirina(), stzu.getGeoDuzina()));
						
						this.osmXmlStzuUids.add(stzu.getUid()); // samo za generiranje WAYova u OSM XMLu
					}
				}
			}
		}
	}
	
	public JSONObject getJson() {
		JSONObject zastitnoUzeJson = new JSONObject();
		
		zastitnoUzeJson.put("idZastitnogUzeta", this.idZastitnogUzeta);
		
		JSONArray idStzuJson = new JSONArray();
		
		for(Integer idStzu : this.idStzu) {
			idStzuJson.put(idStzu);
		}
		
		zastitnoUzeJson.put("idStzu", idStzuJson);
		
		JSONArray koordinateStzuJson = new JSONArray();
		
		for(WgsCoordinate koordinataStzu : koordinateStzu) {
			koordinateStzuJson.put(koordinataStzu.getJson());
		}
		
		zastitnoUzeJson.put("koordinateStzu", koordinateStzuJson);
		
		return zastitnoUzeJson;
	}
	
	public void getAsOsmXmlElement(Element parent) {
		Element zastitnoUzeWay = parent.addElement("way")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1");
		
		zastitnoUzeWay.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idZastitnogUzeta));
		zastitnoUzeWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "zastitno_uze");
		
		for(Long uid : osmXmlStzuUids) {
			zastitnoUzeWay.addElement("nd").addAttribute("ref", Long.toString(uid));
		}
	}

}
