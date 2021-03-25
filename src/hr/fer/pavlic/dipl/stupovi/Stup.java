package hr.fer.pavlic.dipl.stupovi;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public abstract class Stup {
	
	private final static double DECIMALNO_MJESTO = 100000000.0;
	private final static int RAZMAK = 2;
	private int idStupa;
	private boolean isZatezni;
	private double orijentacija;
	private double geoSirina;
	private double geoDuzina;
	private double visina;
	private String tipStupa;
	private String proizvodac;
	private double tezina;
	private String oznakaUzemljenja;
	private String vrstaZastite;
	private List<Izolator> izolatori;
	private List<SpojnaTockaZastitnogUzeta> spojneTockeZu;
	
	public Stup() {
		super();
	}
	
	public Stup(int idStupa, boolean isZatezni, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori, List<SpojnaTockaZastitnogUzeta> spojneTockeZu) {
		super();
		this.idStupa = idStupa;
		this.isZatezni = isZatezni;
		this.orijentacija = orijentacija;
		this.geoSirina = geoSirina;
		this.geoDuzina = geoDuzina;
		this.visina = visina;
		this.tipStupa = tipStupa;
		this.proizvodac = proizvodac;
		this.tezina = tezina;
		this.oznakaUzemljenja = oznakaUzemljenja;
		this.vrstaZastite = vrstaZastite;
		this.izolatori = izolatori;
		this.spojneTockeZu = spojneTockeZu;
	}
	
	public Stup(JSONObject stupJson) {
		if(!(stupJson.isNull("idStupa"))) {
			this.setIdStupa(stupJson.getInt("idStupa"));
		}
		
		if(!(stupJson.isNull("isZatezni"))) {
			this.setZatezni(stupJson.getBoolean("isZatezni"));
		}
		
		if(!(stupJson.isNull("orijentacija"))) {
			this.setOrijentacija(stupJson.getDouble("orijentacija"));
		}
		
		if(!(stupJson.isNull("geoSirina"))) {
			this.setGeoSirina(stupJson.getDouble("geoSirina"));
		}
		
		if(!(stupJson.isNull("geoDuzina"))) {
			this.setGeoDuzina(stupJson.getDouble("geoDuzina"));
		}
		
		if(!(stupJson.isNull("visina"))) {
			this.setVisina(stupJson.getDouble("visina"));
		}
		
		if(!(stupJson.isNull("tipStupa"))) {
			this.setTipStupa(stupJson.getString("tipStupa"));
		}
		
		if(!(stupJson.isNull("proizvodac"))) {
			this.setProizvodac(stupJson.getString("proizvodac"));
		}
		
		if(!(stupJson.isNull("tezina"))) {
			this.setTezina(stupJson.getDouble("tezina"));
		}
		
		if(!(stupJson.isNull("oznakaUzemljenja"))) {
			this.setOznakaUzemljenja(stupJson.getString("oznakaUzemljenja"));
		}
		
		if(!(stupJson.isNull("vrstaZastite"))) {
			this.setVrstaZastite(stupJson.getString("vrstaZastite"));
		}
		
		if(!(stupJson.isNull("izolatori"))) {
			JSONArray izolatoriJson = stupJson.getJSONArray("izolatori");
			
			this.izolatori = new LinkedList<Izolator>();
			
			for(int i = 0; i < izolatoriJson.length(); i++) {
				JSONObject izolatorJson = izolatoriJson.getJSONObject(i);
				
				this.izolatori.add(new Izolator(izolatorJson));
			}
		}
		
		if(!(stupJson.isNull("spojneTockeZastitneUzadi"))) {
			JSONArray spojneTockeZuJson = stupJson.getJSONArray("spojneTockeZastitneUzadi");
			
			this.spojneTockeZu = new LinkedList<SpojnaTockaZastitnogUzeta>();
			
			for(int i = 0; i < spojneTockeZuJson.length(); i++) {
				JSONObject spojnaTockaZuJson = spojneTockeZuJson.getJSONObject(i);
				
				this.spojneTockeZu.add(new SpojnaTockaZastitnogUzeta(spojnaTockaZuJson));
			}
		}
	}
	
	private static class Util {
		private static JSONArray getIzolatoriAsJsonArray(List<Izolator> izolatori) {
			JSONArray izolatoriJson = new JSONArray();
			
			for(Izolator izolator : izolatori) {
				izolatoriJson.put(izolator.getJson());
			}
			
			return izolatoriJson;
		}
		
		private static JSONArray getSpojneTockeZuAsJsonArray(
				List<SpojnaTockaZastitnogUzeta> spojneTockeZu) {
			JSONArray spojneTockeZuJson = new JSONArray();
			
			for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
				spojneTockeZuJson.put(spojnaTockaZu.getAsJson());
			}
			
			return spojneTockeZuJson;
		}
		
		// metoda containsKey sucelja Map objekte ne uspoređuje s equals; potrebno je ručno implementirati
		private static SpojnaTockaIzolatora getKeyIfExists(Map<SpojnaTockaIzolatora, List<Izolator>> parovi, SpojnaTockaIzolatora st) {
			for(Entry<SpojnaTockaIzolatora, List<Izolator>> par : parovi.entrySet()) {
				if(par.getKey().equals(st)) {
					return par.getKey();
				}
			}
							
			return null;
		}
	}
	
	public int getIdStupa() {
		return idStupa;
	}

	public void setIdStupa(int idStupa) {
		this.idStupa = idStupa;
	}

	public boolean isZatezni() {
		return isZatezni;
	}

	public void setZatezni(boolean isZatezni) {
		this.isZatezni = isZatezni;
	}

	public double getOrijentacija() {
		return orijentacija;
	}

	public void setOrijentacija(double orijentacija) {
		this.orijentacija = orijentacija;
	}

	public double getGeoSirina() {
		return geoSirina;
	}

	public void setGeoSirina(double geoSirina) {
		this.geoSirina = geoSirina;
	}

	public double getGeoDuzina() {
		return geoDuzina;
	}

	public void setGeoDuzina(double geoDuzina) {
		this.geoDuzina = geoDuzina;
	}

	public double getVisina() {
		return visina;
	}

	public void setVisina(double visina) {
		this.visina = visina;
	}

	public String getTipStupa() {
		return tipStupa;
	}

	public void setTipStupa(String tipStupa) {
		this.tipStupa = tipStupa;
	}

	public String getProizvodac() {
		return proizvodac;
	}

	public void setProizvodac(String proizvodac) {
		this.proizvodac = proizvodac;
	}

	public double getTezina() {
		return tezina;
	}

	public void setTezina(double tezina) {
		this.tezina = tezina;
	}

	public String getOznakaUzemljenja() {
		return oznakaUzemljenja;
	}

	public void setOznakaUzemljenja(String oznakaUzemljenja) {
		this.oznakaUzemljenja = oznakaUzemljenja;
	}

	public String getVrstaZastite() {
		return vrstaZastite;
	}

	public void setVrstaZastite(String vrstaZastite) {
		this.vrstaZastite = vrstaZastite;
	}

	public List<Izolator> getIzolatori() {
		return izolatori;
	}

	public void setIzolatori(List<Izolator> izolatori) {
		this.izolatori = izolatori;
	}
	
	public List<SpojnaTockaZastitnogUzeta> getSpojneTockeZu() {
		return spojneTockeZu;
	}

	public void setSpojneTockeZu(List<SpojnaTockaZastitnogUzeta> spojneTockeZu) {
		this.spojneTockeZu = spojneTockeZu;
	}

	public JSONObject getJson() {
		JSONObject stupJson = new JSONObject();
		
		stupJson.put("idStupa", this.idStupa);
		stupJson.put("oblikGlaveStupa", this.getType());
		stupJson.put("isZatezni", this.isZatezni);
		stupJson.put("orijentacija", this.orijentacija);
		stupJson.put("geoSirina", this.geoSirina);
		stupJson.put("geoDuzina", this.geoDuzina);
		stupJson.put("visina", this.visina);
		stupJson.put("tipStupa", this.tipStupa);
		stupJson.put("proizvodac", this.proizvodac);
		stupJson.put("tezina", this.tezina);
		stupJson.put("oznakaUzemljenja", this.oznakaUzemljenja);
		stupJson.put("vrstaZastite", this.vrstaZastite);
		stupJson.put("izolatori", Util.getIzolatoriAsJsonArray(this.izolatori));
		stupJson.put("spojneTockeZastitneUzadi", 
				Util.getSpojneTockeZuAsJsonArray(this.spojneTockeZu));
		
		return stupJson;
	}
	
	public Element getAsOsmXmlElement(Element parent) {
		Element stupNode = parent.addElement("node")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("lat", Double.toString(this.geoSirina))
				.addAttribute("lon", Double.toString(this.geoDuzina));

		stupNode.addElement("tag").addAttribute("k", "type").addAttribute("v", "stup");
		stupNode.addElement("tag").addAttribute("k", "id").addAttribute("v", Integer.toString(this.idStupa));
		stupNode.addElement("tag").addAttribute("k", "oblikGlaveStupa").addAttribute("v", this.getType().toString());
		stupNode.addElement("tag").addAttribute("k", "isZatezni").addAttribute("v", Boolean.toString(this.isZatezni));
		stupNode.addElement("tag").addAttribute("k", "visina").addAttribute("v", Double.toString(this.visina));
		stupNode.addElement("tag").addAttribute("k", "tipStupa").addAttribute("v", this.tipStupa);
		stupNode.addElement("tag").addAttribute("k", "proizvodac").addAttribute("v", this.proizvodac);
		stupNode.addElement("tag").addAttribute("k", "tezina").addAttribute("v", Double.toString(this.tezina));
		stupNode.addElement("tag").addAttribute("k", "oznakaUzemljenja").addAttribute("v", this.oznakaUzemljenja);
		stupNode.addElement("tag").addAttribute("k", "vrstaZastite").addAttribute("v", this.vrstaZastite);
		
		for(Izolator izolator : this.izolatori) {
			izolator.getAsOsmXmlElement(parent);
		}
		
		for(SpojnaTockaZastitnogUzeta stzu : this.spojneTockeZu) {
			stzu.getAsOsmXmlElement(parent);
		}
		
		return parent;
	}
	
	public void transform() {
		separateST();
		
		convertUtmToWgs();
		
		updateIzolatori();
	}
	
	/**
	 * Transformiraju se one spojne točke koje su udaljene od centralne osi stupa
	 * Spojne točke koje su na centralnoj osi (imaju X = 0) nije potrebno mijenjati
	 * Koordinate X spojnih točaka izolatora poprimaju vrijednosti RAZMAK * i (..., -6, -4, -2, 2, 4, 6, ...)
	 * Koordinate X spojnih točaka zaštitne užadi poprimaju vrijednosti RAZMAK * i + 1 (..., -5, -3, -1, 1, 3, 5, ...)
	 */
	private void separateST() {
		// transformacija spojnih točaka izolatora
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		List<Izolator> desniIzolatori = new LinkedList<>();
		List<Izolator> lijeviIzolatori = new LinkedList<>();
		
		// raspodjela izolatora lijevo i desno
		// centralni izolator (X = 0) ostaje nepromijenjen (ako postoji)
		for(Izolator izolator : this.izolatori) {
			if(izolator.getStiX() > 0) { // izolator je s jedne strane
				desniIzolatori.add(izolator);
			} else if(izolator.getStiX() < 0) { // izolator je s druge strane
				lijeviIzolatori.add(izolator);
			} else { // izolator je na centralnoj osi - ne mijenja se
				azuriraniIzolatori.add(izolator);
			}
		}
		
		if(desniIzolatori.size() > 0) {
			List<Izolator> azuriraniIzolatoriDesno = getSeparatedIzolatori(desniIzolatori, true);
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriDesno);
		}
		
		if(lijeviIzolatori.size() > 0) {
			List<Izolator> azuriraniIzolatoriLijevo = getSeparatedIzolatori(lijeviIzolatori, false);
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriLijevo);
		}
		
		this.izolatori = azuriraniIzolatori;
		
		// transformacija spojnih točaka zaštitne užadi
		List<SpojnaTockaZastitnogUzeta> spojneTockeZu = this.spojneTockeZu;
		List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZu = new LinkedList<>();
		List<SpojnaTockaZastitnogUzeta> desneSpojneTockeZu = new LinkedList<>();
		List<SpojnaTockaZastitnogUzeta> lijeveSpojneTockeZu = new LinkedList<>();
		
		// raspodjela spojnih točaka zaštitne užadi lijevo i desno
		// centralna spojna točka ZU (X = 0) ostaje nepromijenjena (ako postoji)
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
			if(spojnaTockaZu.getX() > 0) { // spojna točka je s jedne strane
				desneSpojneTockeZu.add(spojnaTockaZu);
			} else if(spojnaTockaZu.getX() < 0) { // spojna točka je s druge strane
				lijeveSpojneTockeZu.add(spojnaTockaZu);
			} else { // spojna točka je na centralnoj osi - ne mijenja se
				azuriraneSpojneTockeZu.add(spojnaTockaZu);
			}
		}
		
		if(desneSpojneTockeZu.size() > 0) {
			List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZuDesno = getSeparatedSpojneTockeZu(desneSpojneTockeZu, true);
		
			azuriraneSpojneTockeZu.addAll(azuriraneSpojneTockeZuDesno);
		}
		
		
		if(lijeveSpojneTockeZu.size() > 0) {
			List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZuLijevo = getSeparatedSpojneTockeZu(lijeveSpojneTockeZu, false);
			
			azuriraneSpojneTockeZu.addAll(azuriraneSpojneTockeZuLijevo);
		}
				
		this.spojneTockeZu = azuriraneSpojneTockeZu;
	}
	
	/**
	 * Vraća novu listu izolatora s ažuriranim vrijednostima (x, y, z) spojnih točaka izolatora i vodiča
	 * Za transformaciju je bitna orijentacija stupa i kut između spojne točke vodiča i ravnine konzole
	 */
	private List<Izolator> getAdjustedIzolatori(List<Izolator> izolatori) {
		List<Izolator> adjustedIzolatori = new LinkedList<>();
		
		for(Izolator izolator : izolatori) {
			adjustedIzolatori.add(new Izolator(izolator));
		}
		
		double alfa = Math.toRadians(this.orijentacija);
		
		for(Izolator izolator : adjustedIzolatori) {
			double xSti = izolator.getStiX();
			double zSti = izolator.getStiZ();
			double zStv = izolator.getStvZ();
			
			// izracun X STV
			double beta = Math.toRadians(Math.abs(izolator.getKutIzmedjuSpojneTockeVodicaIRavnineKonzole()));
			double d = Math.abs(zStv) * Math.sin(Math.toRadians(90) - beta) / Math.sin(beta);
			double xStv = xSti - d;
			
			// prilagodba STI (rotacija koordinatnog sustava za orijentaciju stupa)
			double azuriraniX = xSti * Math.cos(alfa) - zSti * Math.sin(alfa);
			double azuriraniZ = xSti * Math.sin(alfa) + zSti * Math.cos(alfa);
			
			// prilagodba STV (rotacija koordinatnog sustava za orijentaciju stupa)
			double azuriraniXStv = xStv * Math.cos(alfa) - zStv * Math.sin(alfa);
			double azuriraniZStv = xStv * Math.sin(alfa) + zStv * Math.cos(alfa);
			
			izolator.setStiX(azuriraniX);
			izolator.setStiZ(azuriraniZ);
			izolator.setStvX(azuriraniXStv);
			izolator.setStvZ(azuriraniZStv);
		}
		
		return adjustedIzolatori;
	}
	
	/**
	 * Vraća novu listu spojnih točaka zaštitne užadi s ažuriranim vrijednostima (x, y, z)
	 */
	private List<SpojnaTockaZastitnogUzeta> getAdjustedSpojneTockeZu(List<SpojnaTockaZastitnogUzeta> spojneTockeZu) {
		List<SpojnaTockaZastitnogUzeta> adjustedSpojneTockeZu = new LinkedList<>();
		
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
			adjustedSpojneTockeZu.add(new SpojnaTockaZastitnogUzeta(spojnaTockaZu));
		}
		
		double alfa = Math.toRadians(this.orijentacija);
		
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : adjustedSpojneTockeZu) {
			double x = spojnaTockaZu.getX();
			double z = spojnaTockaZu.getZ();
			
			double azuriraniX = x * Math.cos(alfa) - z * Math.sin(alfa);
			double azuriraniZ = x * Math.sin(alfa) + z * Math.cos(alfa);
			
			spojnaTockaZu.setX(azuriraniX);
			spojnaTockaZu.setZ(azuriraniZ);
		}
		
		return adjustedSpojneTockeZu;
	}
	
	/**
	 * Koordinate položaja stupa izvorno su u WGS84 sustavu, a koordinate spojnih točaka su u Kartezijevom sustavu u kojem je stup u ishodištu
	 * Koordinate položaja spojnih točaka dobivaju se na sljedeći način:
	 * - Položaj stupa transformira se u UTM sustav
	 * - Položaj spojne točke računa se kao (x = x + xStupa, z = z + zStupa) gdje je xStupa vrijednost easting udaljenosti stupa u UTM sustavu, a zStupa vrijednost je northing udaljenosti stupa u UTM sustavu
	 * - Dobiveni položaj spojne točke u UTM sustavu transformira se natrag u WGS84 sustav
	 */
	private void convertUtmToWgs() {
		try {
			// Potrebno je očuvati podatke o položaju spojnih točaka nakon razdvajanja
			List<Izolator> izolatori = getAdjustedIzolatori(this.izolatori);
			List<SpojnaTockaZastitnogUzeta> spojneTockeZu = getAdjustedSpojneTockeZu(this.spojneTockeZu);
			
			// Konverzija spojnih točaka izolatora i vodiča
			UtmCoordinate stupUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(this.geoSirina, this.geoDuzina));
			
			for(Izolator izolator : izolatori) {
				UtmCoordinate stiUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(), 
						stupUtm.getEasting() + izolator.getStiX(), 
						stupUtm.getNorthing() + izolator.getStiZ());
					
				WgsCoordinate stiWgs = UtmWgsConverter.convertToWgs(stiUtm);
					
				izolator.setStiGeoSirina(
						Math.round(stiWgs.getGeoSirina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				izolator.setStiGeoDuzina(
						Math.round(stiWgs.getGeoDuzina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
					
				UtmCoordinate stvUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(),
						stupUtm.getEasting() + izolator.getStvX(),
						stupUtm.getNorthing() + izolator.getStvZ());
					
				WgsCoordinate stvWgs = UtmWgsConverter.convertToWgs(stvUtm);
					
				izolator.setStvGeoSirina(
						Math.round(stvWgs.getGeoSirina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				izolator.setStvGeoDuzina(
						Math.round(stvWgs.getGeoDuzina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				
			}
			
			// Konverzija spojnih točaka zaštitne užadi
			for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
				UtmCoordinate spojnaTockaZuUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(), 
						stupUtm.getEasting() + spojnaTockaZu.getX(), 
						stupUtm.getNorthing() + spojnaTockaZu.getZ());
					
				WgsCoordinate spojnaTockaZuWgs = 
						UtmWgsConverter.convertToWgs(spojnaTockaZuUtm);
					
				spojnaTockaZu.setGeoSirina(
						Math.round(spojnaTockaZuWgs.getGeoSirina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				spojnaTockaZu.setGeoDuzina(
						Math.round(spojnaTockaZuWgs.getGeoDuzina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
			}
			
			// Postavljanje vrijednosti (geo. širina, geo. dužina) spojnih točaka izolatora i vodiča
			// Vrijednosti (x, y, z) spojnih točaka izolatora i vodiča nakon razdvajanja su očuvane
			for(int i = 0, length = this.izolatori.size(); i < length; i++) {
				Izolator izolator = this.izolatori.get(i);
				Izolator azuriraniIzolator = izolatori.get(i);
				
				izolator.setGeoSirina(azuriraniIzolator.getGeoSirina());
				izolator.setGeoDuzina(azuriraniIzolator.getGeoDuzina());
				izolator.setStiGeoSirina(azuriraniIzolator.getStiGeoSirina());
				izolator.setStiGeoDuzina(azuriraniIzolator.getStiGeoDuzina());
				izolator.setStvGeoSirina(azuriraniIzolator.getStvGeoSirina());
				izolator.setStvGeoDuzina(azuriraniIzolator.getStvGeoDuzina());
			}
			
			// Postavljanje vrijednosti (geo. širina, geo. dužina) spojnih točaka zaštitne užadi
			// Vrijednosti (x, y, z) spojnih točaka zaštitne užadi nakon razdvajanja su očuvane
			for(int i = 0, length = this.spojneTockeZu.size(); i < length; i++) {
				SpojnaTockaZastitnogUzeta spojnaTockaZu = this.spojneTockeZu.get(i);
				SpojnaTockaZastitnogUzeta azuriranaSpojnaTockaZu = spojneTockeZu.get(i);
				
				spojnaTockaZu.setGeoSirina(azuriranaSpojnaTockaZu.getGeoSirina());
				spojnaTockaZu.setGeoDuzina(azuriranaSpojnaTockaZu.getGeoDuzina());
			}
		} catch (Exception e) {
			System.out.println("Neuspješna konverzija!");
		}
	}
	
	/**
	 * Ažuriraju se geo. širina i geo. dužina stupa, te otklon izolatora
	 */
	private void updateIzolatori() {
		for(Izolator izolator : this.izolatori) {
			izolator.updateLatLong();
			
			izolator.updateOtklon(this.orijentacija);
		}
	}
	
	/**
	 * Vraća izmijenjenu listu izolatora kojima su spojne točke izolatora i vodiča razdvojene (izbjegavanje preklapanja)
	 */
	private List<Izolator> getSeparatedIzolatori(List<Izolator> izolatori, boolean isDesno) {
		// nosivi stup tipa bacva i dunav: 6 izolatora sa svake strane -> 3 para jednakih STI
		// nosivi stup tipa jela: 2 izolatora s jedna i 4 s druge strane
		// zatezni stupovi nemaju parove jednakih STI
		// grupiranje parova
		Map<SpojnaTockaIzolatora, List<Izolator>> parovi = new LinkedHashMap<>();
					
		for(Izolator izolator : izolatori) {
			SpojnaTockaIzolatora sti = new SpojnaTockaIzolatora(izolator.getIdSti(), izolator.getStiX(), 
					izolator.getStiY(), izolator.getStiZ());
					SpojnaTockaIzolatora kljuc = Util.getKeyIfExists(parovi, sti);
						
			if(kljuc != null) {
				List<Izolator> par = parovi.get(kljuc);
				par.add(izolator);
							
				parovi.put(kljuc, par);
			} else {
				parovi.put(sti, new LinkedList<>(Arrays.asList(izolator)));
			}
		}
					
		// sortirati parove prema padajućoj vrijednosti koordinate Y STI
		Stream<Entry<SpojnaTockaIzolatora, List<Izolator>>> streamParova = parovi.entrySet()
				.stream().sorted(Collections.reverseOrder(Entry.comparingByKey(new Comparator<SpojnaTockaIzolatora>() {

					@Override
					public int compare(SpojnaTockaIzolatora sti1, SpojnaTockaIzolatora sti2) {
						return Double.compare(sti1.getY(), sti1.getY());
					}
								
				})));
					
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		Iterator<Entry<SpojnaTockaIzolatora, List<Izolator>>> streamParovaIterator = streamParova.iterator();
					
		int i = 1;
		int predznak = isDesno ? 1 : -1; // desna strana ima predznak +, a lijeva -
					
		// koordinata X = RAZMAK * i * predznak
		while(streamParovaIterator.hasNext()) {
			List<Izolator> par = streamParovaIterator.next().getValue();
						
			for(Izolator izolator : par) {
				double noviX = RAZMAK * i * predznak;
				izolator.setStiX(noviX);
				izolator.setStvX(noviX);
			}
						
			azuriraniIzolatori.addAll(par);
						
			i += 1;
		}
		
		return azuriraniIzolatori;
	}
	
	/**
	 * Vraća izmijenjenu listu spojnih točaka koje su razdvojene (izbjegavanje preklapanja)
	 */
	private List<SpojnaTockaZastitnogUzeta> getSeparatedSpojneTockeZu(List<SpojnaTockaZastitnogUzeta> spojneTockeZu, 
			boolean isDesno) {
		// sortirati spojne točke zaštitne užadi prema rastućoj koordinati X
		spojneTockeZu.sort(new Comparator<SpojnaTockaZastitnogUzeta>() {

			@Override
			public int compare(SpojnaTockaZastitnogUzeta stZu1, SpojnaTockaZastitnogUzeta stZu2) {
				return isDesno ? 
						Double.compare(stZu1.getX(), stZu2.getX()) : 
						Double.compare(stZu2.getX(), stZu1.getX());
			}
						
		});
					
		int i = 0;
		int predznak = isDesno ? 1 : -1;
					
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
			// koordinata X = RAZMAK * i + 1 (desna strana)
			// koordinata X = (RAZMAK * i + 1) * -1 (lijeva strana)
			spojnaTockaZu.setX((RAZMAK * i + 1) * predznak);
						
			i += 1;
		}
		
		return spojneTockeZu;
	}
	
	public abstract TipStupa getType();

}
