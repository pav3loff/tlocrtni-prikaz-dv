package hr.fer.pavlic.dipl.stupovi;

import java.util.List;

import org.json.JSONObject;

public class BacvaStup extends Stup {
	
	public BacvaStup() {
		super();
	}
	
	public BacvaStup(int id, boolean isZatezni, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori, List<SpojnaTockaZastitnogUzeta> spojneTockeZu) {
		super(id, isZatezni, orijentacija, geoSirina, geoDuzina, visina, tipStupa, proizvodac, tezina,
				oznakaUzemljenja, vrstaZastite, izolatori, spojneTockeZu);
	}
	
	public BacvaStup(JSONObject stupJson) {
		super(stupJson);
	}
	
	@Override
	public TipStupa getType() {
		return TipStupa.BACVA;
	}	

}
