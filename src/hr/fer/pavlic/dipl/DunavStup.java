package hr.fer.pavlic.dipl;

import java.util.List;

import org.json.JSONObject;

public class DunavStup extends Stup {
	
	public DunavStup() {
		super();
	}
	
	public DunavStup(int idStupa, boolean isZatezniStup, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori) {
		super(idStupa, isZatezniStup, orijentacija, geoSirina, geoDuzina, visina, tipStupa, proizvodac, tezina,
				oznakaUzemljenja, vrstaZastite, izolatori);
	}
	
	public DunavStup(JSONObject stupJson) {
		super(stupJson);
	}

	@Override
	public TipStupa getType() {
		return TipStupa.DUNAV;
	}

}
