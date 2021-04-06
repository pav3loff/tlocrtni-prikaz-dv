package hr.fer.pavlic.dipl.model;

import java.util.List;

import org.dom4j.Element;

/**
 * Izolator zateznog stupa prikazuje se u obliku pravokutnika (4 tocke)
 * Izolator nosivog stupa prikazuje se u obliku kruga
 */
public class PrikazIzolatoraZateznogStupa {
	
	private Izolator izolator;
	private List<VrhPravokutnikaIzolatora> vrhoviPravokutnika;
	
	public PrikazIzolatoraZateznogStupa(Izolator izolator, List<VrhPravokutnikaIzolatora> vrhoviPravokutnika) {
		super();
		this.izolator = izolator;
		this.vrhoviPravokutnika = vrhoviPravokutnika;
	}

	public Izolator getIzolator() {
		return izolator;
	}

	public void setIzolator(Izolator izolator) {
		this.izolator = izolator;
	}

	public List<VrhPravokutnikaIzolatora> getVrhoviPravokutnika() {
		return vrhoviPravokutnika;
	}

	public void setVrhoviPravokutnika(List<VrhPravokutnikaIzolatora> vrhoviPravokutnika) {
		this.vrhoviPravokutnika = vrhoviPravokutnika;
	}

	public void getAsOsmXmlElement(Element parent) {
		Element izolatorWay = parent.addElement("way")
				.addAttribute("id", Long.toString(this.izolator.getUid()))
				.addAttribute("version", "1");
		
		izolatorWay.addElement("tag").addAttribute("k", "area").addAttribute("v", "yes");
		izolatorWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "izolator");
		izolatorWay.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.izolator.getIdIzolatora()));
		izolatorWay.addElement("tag").addAttribute("k", "materijal").addAttribute("v", this.izolator.getMaterijal());
		izolatorWay.addElement("tag").addAttribute("k", "izvedba").addAttribute("v", this.izolator.getIzvedba());
		izolatorWay.addElement("tag").addAttribute("k", "brojClanaka").addAttribute("v", Integer.toString(this.izolator.getBrojClanaka()));
		
		for(VrhPravokutnikaIzolatora vrhPravokutnika : this.vrhoviPravokutnika) {
			Element vrh = parent.addElement("node")
					.addAttribute("id", Long.toString(vrhPravokutnika.getUid()))
					.addAttribute("version", "1")
					.addAttribute("lat", Double.toString(vrhPravokutnika.getVrhWgs().getGeoSirina()))
					.addAttribute("lon", Double.toString(vrhPravokutnika.getVrhWgs().getGeoDuzina()));

			vrh.addElement("tag").addAttribute("k", "type").addAttribute("v", "vrhIzolatora");
			
			izolatorWay.addElement("nd").addAttribute("ref", Long.toString(vrhPravokutnika.getUid()));
		}
		
		izolatorWay.addElement("nd").addAttribute("ref", Long.toString(this.vrhoviPravokutnika.get(0).getUid())); // Još jednom dodati početnu točku, da se linija zatvori u poligon
	}

}
