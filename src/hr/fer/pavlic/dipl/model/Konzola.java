package hr.fer.pavlic.dipl.model;

import java.sql.Timestamp;
import java.util.List;

import org.dom4j.Element;

import hr.fer.pavlic.dipl.util.UidGenerator;

public class Konzola {
	
	private List<Tocka2D> vrhoviKonzole;

	public Konzola(List<Tocka2D> vrhoviKonzole) {
		super();
		this.vrhoviKonzole = vrhoviKonzole;
	}

	public List<Tocka2D> getVrhoviKonzole() {
		return vrhoviKonzole;
	}

	public void setVrhoviKonzole(List<Tocka2D> vrhoviKonzole) {
		this.vrhoviKonzole = vrhoviKonzole;
	}
	
	public void updateKoordinateVrhovaKonzole(double geoSirinaStupa, double geoDuzinaStupa) {
		for(Tocka2D tocka : this.vrhoviKonzole) {
			tocka.izracunajKoordinatePremaReferentnojTocki(geoSirinaStupa, geoDuzinaStupa);
		}
	}

	public void getAsOsmXmlElement(Element parent) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		for(Tocka2D tocka : this.vrhoviKonzole) {
			tocka.getAsOsmXmlElement(parent);
		}
		
		Element konzolaWay = parent.addElement("way")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("timestamp", timestamp.toString());
		
		konzolaWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "konzola");
		
		for(Tocka2D tocka : this.vrhoviKonzole) {
			konzolaWay.addElement("nd").addAttribute("ref", Long.toString(tocka.getUid()));
		}
		
		konzolaWay.addElement("nd").addAttribute("ref", Long.toString(this.vrhoviKonzole.get(0).getUid())); // Još jednom dodati početnu točku, da se linija zatvori u poligon
	}

}
