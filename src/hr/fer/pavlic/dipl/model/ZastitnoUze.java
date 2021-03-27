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
	private List<Integer> idStzu;
	private List<WgsCoordinate> koordinateStzu;
	// OSM XML way (linije) zahtijevaju reference na čvorove kojima prolaze (čvorovi se referenciraju preko uida)
	private List<Long> osmXmlStzuUids;
	private List<Raspon> rasponi;
	
	public ZastitnoUze() {
		super();
	}

	public ZastitnoUze(int idZastitnogUzeta, List<Integer> idStzu) {
		super();
		this.idZastitnogUzeta = idZastitnogUzeta;
		this.idStzu = idStzu;
		this.koordinateStzu = new LinkedList<>();
		this.osmXmlStzuUids = new LinkedList<>();
		this.rasponi = new LinkedList<>();
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
		this.rasponi = new LinkedList<>();
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

	public List<Raspon> getRasponi() {
		return rasponi;
	}

	public void setRasponi(List<Raspon> rasponi) {
		this.rasponi = rasponi;
	}
	
	private void updateKoordinateStzu(List<Stup> stupovi) {
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

	public void generateRasponi(List<Stup> stupovi) {
		this.updateKoordinateStzu(stupovi);
		
		// u listi koordinata stzu nalaze se koordinate kroz koje zaštitno uže prolazi
		// ako lista ima sveukupno 5 koordinata, rasponi se generiraju kao:
		// 1. raspon: POCETAK - 1. koordinata, KRAJ - 2. koordinata
		// 2. raspon: POCETAK - 2. koordinata, KRAJ - 3. koordinata
		// 3. raspon: POCETAK - 3. koordinata, KRAJ - 4. koordinata
		// 4. raspon: POCETAK - 4. koordinata, KRAJ - 5. koordinata
		
		for(int i = 1; i < this.koordinateStzu.size(); i++) {
			// trenutni iterator je i, a prethodni je i - 1
			WgsCoordinate pocKoordinata = this.koordinateStzu.get(i - 1);
			WgsCoordinate krajKoordinata = this.koordinateStzu.get(i);
			
			Raspon raspon = new Raspon(pocKoordinata.getGeoSirina(), pocKoordinata.getGeoDuzina(), 
					krajKoordinata.getGeoSirina(), krajKoordinata.getGeoDuzina());
			
			this.rasponi.add(raspon);
		}
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
