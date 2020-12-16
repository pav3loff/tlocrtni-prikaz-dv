package hr.fer.pavlic.dipl.stupovi;

import java.util.List;

import org.json.JSONObject;

public class DvostrukiYStup extends Stup {
	
	public DvostrukiYStup() {
		super();
	}
	
	public DvostrukiYStup(int idStupa, boolean isZatezniStup, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori, List<SpojnaTockaZastitnogUzeta> spojneTockeZastitneUzadi) {
		super(idStupa, isZatezniStup, orijentacija, geoSirina, geoDuzina, visina, tipStupa, proizvodac, tezina,
				oznakaUzemljenja, vrstaZastite, izolatori, spojneTockeZastitneUzadi);
	}
	
	public DvostrukiYStup(JSONObject stupJson) {
		super(stupJson);
	}
	
	@Override
	public TipStupa getType() {
		return TipStupa.DVOSTRUKI_Y;
	}	

}
