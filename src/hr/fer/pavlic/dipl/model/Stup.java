package hr.fer.pavlic.dipl.model;

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
	private final static int DIM_KONZ_X = 2;
	private final static int DIM_KONZ_Y = 1;
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
	private List<SpojnaTocka> spojneTockeZu;
	private Konzola konzola;
	
	public Stup() {
		super();
	}
	
	public Stup(int idStupa, boolean isZatezni, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori, List<SpojnaTocka> spojneTockeZu) {
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
			
			this.spojneTockeZu = new LinkedList<SpojnaTocka>();
			
			for(int i = 0; i < spojneTockeZuJson.length(); i++) {
				JSONObject spojnaTockaZuJson = spojneTockeZuJson.getJSONObject(i);
				
				this.spojneTockeZu.add(new SpojnaTocka(spojnaTockaZuJson));
			}
		}
	}
	
	private static class Util {
		private static JSONArray getSpojneTockeZuAsJsonArray(
				List<SpojnaTocka> spojneTockeZu) {
			JSONArray spojneTockeZuJson = new JSONArray();
			
			for(SpojnaTocka spojnaTockaZu : spojneTockeZu) {
				spojneTockeZuJson.put(spojnaTockaZu.getJson());
			}
			
			return spojneTockeZuJson;
		}
		
		// metoda containsKey sucelja Map objekte ne uspoređuje s equals; potrebno je ru�?no implementirati
		private static SpojnaTocka getKeyIfExists(Map<SpojnaTocka, List<Izolator>> parovi, SpojnaTocka sti) {
			for(Entry<SpojnaTocka, List<Izolator>> par : parovi.entrySet()) {
				if(par.getKey().equals(sti)) {
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
	
	public List<SpojnaTocka> getSpojneTockeZu() {
		return spojneTockeZu;
	}

	public void setSpojneTockeZu(List<SpojnaTocka> spojneTockeZu) {
		this.spojneTockeZu = spojneTockeZu;
	}

	public Konzola getKonzola() {
		return konzola;
	}

	public void setKonzola(Konzola konzola) {
		this.konzola = konzola;
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
		stupJson.put("spojneTockeZastitneUzadi", 
				Util.getSpojneTockeZuAsJsonArray(this.spojneTockeZu));
		
		return stupJson;
	}
	
	public void getAsOsmXmlElement(Element parent) {
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
			izolator.getAsOsmXmlElement(parent, this.isZatezni);
		}
		
		for(SpojnaTocka stzu : this.spojneTockeZu) {
			stzu.getAsOsmXmlElement(parent, this.isZatezni);
		}
		
		this.konzola.getAsOsmXmlElement(parent);
	}
	
	/**
	 * Transformiraju se one STI i STZU koje su udaljene od centralne osi stupa
	 * STI i STZU koje su na centralnoj osi (imaju X = 0) nije potrebno mijenjati
	 * Koordinate X STI poprimaju vrijednosti RAZMAK * i (..., -6, -4, -2, 2, 4, 6, ...)
	 * Koordinate X STZU poprimaju vrijednosti RAZMAK * i + 1 (..., -5, -3, -1, 1, 3, 5, ...)
	 */
	public void separateSti() {
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		List<Izolator> desniIzolatori = new LinkedList<>();
		List<Izolator> lijeviIzolatori = new LinkedList<>();
		
		// raspodjela izolatora lijevo i desno
		// centralni izolator (X = 0) ostaje nepromijenjen (ako postoji)
		for(Izolator izolator : this.izolatori) {
			if(izolator.getSti().getX() > 0) { // izolator je s jedne strane
				desniIzolatori.add(izolator);
			} else if(izolator.getSti().getX() < 0) { // izolator je s druge strane
				lijeviIzolatori.add(izolator);
			} else { // izolator je na centralnoj osi - ne mijenja se
				azuriraniIzolatori.add(izolator);
			}
		}
		
		if(desniIzolatori.size() > 0) {
			List<Izolator> azuriraniIzolatoriDesno = getSeparatedSti(desniIzolatori, true);
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriDesno);
		}
		
		if(lijeviIzolatori.size() > 0) {
			List<Izolator> azuriraniIzolatoriLijevo = getSeparatedSti(lijeviIzolatori, false);
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriLijevo);
		}
		
		this.izolatori = azuriraniIzolatori;
	}
	
	public void separateStzu() {
		List<SpojnaTocka> spojneTockeZu = this.spojneTockeZu;
		List<SpojnaTocka> azuriraneSpojneTockeZu = new LinkedList<>();
		List<SpojnaTocka> desneSpojneTockeZu = new LinkedList<>();
		List<SpojnaTocka> lijeveSpojneTockeZu = new LinkedList<>();
				
		// raspodjela STZU užadi lijevo i desno
		// centralna STZU (X = 0) ostaje nepromijenjena (ako postoji)
		for(SpojnaTocka spojnaTockaZu : spojneTockeZu) {
			if(spojnaTockaZu.getX() > 0) { // STZU je s jedne strane
				desneSpojneTockeZu.add(spojnaTockaZu);
			} else if(spojnaTockaZu.getX() < 0) { // STZU je s druge strane
				lijeveSpojneTockeZu.add(spojnaTockaZu);
			} else { // STZU je na centralnoj osi - ne mijenja se
				azuriraneSpojneTockeZu.add(spojnaTockaZu);
			}
		}
				
		if(desneSpojneTockeZu.size() > 0) {
			List<SpojnaTocka> azuriraneSpojneTockeZuDesno = getSeparatedStzu(desneSpojneTockeZu, true);
				
			azuriraneSpojneTockeZu.addAll(azuriraneSpojneTockeZuDesno);
		}
				
				
		if(lijeveSpojneTockeZu.size() > 0) {
			List<SpojnaTocka> azuriraneSpojneTockeZuLijevo = getSeparatedStzu(lijeveSpojneTockeZu, false);
					
			azuriraneSpojneTockeZu.addAll(azuriraneSpojneTockeZuLijevo);
		}
						
		this.spojneTockeZu = azuriraneSpojneTockeZu;
	}
	
	/**
	 * Ažurira vrijednosti (x, y, z) STI, zbog utjecaja orijentacije stupa (ravnina konzole nije uvijek paralelna s ekvatorom)
	 */
	private void adjustStiByOrijentacijaStupa() {
		double alfa = Math.toRadians(this.orijentacija);
		
		for(Izolator izolator : this.izolatori) {
			double xSti = izolator.getSti().getX();
			double zSti = izolator.getSti().getZ();
			
			// prilagodba STI (rotacija koordinatnog sustava za orijentaciju stupa)
			double azuriraniXSti = xSti * Math.cos(alfa) - zSti * Math.sin(alfa);
			double azuriraniZSti = xSti * Math.sin(alfa) + zSti * Math.cos(alfa);
			
			izolator.getSti().setX(Math.round(azuriraniXSti * 100.0) / 100.0);
			izolator.getSti().setZ(Math.round(azuriraniZSti * 100.0) / 100.0);
		}
	}
	
	/**
	 * Ažurira vrijednosti (x, y, z) STZU, zbog utjecaja orijentacije stupa (ravnina konzole nije uvijek paralelna s ekvatorom)
	 */
	public void adjustStzuByOrijentacijaStupa() {		
		double alfa = Math.toRadians(this.orijentacija);
		
		for(SpojnaTocka spojnaTockaZu : this.spojneTockeZu) {
			double x = spojnaTockaZu.getX();
			double z = spojnaTockaZu.getZ();
			
			double azuriraniX = x * Math.cos(alfa) - z * Math.sin(alfa);
			double azuriraniZ = x * Math.sin(alfa) + z * Math.cos(alfa);
			
			spojnaTockaZu.setX(Math.round(azuriraniX * 100.0) / 100.0);
			spojnaTockaZu.setZ(Math.round(azuriraniZ * 100.0) / 100.0);
		}
	}
	
	/**
	 * Koordinate položaja stupa izvorno su u WGS84 sustavu, a koordinate STI su u Kartezijevom sustavu u kojem je stup u ishodištu
	 * Koordinate položaja STI dobivaju se na sljedeći na�?in:
	 * - Položaj stupa transformira se u UTM sustav
	 * - Položaj STI ra�?una se kao (x = x + xStupa, z = z + zStupa) gdje je xStupa vrijednost easting udaljenosti stupa u UTM sustavu, a zStupa vrijednost je northing udaljenosti stupa u UTM sustavu
	 * - Dobiveni položaj STI u UTM sustavu transformira se natrag u WGS84 sustav
	 */
	public void convertStiUtmToWgs() {
		try {
			UtmCoordinate stupUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(this.geoSirina, this.geoDuzina));
			
			// Ažuriranje položaja STI s obzirom na orijentaciju stupa izmijenit će koordinate (x, y, z) - njih je potrebno očuvati kako bi se kasnije lakše generirali vrhovi konzole
			List<Izolator> izolatoriSOcuvanimPolozajemSti = new LinkedList<>();
			for(Izolator izolator : this.izolatori) {
				izolatoriSOcuvanimPolozajemSti.add(new Izolator(izolator));
			}
			
			adjustStiByOrijentacijaStupa(); // Mijenja izolatore, ali u gornjem polju su izvorno očuvani
			
			for(int i = 0; i < this.izolatori.size(); i++) {
				UtmCoordinate stiUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(), 
						stupUtm.getEasting() + this.izolatori.get(i).getSti().getX(), 
						stupUtm.getNorthing() + this.izolatori.get(i).getSti().getZ());
					
				WgsCoordinate stiWgs = UtmWgsConverter.convertToWgs(stiUtm);
					
				this.izolatori.get(i).getSti().setGeoSirina(
						Math.round(stiWgs.getGeoSirina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				this.izolatori.get(i).getSti().setGeoDuzina(
						Math.round(stiWgs.getGeoDuzina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				
				// Lat i long STV se takoder odmah moze postaviti i jednaki su kao za STI (ukoliko na neki STV nije spojen vodic - STI, STV i izolator bit ce u istoj tocki)
				// Ako se vodic spaja na neku STV, sam ce ju kasnije prilagoditi
				this.izolatori.get(i).getStv().setGeoSirina(
						Math.round(stiWgs.getGeoSirina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				this.izolatori.get(i).getStv().setGeoDuzina(
						Math.round(stiWgs.getGeoDuzina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				
				// Nakon što su lat i long dobijeni, stare vrijednosti x, y, z potrebno je vratiti
				this.izolatori.get(i).getSti().setX(izolatoriSOcuvanimPolozajemSti.get(i).getSti().getX());
				this.izolatori.get(i).getSti().setY(izolatoriSOcuvanimPolozajemSti.get(i).getSti().getY());
				this.izolatori.get(i).getSti().setZ(izolatoriSOcuvanimPolozajemSti.get(i).getSti().getZ());
			}
		} catch (Exception e) {
			System.out.println("Neuspješna konverzija STI!");
		}
	}
	
	/**
	 * Koordinate položaja stupa izvorno su u WGS84 sustavu, a koordinate STI su u Kartezijevom sustavu u kojem je stup u ishodištu
	 * Koordinate položaja STZU dobivaju se na sljedeći na�?in:
	 * - Položaj stupa transformira se u UTM sustav
	 * - Položaj STZU ra�?una se kao (x = x + xStupa, z = z + zStupa) gdje je xStupa vrijednost easting udaljenosti stupa u UTM sustavu, a zStupa vrijednost je northing udaljenosti stupa u UTM sustavu
	 * - Dobiveni položaj STZU u UTM sustavu transformira se natrag u WGS84 sustav
	 */
	public void convertStzuUtmToWgs() {
		try {
			UtmCoordinate stupUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(this.geoSirina, this.geoDuzina));
			
			// Ažuriranje položaja STZU s obzirom na orijentaciju stupa izmijenit će koordinate (x, y, z) - njih je potrebno očuvati
			List<SpojnaTocka> stzuSOcuvanimPolozajem = new LinkedList<>();
			for(SpojnaTocka stzu : this.spojneTockeZu) {
				stzuSOcuvanimPolozajem.add(new SpojnaTocka(stzu));
			}
						
			adjustStzuByOrijentacijaStupa(); // Mijenja polje stzu, ali u polju iznad su polozaji ocuvani
			
			for(int i = 0; i < this.spojneTockeZu.size(); i++) {
				UtmCoordinate stzuUtm = new UtmCoordinate(
						stupUtm.getLongZone(), stupUtm.getLatZone(), 
						stupUtm.getEasting() + this.spojneTockeZu.get(i).getX(), 
						stupUtm.getNorthing() + this.spojneTockeZu.get(i).getZ());
					
				WgsCoordinate stzuWgs = 
						UtmWgsConverter.convertToWgs(stzuUtm);
					
				this.spojneTockeZu.get(i).setGeoSirina(
						Math.round(stzuWgs.getGeoSirina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				this.spojneTockeZu.get(i).setGeoDuzina(
						Math.round(stzuWgs.getGeoDuzina() * DECIMALNO_MJESTO) / DECIMALNO_MJESTO);
				
				// Nakon što su lat i long dobijeni, stare vrijednosti x, y, z potrebno je vratiti
				this.spojneTockeZu.get(i).setX(stzuSOcuvanimPolozajem.get(i).getX());
				this.spojneTockeZu.get(i).setY(stzuSOcuvanimPolozajem.get(i).getY());
				this.spojneTockeZu.get(i).setZ(stzuSOcuvanimPolozajem.get(i).getZ());
			}
		} catch (Exception e) {
			System.out.println("Neuspješna konverzija STZU!");
		}
	}
	
	/**
	 * Vraća izmijenjenu listu izolatora kojima su STI razdvojene (izbjegavanje preklapanja)
	 */
	private List<Izolator> getSeparatedSti(List<Izolator> izolatori, boolean isDesno) {
		// nosivi stup tipa bacva i dunav: 6 izolatora sa svake strane -> 3 para jednakih STI
		// nosivi stup tipa jela: 2 izolatora s jedna i 4 s druge strane
		// zatezni stupovi nemaju parove jednakih STI
		// grupiranje parova
		Map<SpojnaTocka, List<Izolator>> parovi = new LinkedHashMap<>();
					
		for(Izolator izolator : izolatori) {
			SpojnaTocka sti = new SpojnaTocka(izolator.getSti().getIdSt(), izolator.getSti().getX(), 
					izolator.getSti().getY(), izolator.getSti().getZ(), TipSpojneTocke.STI);
					SpojnaTocka kljuc = Util.getKeyIfExists(parovi, sti);
						
			if(kljuc != null) {
				List<Izolator> par = parovi.get(kljuc);
				par.add(izolator);
							
				parovi.put(kljuc, par);
			} else {
				parovi.put(sti, new LinkedList<>(Arrays.asList(izolator)));
			}
		}
					
		// sortirati parove prema padajućoj vrijednosti koordinate Y STI
		Stream<Entry<SpojnaTocka, List<Izolator>>> streamParova = parovi.entrySet()
				.stream().sorted(Collections.reverseOrder(Entry.comparingByKey(new Comparator<SpojnaTocka>() {

					@Override
					public int compare(SpojnaTocka sti1, SpojnaTocka sti2) {
						return Double.compare(sti1.getY(), sti1.getY());
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
				izolator.getSti().setX(noviX);
			}
						
			azuriraniIzolatori.addAll(par);
						
			i += 1;
		}
		
		return azuriraniIzolatori;
	}
	
	/**
	 * Vraća izmijenjenu listu STZU koje su razdvojene (izbjegavanje preklapanja)
	 */
	private List<SpojnaTocka> getSeparatedStzu(List<SpojnaTocka> spojneTockeZu, 
			boolean isDesno) {
		// sortirati STZU užadi prema rastućoj koordinati X
		spojneTockeZu.sort(new Comparator<SpojnaTocka>() {

			@Override
			public int compare(SpojnaTocka stZu1, SpojnaTocka stZu2) {
				return isDesno ? 
						Double.compare(stZu1.getX(), stZu2.getX()) : 
						Double.compare(stZu2.getX(), stZu1.getX());
			}
						
		});
					
		int i = 0;
		int predznak = isDesno ? 1 : -1;
					
		for(SpojnaTocka spojnaTockaZu : spojneTockeZu) {
			// koordinata X = RAZMAK * i + 1 (desna strana)
			// koordinata X = (RAZMAK * i + 1) * -1 (lijeva strana)
			spojnaTockaZu.setX((RAZMAK * i + 1) * predznak);
						
			i += 1;
		}
		
		return spojneTockeZu;
	}
	
	/**
	 * Generira oblik konzole stupa za prikaz u JOSM alatu
	 * Dva nasuprotna vrha četverokuta konzole koji su duž osi konzole dobivaju se pomoću STI koje su najudaljenije od cent. osi stupa
	 * Dva ostala nasuprotna vrha (duž okomice na os konzole) dobivaju se način (x=0, z=RAZMAK), (x=0, z=-RAZMAK)
	 * Bitan je redoslijed navođenja tih vrhova/točaka za uspješno generiranje geometrijskog oblika u OSM XML-u
	 * Redoslijed (nasuprotni vrhovi ne smiju biti susjedni u listi):
	 *   2
	 *  / \
	 * 1---3
	 *  \ /
	 *   4
	 */
	public void generateKonzola() {
		// pronalazak STI koje su najudaljenije od centr. osi stupa (sa svake strane po jedna)
		// Dva nasuprotna vrha duž osi konzole imaju koordinate (x=xMin, z=0) i (x=xMax, z=0)
		// Dva ostala nasuprotna vrha imaju koordinate (x=0, z=RAZMAK) i (x=0, z=-RAZMAK)
		double xMin = 0, xMax = 0;
		for(Izolator izolator : this.izolatori) {
			SpojnaTocka sti = izolator.getSti();
			
			if(sti.getX() < xMin) {
				xMin = sti.getX();
			}
			
			if(sti.getX() > xMax) {
				xMax = sti.getX();
			}
		}
				
		Tocka2D t1 = new Tocka2D(xMin - DIM_KONZ_X, 0);
		Tocka2D t2 = new Tocka2D(0, DIM_KONZ_Y);
		Tocka2D t3 = new Tocka2D(xMax + DIM_KONZ_X, 0);
		Tocka2D t4 = new Tocka2D(0, -1 * DIM_KONZ_Y);
		
		double kutRotacije = Math.toRadians(this.orijentacija);
				
		List<Tocka2D> tocke = Arrays.asList(t1, t2, t3, t4);
		for(Tocka2D tocka : tocke) {
			tocka.rotateTocka2D(kutRotacije);
		}
		
		Konzola konzola = new Konzola(tocke);
		konzola.updateKoordinateVrhovaKonzole(this.geoSirina, this.geoDuzina);
		
		this.konzola = konzola;
	}
	
	public abstract TipStupa getType();
	
	@Override
	public String toString() {
		return String.format("[STUP] ID: %d, LAT: %f, LONG: %f\n", this.idStupa, this.geoSirina, this.geoDuzina);
	}

}
