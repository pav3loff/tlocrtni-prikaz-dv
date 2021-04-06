package hr.fer.pavlic.dipl.model;

import org.dom4j.Element;

import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class Tocka2D {

	private double x;
	private double z;
	private double geoSirina;
	private double geoDuzina;
	private long uid;
	
	public Tocka2D(double x, double z) {
		super();
		this.x = x;
		this.z = z;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * @param kutRotacije Kut rotacije koordinatnog sustava
	 */
	public void rotateTocka2D(double kutRotacije) {
		double noviX = this.x * Math.cos(kutRotacije) - this.z * Math.sin(kutRotacije);
		double noviZ = this.x * Math.sin(kutRotacije) + this.z * Math.cos(kutRotacije);
		
		this.x = noviX;
		this.z = noviZ;
	}
	
	public void izracunajKoordinatePremaReferentnojTocki(double refGeoSirina, double refGeoDuzina) {
		try {
			UtmCoordinate stupUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(refGeoSirina, refGeoDuzina));
			
			UtmCoordinate tockaUtm = new UtmCoordinate(
					stupUtm.getLongZone(), stupUtm.getLatZone(), 
					stupUtm.getEasting() + this.x, 
					stupUtm.getNorthing() + this.z);
				
			WgsCoordinate tockaWgs = UtmWgsConverter.convertToWgs(tockaUtm);
			
			this.geoSirina = tockaWgs.getGeoSirina();
			this.geoDuzina = tockaWgs.getGeoDuzina();
		} catch (Exception e) {
			System.out.println("Neuspješna konverzija!");
		}
	}
	
	public void getAsOsmXmlElement(Element parent) {
		Element tockaNode = parent.addElement("node")
				.addAttribute("id", Long.toString(this.uid))
				.addAttribute("version", "1")
				.addAttribute("lat", Double.toString(this.geoSirina))
				.addAttribute("lon", Double.toString(this.geoDuzina));

		tockaNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "vrhKonzole");
	}
	
}
