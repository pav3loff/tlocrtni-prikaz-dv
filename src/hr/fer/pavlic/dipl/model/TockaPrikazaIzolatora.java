package hr.fer.pavlic.dipl.model;

import org.json.JSONArray;

import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class TockaPrikazaIzolatora {
	
	private UtmCoordinate tockaUtm;
	private WgsCoordinate tockaWgs;
	private long uid;
	
	public TockaPrikazaIzolatora(UtmCoordinate tockaUtm) {
		super();
		this.tockaUtm = tockaUtm;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public TockaPrikazaIzolatora(WgsCoordinate tockaWgs) {
		super();
		this.tockaWgs = tockaWgs;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public UtmCoordinate getTockaUtm() {
		return tockaUtm;
	}

	public void setTockaUtm(UtmCoordinate tockaUtm) {
		this.tockaUtm = tockaUtm;
	}

	public WgsCoordinate getTockaWgs() {
		return tockaWgs;
	}

	public void setTockaWgs(WgsCoordinate tockaWgs) {
		this.tockaWgs = tockaWgs;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void izracunajWgs() {
		this.tockaWgs = UtmWgsConverter.convertToWgs(this.tockaUtm);
	}
	
	public void izracunajUtm() {
		try {
			this.tockaUtm = UtmWgsConverter.convertToUtm(this.tockaWgs);
		} catch (Exception e) {
			System.out.println("Neuspjesna konverzija!");
		}
	}
	
	public JSONArray getAsJson() {
		JSONArray tockaJson = new JSONArray();
		
		tockaJson.put(this.tockaWgs.getGeoSirina());
		tockaJson.put(this.tockaWgs.getGeoDuzina());
		
		return tockaJson;
	}
	
}
