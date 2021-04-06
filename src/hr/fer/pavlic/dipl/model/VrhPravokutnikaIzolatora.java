package hr.fer.pavlic.dipl.model;

import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class VrhPravokutnikaIzolatora {
	
	private UtmCoordinate vrhUtm;
	private WgsCoordinate vrhWgs;
	private long uid;
	
	public VrhPravokutnikaIzolatora(UtmCoordinate vrhUtm) {
		super();
		this.vrhUtm = vrhUtm;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public VrhPravokutnikaIzolatora(WgsCoordinate vrhWgs) {
		super();
		this.vrhWgs = vrhWgs;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public UtmCoordinate getVrhUtm() {
		return vrhUtm;
	}

	public void setVrhUtm(UtmCoordinate vrhUtm) {
		this.vrhUtm = vrhUtm;
	}

	public WgsCoordinate getVrhWgs() {
		return vrhWgs;
	}

	public void setVrhWgs(WgsCoordinate vrhWgs) {
		this.vrhWgs = vrhWgs;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void izracunajWgs() {
		this.vrhWgs = UtmWgsConverter.convertToWgs(this.vrhUtm);
	}
	
	public void izracunajUtm() {
		try {
			this.vrhUtm = UtmWgsConverter.convertToUtm(this.vrhWgs);
		} catch (Exception e) {
			System.out.println("Neuspjesna konverzija!");
		}
	}
	
}
