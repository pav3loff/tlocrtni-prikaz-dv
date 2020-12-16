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

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public abstract class Stup {
	
	private final static int RAZMAK = 2;
	private int idStupa;
	private boolean isZatezniStup;
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
	private List<SpojnaTockaZastitnogUzeta> spojneTockeZastitneUzadi;
	
	public Stup() {
		super();
	}
	
	public Stup(int idStupa, boolean isZatezniStup, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori, List<SpojnaTockaZastitnogUzeta> spojneTockeZastitneUzadi) {
		super();
		this.idStupa = idStupa;
		this.isZatezniStup = isZatezniStup;
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
		this.spojneTockeZastitneUzadi = spojneTockeZastitneUzadi;
	}
	
	public Stup(JSONObject stupJson) {
		if(!(stupJson.isNull("idStupa"))) {
			this.setIdStupa(stupJson.getInt("idStupa"));
		}
		
		if(!(stupJson.isNull("isZatezniStup"))) {
			this.setZatezniStup(stupJson.getBoolean("isZatezniStup"));
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
			JSONArray spojneTockeZastitneUzadi = stupJson.getJSONArray("spojneTockeZastitneUzadi");
			
			this.spojneTockeZastitneUzadi = new LinkedList<SpojnaTockaZastitnogUzeta>();
			
			for(int i = 0; i < spojneTockeZastitneUzadi.length(); i++) {
				JSONObject spojnaTockaZastitnogUzetaJson = spojneTockeZastitneUzadi.getJSONObject(i);
				
				this.spojneTockeZastitneUzadi.add(new SpojnaTockaZastitnogUzeta(spojnaTockaZastitnogUzetaJson));
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
		
		private static JSONArray getSpojneTockeZastitneUzadiAsJsonArray(
				List<SpojnaTockaZastitnogUzeta> spojneTockeZastitneUzadi) {
			JSONArray spojneTockeZastitneUzadiJson = new JSONArray();
			
			for(SpojnaTockaZastitnogUzeta spojnaTockaZastitnogUzeta : spojneTockeZastitneUzadi) {
				spojneTockeZastitneUzadiJson.put(spojnaTockaZastitnogUzeta.getAsJson());
			}
			
			return spojneTockeZastitneUzadiJson;
		}
		
		// metoda containsKey sucelja Map objekte ne uspoređuje s equals; potrebno je ručno implementirati
		private static SpojnaTocka getKeyIfExists(Map<SpojnaTocka, List<Izolator>> parovi, SpojnaTocka st) {
			for(Entry<SpojnaTocka, List<Izolator>> par : parovi.entrySet()) {
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

	public boolean isZatezniStup() {
		return isZatezniStup;
	}

	public void setZatezniStup(boolean isZatezniStup) {
		this.isZatezniStup = isZatezniStup;
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
	
	public List<SpojnaTockaZastitnogUzeta> getSpojneTockeZastitneUzadi() {
		return spojneTockeZastitneUzadi;
	}

	public void setSpojneTockeZastitneUzadi(List<SpojnaTockaZastitnogUzeta> spojneTockeZastitneUzadi) {
		this.spojneTockeZastitneUzadi = spojneTockeZastitneUzadi;
	}

	public JSONObject getJson() {
		JSONObject stupJson = new JSONObject();
		
		stupJson.put("idStupa", this.idStupa);
		stupJson.put("oblikGlaveStupa", "Bacva");
		stupJson.put("isZatezniStup", this.isZatezniStup);
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
				Util.getSpojneTockeZastitneUzadiAsJsonArray(this.spojneTockeZastitneUzadi));
		
		return stupJson;
	}
	
	/**
	 * Transformiraju se one spojne točke koje su udaljene od centralne osi stupa
	 * Spojne točke koje su na centralnoj osi (imaju X = 0) nije potrebno mijenjati
	 * Koordinate X spojnih točaka izolatora poprimaju vrijednosti RAZMAK * i (..., -6, -4, -2, 2, 4, 6, ...)
	 * Koordinate X spojnih točaka zaštitne užadi poprimaju vrijednosti RAZMAK * i + 1 (..., -5, -3, -1, 1, 3, 5, ...)
	 */
	public Stup separateST(Stup stup) {
		// transformacija spojnih točaka izolatora
		List<Izolator> izolatori = stup.getIzolatori();
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		List<Izolator> desniIzolatori = new LinkedList<>();
		List<Izolator> lijeviIzolatori = new LinkedList<>();
		
		// raspodjela izolatora lijevo i desno
		// centralni izolator (X = 0) ostaje nepromijenjen (ako postoji)
		for(Izolator izolator : izolatori) {
			if(izolator.getSpojnaTockaIzolatoraX() > 0) { // izolator je s jedne strane
				desniIzolatori.add(izolator);
			} else if(izolator.getSpojnaTockaIzolatoraX() < 0) { // izolator je s druge strane
				lijeviIzolatori.add(izolator);
			} else { // izolator je na centralnoj osi - ne mijenja se
				azuriraniIzolatori.add(izolator);
			}
		}
		
		if(desniIzolatori.size() > 0) {
			List<Izolator> azuriraniIzolatoriDesno = getAdjustedIzolatori(desniIzolatori, true);
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriDesno);
		}
		
		if(lijeviIzolatori.size() > 0) {
			List<Izolator> azuriraniIzolatoriLijevo = getAdjustedIzolatori(lijeviIzolatori, false);
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriLijevo);
		}
		
		stup.setIzolatori(azuriraniIzolatori);
		
		// transformacija spojnih točaka zaštitne užadi
		List<SpojnaTockaZastitnogUzeta> spojneTockeZu = stup.getSpojneTockeZastitneUzadi();
		List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZu = new LinkedList<>();
		List<SpojnaTockaZastitnogUzeta> desneSpojneTockeZu = new LinkedList<>();
		List<SpojnaTockaZastitnogUzeta> lijeveSpojneTockeZu = new LinkedList<>();
		
		// raspodjela spojnih točaka zaštitne užadi lijevo i desno
		// centralna spojna točka ZU (X = 0) ostaje nepromijenjena (ako postoji)
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
			if(spojnaTockaZu.getSpojnaTockaZastitnogUzetaX() > 0) { // spojna točka je s jedne strane
				desneSpojneTockeZu.add(spojnaTockaZu);
			} else if(spojnaTockaZu.getSpojnaTockaZastitnogUzetaX() < 0) { // spojna točka je s druge strane
				lijeveSpojneTockeZu.add(spojnaTockaZu);
			} else { // spojna točka je na centralnoj osi - ne mijenja se
				azuriraneSpojneTockeZu.add(spojnaTockaZu);
			}
		}
		
		if(desneSpojneTockeZu.size() > 0) {
			List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZuDesno = getAdjustedSpojneTockeZu(desneSpojneTockeZu, true);
		
			azuriraneSpojneTockeZu.addAll(azuriraneSpojneTockeZuDesno);
		}
		
		
		if(lijeveSpojneTockeZu.size() > 0) {
			List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZuLijevo = getAdjustedSpojneTockeZu(lijeveSpojneTockeZu, false);
			
			azuriraneSpojneTockeZu.addAll(azuriraneSpojneTockeZuLijevo);
		}
				
		stup.setSpojneTockeZastitneUzadi(azuriraneSpojneTockeZu);
		
		return stup;
	}
	
	/**
	 * U obzir se uzima orijentacija stupa (kut kojeg ravnina konzole zatvara s x osi)
	 * Za spojne točke vodiča potrebno je uzeti u obzir još i kut kojeg ravnina izolatora zatvara s ravninom konzole
	 */
	public Stup changeOrientation(Stup stup) {
		double alfa = Math.toRadians(stup.getOrijentacija());
		
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		
		for(Izolator izolator : stup.getIzolatori()) {
			double xSti = izolator.getSpojnaTockaIzolatoraX();
			double zSti = izolator.getSpojnaTockaIzolatoraZ();
			double zStv = izolator.getSpojnaTockaVodicaZ();
			
			// izracun X STV
			double beta = Math.toRadians(Math.abs(izolator.getKutIzmedjuSpojneTockeVodicaIRavnineKonzole()));
			double d = Math.abs(zStv) * Math.sin(Math.toRadians(90) - beta) / Math.sin(beta);
			double xStv = xSti - d;
			
			// prilagodba STI (rotacija koordinatnog sustava za orijentaciju stupa)
			double azuriraniXSti = xSti * Math.cos(alfa) - zSti * Math.sin(alfa);
			double azuriraniZSti = xSti * Math.sin(alfa) + zSti * Math.cos(alfa);
			
			// prilagodba STV (rotacija koordinatnog sustava za orijentaciju stupa)
			double azuriraniXStv = xStv * Math.cos(alfa) - zStv * Math.sin(alfa);
			double azuriraniZStv = xStv * Math.sin(alfa) + zStv * Math.cos(alfa);
			
			izolator.setSpojnaTockaIzolatoraX(azuriraniXSti);
			izolator.setSpojnaTockaIzolatoraZ(azuriraniZSti);
			izolator.setSpojnaTockaVodicaX(azuriraniXStv);
			izolator.setSpojnaTockaVodicaZ(azuriraniZStv);
			
			azuriraniIzolatori.add(izolator);
		}
		
		stup.setIzolatori(azuriraniIzolatori);
		
		// prilagodba STZU
		List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZu = new LinkedList<>();
		
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : stup.getSpojneTockeZastitneUzadi()) {
			double xStzu = spojnaTockaZu.getSpojnaTockaZastitnogUzetaX();
			double zStzu = spojnaTockaZu.getSpojnaTockaZastitnogUzetaZ();
			
			double azuriraniXStzu = xStzu * Math.cos(alfa) - zStzu * Math.sin(alfa);
			double azuriraniZStzu = xStzu * Math.sin(alfa) + zStzu * Math.cos(alfa);
			
			spojnaTockaZu.setSpojnaTockaZastitnogUzetaX(azuriraniXStzu);
			spojnaTockaZu.setSpojnaTockaZastitnogUzetaZ(azuriraniZStzu);
			
			azuriraneSpojneTockeZu.add(spojnaTockaZu);
		}
		
		stup.setSpojneTockeZastitneUzadi(azuriraneSpojneTockeZu);
		
		return stup;
	}
	
	/**
	 * Koordinate položaja stupa izvorno su u WGS84 sustavu, a koordinate spojnih točaka su u Kartezijevom sustavu u kojem je stup u ishodištu
	 * Koordinate položaja spojnih točaka dobivaju se na sljedeći način:
	 * - Položaj stupa transformira se u UTM sustav
	 * - Položaj spojne točke računa se kao (x = xStupa, z = zStupa) gdje je xStupa vrijednost easting udaljenosti stupa u UTM sustavu, a zStupa vrijednost je northing udaljenosti stupa u UTM sustavu
	 * - Dobiveni položaj spojne točke u UTM sustavu transformira se natrag u WGS84 sustav
	 */
	public Stup convertUtmToWgs(Stup stup) {
		try {
			double geoSirinaStupa = stup.getGeoSirina();
			double geoDuzinaStupa = stup.getGeoDuzina();
			
			UtmCoordinate stupUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(geoSirinaStupa, geoDuzinaStupa));
			
			List<Izolator> izolatori = stup.getIzolatori();
			
			for(Izolator izolator : izolatori) {
				double spojnaTockaIzolatoraX = izolator.getSpojnaTockaIzolatoraX();
				double spojnaTockaIzolatoraZ = izolator.getSpojnaTockaIzolatoraZ();
					
				UtmCoordinate spojnaTockaIzolatoraUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(), 
						stupUtm.getEasting() + spojnaTockaIzolatoraX, 
						stupUtm.getNorthing() + spojnaTockaIzolatoraZ);
					
				WgsCoordinate spojnaTockaIzolatoraWgs = UtmWgsConverter.convertToWgs(spojnaTockaIzolatoraUtm);
					
				izolator.setSpojnaTockaIzolatoraGeoSirina(spojnaTockaIzolatoraWgs.getGeoSirina());
				izolator.setSpojnaTockaIzolatoraGeoDuzina(spojnaTockaIzolatoraWgs.getGeoDuzina());
				
				double spojnaTockaVodicaX = izolator.getSpojnaTockaVodicaX();
				double spojnaTockaVodicaZ = izolator.getSpojnaTockaVodicaZ();
					
				UtmCoordinate spojnaTockaVodicaUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(),
						stupUtm.getEasting() + spojnaTockaVodicaX,
						stupUtm.getNorthing() + spojnaTockaVodicaZ);
					
				WgsCoordinate spojnaTockaVodicaWgs = UtmWgsConverter.convertToWgs(spojnaTockaVodicaUtm);
					
				izolator.setSpojnaTockaVodicaGeoSirina(spojnaTockaVodicaWgs.getGeoSirina());
				izolator.setSpojnaTockaVodicaGeoDuzina(spojnaTockaVodicaWgs.getGeoDuzina());
				
			}
			
			List<SpojnaTockaZastitnogUzeta> spojneTockeZu = stup.getSpojneTockeZastitneUzadi();
			
			for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
				double spojnaTockaZuX = spojnaTockaZu.getSpojnaTockaZastitnogUzetaX();
				double spojnaTockaZuZ = spojnaTockaZu.getSpojnaTockaZastitnogUzetaZ();
					
				UtmCoordinate spojnaTockaZuUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(), 
						stupUtm.getEasting() + spojnaTockaZuX, 
						stupUtm.getNorthing() + spojnaTockaZuZ);
					
				WgsCoordinate spojnaTockaZastitnogUzetaWgs = 
						UtmWgsConverter.convertToWgs(spojnaTockaZuUtm);
					
				spojnaTockaZu.setSpojnaTockaZastitnogUzetaGeoSirina(spojnaTockaZastitnogUzetaWgs.getGeoSirina());
				spojnaTockaZu.setSpojnaTockaZastitnogUzetaGeoDuzina(spojnaTockaZastitnogUzetaWgs.getGeoDuzina());
				
			}
		} catch (Exception e) {
			System.out.println("Neuspješna konverzija!");
		}
		
		return stup;
	}
	
	private List<Izolator> getAdjustedIzolatori(List<Izolator> izolatori, boolean isDesno) {
		// nosivi stup tipa bacva i dunav: 6 izolatora sa svake strane -> 3 para jednakih STI
		// nosivi stup tipa jela: 2 izolatora s jedna i 4 s druge strane
		// zatezni stupovi nemaju parove jednakih STI
		// grupiranje parova
		Map<SpojnaTocka, List<Izolator>> parovi = new LinkedHashMap<>();
					
		for(Izolator izolator : izolatori) {
			SpojnaTocka st = new SpojnaTocka(izolator.getSpojnaTockaIzolatoraX(), 
					izolator.getSpojnaTockaIzolatoraY(), izolator.getSpojnaTockaIzolatoraZ());
					SpojnaTocka kljuc = Util.getKeyIfExists(parovi, st);
						
			if(kljuc != null) {
				List<Izolator> par = parovi.get(kljuc);
				par.add(izolator);
							
				parovi.put(kljuc, par);
			} else {
				parovi.put(st, new LinkedList<>(Arrays.asList(izolator)));
			}
		}
					
		// sortirati parove prema padajućoj vrijednosti koordinate Y STI
		Stream<Entry<SpojnaTocka, List<Izolator>>> streamParova = parovi.entrySet()
				.stream().sorted(Collections.reverseOrder(Entry.comparingByKey(new Comparator<SpojnaTocka>() {

					@Override
					public int compare(SpojnaTocka st1, SpojnaTocka st2) {
						return Double.compare(st1.getY(), st1.getY());
					}
								
				})));
					
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		Iterator<Entry<SpojnaTocka, List<Izolator>>> streamParovaIterator = streamParova.iterator();
					
		int i = 1;
		int predznak = isDesno ? 1 : -1; // desna strana ima predznak +, a lijeva -
					
		// koordinata X = RAZMAK * i * predznak
		while(streamParovaIterator.hasNext()) {
			List<Izolator> par = streamParovaIterator.next().getValue();
						
			for(Izolator izolator : par) {
				double noviX = RAZMAK * i * predznak;
				izolator.setSpojnaTockaIzolatoraX(noviX);
				izolator.setSpojnaTockaVodicaX(noviX);
			}
						
			azuriraniIzolatori.addAll(par);
						
			i += 1;
		}
		
		return azuriraniIzolatori;
	}
	
	private List<SpojnaTockaZastitnogUzeta> getAdjustedSpojneTockeZu(List<SpojnaTockaZastitnogUzeta> spojneTockeZu, 
			boolean isDesno) {
		// sortirati spojne točke zaštitne užadi prema rastućoj koordinati X
		spojneTockeZu.sort(new Comparator<SpojnaTockaZastitnogUzeta>() {

			@Override
			public int compare(SpojnaTockaZastitnogUzeta stZu1, SpojnaTockaZastitnogUzeta stZu2) {
				return isDesno ? 
						Double.compare(stZu1.getSpojnaTockaZastitnogUzetaX(), stZu2.getSpojnaTockaZastitnogUzetaX()) : 
						Double.compare(stZu2.getSpojnaTockaZastitnogUzetaX(), stZu1.getSpojnaTockaZastitnogUzetaX());
			}
						
		});
					
		int i = 0;
		int predznak = isDesno ? 1 : -1;
					
		for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
			// koordinata X = RAZMAK * i + 1 (desna strana)
			// koordinata X = (RAZMAK * i + 1) * -1 (lijeva strana)
			spojnaTockaZu.setSpojnaTockaZastitnogUzetaX((RAZMAK * i + 1) * predznak);
						
			i += 1;
		}
		
		return spojneTockeZu;
	}
	
	public abstract TipStupa getType();

}
