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
	private List<Integer> idStv;
	private List<WgsCoordinate> koordinateStv;
	// OSM XML way (linije) zahtijevaju reference na čvorove kojima prolaze (čvorovi se referenciraju preko uida)
	private List<Long> osmXmlStvUids;
	private List<Raspon> rasponi;
	
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
		this.rasponi = new LinkedList<>();
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
		this.rasponi = new LinkedList<>();
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

	public List<Raspon> getRasponi() {
		return rasponi;
	}

	public void setRasponi(List<Raspon> rasponi) {
		this.rasponi = rasponi;
	}

	private void updateKoordinateStv(List<Stup> stupovi) {
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
	
	public void generateRasponi(List<Stup> stupovi) {
		this.updateKoordinateStv(stupovi);
		
		// u listi koordinata stv nalaze se koordinate kroz koje vodic prolazi
		// ako lista ima sveukupno 5 koordinata, rasponi se generiraju kao:
		// 1. raspon: POCETAK - 1. koordinata, KRAJ - 2. koordinata
		// 2. raspon: POCETAK - 2. koordinata, KRAJ - 3. koordinata
		// 3. raspon: POCETAK - 3. koordinata, KRAJ - 4. koordinata
		// 4. raspon: POCETAK - 4. koordinata, KRAJ - 5. koordinata
		
		for(int i = 1; i < this.koordinateStv.size(); i++) {
			// trenutni iterator je i, a prethodni je i - 1
			WgsCoordinate pocKoordinata = this.koordinateStv.get(i - 1);
			WgsCoordinate krajKoordinata = this.koordinateStv.get(i);
			
			Raspon raspon = new Raspon(pocKoordinata.getGeoSirina(), pocKoordinata.getGeoDuzina(), 
					krajKoordinata.getGeoSirina(), krajKoordinata.getGeoDuzina());
			
			this.rasponi.add(raspon);
		}
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
