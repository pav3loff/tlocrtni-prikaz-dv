package hr.fer.pavlic.dipl.model;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class Vodic {
	
	private int idVodica;
	private String oznakaFaze;
	private String materijal;
	private List<Integer> idStv; // identifikatori spojnih točaka vodiča na koje se spaja
	private List<WgsCoordinate> koordinateStv; // polje koordinata STV u sustavu WGS84
	private List<Long> osmXmlStvUids; // OSM XML way (linije) zahtijevaju reference na čvorove kojima prolaze (čvorovi se referenciraju preko uida)
	
	public Vodic() {
		super();
	}
	
	public Vodic(int idVodica, String oznakaFaze, String materijal, List<Integer> idStv) {
		super();
		this.idVodica = idVodica;
		this.oznakaFaze = oznakaFaze;
		this.materijal = materijal;
		this.idStv = idStv;
		this.koordinateStv = new LinkedList<>();
		this.osmXmlStvUids = new LinkedList<>();
	}
	
	public Vodic(JSONObject vodicJson) {
		if(!(vodicJson.isNull("idVodica"))) {
			this.setIdVodica(vodicJson.getInt("idVodica"));
		}
		
		if(!(vodicJson.isNull("oznakaFaze"))) {
			this.setOznakaFaze(vodicJson.getString("oznakaFaze"));
		}
		
		if(!(vodicJson.isNull("materijal"))) {
			this.setMaterijal(vodicJson.getString("materijal"));
		}
		
		if(!(vodicJson.isNull("idStv"))) {
			JSONArray idStvJson = vodicJson.getJSONArray("idStv");
			
			this.idStv = new LinkedList<>();
			
			for(int i = 0; i < idStvJson.length(); i++) {
				int idStv = idStvJson.getInt(i);
				
				this.idStv.add(idStv);
			}
		}
		
		this.koordinateStv = new LinkedList<>();
		this.osmXmlStvUids = new LinkedList<>();
	}

	public int getIdVodica() {
		return idVodica;
	}

	public void setIdVodica(int idVodica) {
		this.idVodica = idVodica;
	}

	public String getOznakaFaze() {
		return oznakaFaze;
	}

	public void setOznakaFaze(String oznakaFaze) {
		this.oznakaFaze = oznakaFaze;
	}

	public String getMaterijal() {
		return materijal;
	}

	public void setMaterijal(String materijal) {
		this.materijal = materijal;
	}

	public List<Integer> getIdStv() {
		return idStv;
	}

	public void setIdStv(List<Integer> idStv) {
		this.idStv = idStv;
	}

	public List<WgsCoordinate> getKoordinateStv() {
		return koordinateStv;
	}

	public void setKoordinateStv(List<WgsCoordinate> koordinateStv) {
		this.koordinateStv = koordinateStv;
	}
	
	public List<Long> getOsmXmlStvUids() {
		return osmXmlStvUids;
	}

	public void setOsmXmlStvUids(List<Long> osmXmlStvUids) {
		this.osmXmlStvUids = osmXmlStvUids;
	}

	public void updateKoordinateStv(List<Stup> stupovi) {
		// redom proci kroz sve id-eve stvova i za svaki pronaci koordinate tog stva
		for(Integer idStv : this.idStv) {
			for(Stup stup : stupovi) {
				for(Izolator izolator : stup.getIzolatori()) {
					SpojnaTocka stv = izolator.getStv();
					
					if(idStv == stv.getIdSt()) {
						this.koordinateStv.add(new WgsCoordinate(stv.getGeoSirina(), stv.getGeoDuzina()));
						
						this.osmXmlStvUids.add(stv.getUid()); // samo za generiranje WAYova u OSM XMLu
					}
				}
			}
		}
	}
	
	public JSONObject getJson() {
		JSONObject vodicJson = new JSONObject();
		
		vodicJson.put("idVodica", this.idVodica);
		vodicJson.put("oznakaFaze", this.oznakaFaze);
		vodicJson.put("materijal", this.materijal);
		
		JSONArray idStvJson = new JSONArray();
		
		for(Integer idStv : this.idStv) {
			idStvJson.put(idStv);
		}
		
		vodicJson.put("idStv", idStvJson);
		
		JSONArray koordinateStvJson = new JSONArray();
		
		for(WgsCoordinate koordinataStv : koordinateStv) {
			koordinateStvJson.put(koordinataStv.getJson());
		}
		
		vodicJson.put("koordinateStv", koordinateStvJson);
		
		return vodicJson;
	}
	
	public void getAsOsmXmlElement(Element parent, int idDalekovoda, int naponDalekovoda) {
		Element vodicWay = parent.addElement("way")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1");
		
		vodicWay.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idVodica));
		vodicWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "vodic");
		vodicWay.addElement("tag").addAttribute("k", "idDalekovoda").addAttribute("v", Integer.toString(idDalekovoda));
		vodicWay.addElement("tag").addAttribute("k", "naponDalekovoda").addAttribute("v", Integer.toString(naponDalekovoda));	
		vodicWay.addElement("tag").addAttribute("k", "oznakaFaze").addAttribute("v", this.oznakaFaze);
		vodicWay.addElement("tag").addAttribute("k", "materijal").addAttribute("v", this.materijal);
		
		for(Long uid : osmXmlStvUids) {
			vodicWay.addElement("nd").addAttribute("ref", Long.toString(uid));
		}
	}

}
