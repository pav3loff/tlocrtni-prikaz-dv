package hr.fer.pavlic.dipl.model;

import java.util.List;

import org.json.JSONObject;

public class DvostrukaMackaStup extends Stup {
	
	public DvostrukaMackaStup(int id, boolean isZatezni, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori, List<SpojnaTocka> spojneTockeZu) {
		super(id, isZatezni, orijentacija, geoSirina, geoDuzina, visina, tipStupa, proizvodac, tezina,
				oznakaUzemljenja, vrstaZastite, izolatori, spojneTockeZu);
	}
	
	public DvostrukaMackaStup(JSONObject stupJson) {
		super(stupJson);
	}
	
	@Override
	public TipStupa getType() {
		return TipStupa.DVOSTRUKA_MACKA;
	}	
	
}
