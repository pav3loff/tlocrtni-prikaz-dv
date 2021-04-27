package hr.fer.pavlic.dipl.model;

import java.sql.Timestamp;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;

/**
 * Izolator zateznog stupa prikazuje se u obliku pravokutnika (4 tocke)
 * Izolator nosivog stupa prikazuje se u obliku kruga
 */
public class PrikazIzolatora {
	
	private Izolator izolator;
	private List<TockaPrikazaIzolatora> tockePrikazaIzolatora;
	
	public PrikazIzolatora(Izolator izolator, List<TockaPrikazaIzolatora> tockePrikazaIzolatora) {
		super();
		this.izolator = izolator;
		this.tockePrikazaIzolatora = tockePrikazaIzolatora;
	}

	public Izolator getIzolator() {
		return izolator;
	}

	public void setIzolator(Izolator izolator) {
		this.izolator = izolator;
	}

	public List<TockaPrikazaIzolatora> getTockePrikazaIzolatora() {
		return tockePrikazaIzolatora;
	}

	public void setTockePrikazaIzolatora(List<TockaPrikazaIzolatora> tockePrikazaIzolatora) {
		this.tockePrikazaIzolatora = tockePrikazaIzolatora;
	}
	
	public JSONArray getAsJson() {
		JSONArray prikazJson = new JSONArray();
		
		for(TockaPrikazaIzolatora tocka : this.tockePrikazaIzolatora) {
			prikazJson.put(tocka.getAsJson());
		}
		
		return prikazJson;
	}

	public void getAsOsmXmlElement(Element root) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		for(TockaPrikazaIzolatora tockaPrikazaIzolatora : this.tockePrikazaIzolatora) {
			Element vrh = root.addElement("node")
					.addAttribute("id", Long.toString(tockaPrikazaIzolatora.getUid()))
					.addAttribute("version", "1")
					.addAttribute("timestamp", timestamp.toString())
					.addAttribute("lat", Double.toString(tockaPrikazaIzolatora.getTockaWgs().getGeoSirina()))
					.addAttribute("lon", Double.toString(tockaPrikazaIzolatora.getTockaWgs().getGeoDuzina()));

			vrh.addElement("tag").addAttribute("k", "type").addAttribute("v", "tockaPrikazaIzolatora");
		}
		
		Element izolatorWay = root.addElement("way")
				.addAttribute("id", Long.toString(this.izolator.getUid()))
				.addAttribute("version", "1")
				.addAttribute("timestamp", timestamp.toString());
		
		izolatorWay.addElement("tag").addAttribute("k", "area").addAttribute("v", "yes");
		izolatorWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "izolator");
		izolatorWay.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.izolator.getIdIzolatora()));
		izolatorWay.addElement("tag").addAttribute("k", "materijal").addAttribute("v", this.izolator.getMaterijal());
		izolatorWay.addElement("tag").addAttribute("k", "izvedba").addAttribute("v", this.izolator.getIzvedba());
		izolatorWay.addElement("tag").addAttribute("k", "brojClanaka").addAttribute("v", Integer.toString(this.izolator.getBrojClanaka()));
		
		for(TockaPrikazaIzolatora tockaPrikazaIzolatora : this.tockePrikazaIzolatora) {
			izolatorWay.addElement("nd").addAttribute("ref", Long.toString(tockaPrikazaIzolatora.getUid()));
		}
		
		izolatorWay.addElement("nd").addAttribute("ref", Long.toString(this.tockePrikazaIzolatora.get(0).getUid())); // Još jednom dodati početnu točku, da se linija zatvori u poligon
	}

}
