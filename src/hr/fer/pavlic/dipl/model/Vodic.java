package hr.fer.pavlic.dipl.model;

import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class Vodic {
	
	private int idVodica;
	private String oznakaFaze;
	private String materijal;
	private List<Integer> idSt; // identifikatori spojnih točaka na koje se spaja
	private List<SpojnaTocka> spojneTocke; // polje koordinata u sustavu WGS84
	
	public Vodic() {
		super();
	}
	
	public Vodic(int idVodica, String oznakaFaze, String materijal, List<Integer> idSt) {
		super();
		this.idVodica = idVodica;
		this.oznakaFaze = oznakaFaze;
		this.materijal = materijal;
		this.idSt = idSt;
		this.spojneTocke = new LinkedList<>();
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
		
		if(!(vodicJson.isNull("idSt"))) {
			JSONArray idStJson = vodicJson.getJSONArray("idSt");
			
			this.idSt = new LinkedList<>();
			
			for(int i = 0; i < idStJson.length(); i++) {
				int idSt = idStJson.getInt(i);
				
				this.idSt.add(idSt);
			}
		}
		
		this.spojneTocke = new LinkedList<>();
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

	public void updateKoordinateSt(List<Stup> stupovi) {
		// redom proci kroz sve id-eve stva i za svaki pronaci koordinate
		for(Integer idSt : this.idSt) {
			for(Stup stup : stupovi) {
				for(Izolator izolator : stup.getIzolatori()) {
					SpojnaTocka stv = izolator.getStv();
					SpojnaTocka sti = izolator.getSti();
					
					if(idSt == stv.getIdSt()) {
						this.spojneTocke.add(stv);
						
						// Svaki od izolatora u paru (spajaju se na istu spojnu točku) sadrže ju - bilo koju od te dvije spojne točke potrebno je zanemariti
						if(!(this.spojneTocke.contains(sti))) {
							this.spojneTocke.add(sti);
						}
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
		
		JSONArray idStJson = new JSONArray();
		
		for(Integer idSt : this.idSt) {
			idStJson.put(idSt);
		}
		
		vodicJson.put("idSt", idStJson);
		
		JSONArray spojneTockeJson = new JSONArray();
		
		for(SpojnaTocka st : spojneTocke) {
			spojneTockeJson.put(st.getJson());
		}
		
		vodicJson.put("spojneTocke", spojneTockeJson);
		
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
		
		for(SpojnaTocka st : this.spojneTocke) {
			vodicWay.addElement("nd").addAttribute("ref", Long.toString(st.getUid()));
		}
	}

}
