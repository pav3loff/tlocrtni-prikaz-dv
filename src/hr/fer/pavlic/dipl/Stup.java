package hr.fer.pavlic.dipl;

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

public abstract class Stup {
	
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
	
	public Stup() {
		super();
	}
	
	public Stup(int idStupa, boolean isZatezniStup, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori) {
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
	}
	
	private static class Util {
		private static JSONArray getAsJsonArray(List<Izolator> izolatori) {
			JSONArray izolatoriJson = new JSONArray();
			
			for(Izolator izolator : izolatori) {
				izolatoriJson.put(izolator.getJson());
			}
			
			return izolatoriJson;
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
	
	public JSONObject getJson() {
		JSONObject bacvaStupJson = new JSONObject();
		
		bacvaStupJson.put("idStupa", this.idStupa);
		bacvaStupJson.put("oblikGlaveStupa", "Bacva");
		bacvaStupJson.put("isZatezniStup", this.isZatezniStup);
		bacvaStupJson.put("orijentacija", this.orijentacija);
		bacvaStupJson.put("geoSirina", this.geoSirina);
		bacvaStupJson.put("geoDuzina", this.geoDuzina);
		bacvaStupJson.put("visina", this.visina);
		bacvaStupJson.put("tipStupa", this.tipStupa);
		bacvaStupJson.put("proizvodac", this.proizvodac);
		bacvaStupJson.put("tezina", this.tezina);
		bacvaStupJson.put("oznakaUzemljenja", this.oznakaUzemljenja);
		bacvaStupJson.put("vrstaZastite", this.vrstaZastite);
		bacvaStupJson.put("izolatori", Util.getAsJsonArray(this.izolatori));
		
		return bacvaStupJson;
	}
	
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
			double azuriraniXSti = xSti * Math.cos(alfa) + zSti * Math.sin(alfa);
			double azuriraniZSti = xSti * Math.sin(alfa) + zSti * Math.cos(alfa);
			
			// prilagodba STV (rotacija koordinatnog sustava za orijentaciju stupa)
			double azuriraniXStv = xStv * Math.cos(alfa) - zStv * Math.sin(alfa);
			double azuriraniZStv = xStv * Math.sin(alfa) + zStv * Math.cos(alfa);
			
			izolator.setSpojnaTockaIzolatoraX(Math.round(azuriraniXSti * 100.0) / 100.0);
			izolator.setSpojnaTockaIzolatoraZ(Math.round(azuriraniZSti * 100.0) / 100.0);
			izolator.setSpojnaTockaVodicaX(Math.round(azuriraniXStv * 100.0) / 100.0);
			izolator.setSpojnaTockaVodicaZ(Math.round(azuriraniZStv * 100.0) / 100.0);
			
			azuriraniIzolatori.add(izolator);
		}
		
		stup.setIzolatori(azuriraniIzolatori);
		
		return stup;
	}
	
	// Transformacija za izbjegavanje preklapanja spojnih točaka jednaka je za tipove stupa BACVA, JELA i DUNAV
	public Stup separateST(Stup stup) {
		List<Izolator> izolatori = stup.getIzolatori();
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		List<Izolator> desniIzolatori = new LinkedList<>();
		List<Izolator> lijeviIzolatori = new LinkedList<>();
		
		// raspodjela izolatora lijevo i desno
		for(Izolator izolator : izolatori) {
			if(izolator.getSpojnaTockaIzolatoraX() > 0) { // izolator je s jedne strane
				desniIzolatori.add(izolator);
			} else { // izolator je s druge strane
				lijeviIzolatori.add(izolator);
			}
		}
		
		if(desniIzolatori.size() > 0) {
			// nosivi stup tipa bacva i dunav: 6 izolatora sa svake strane -> 3 para jednakih STI
			// nosivi stup tipa jela: 2 izolatora s jedna i 4 s druge strane
			// zatezni stupovi nemaju parove jednakih STI
			// grupiranje parova
			Map<SpojnaTocka, List<Izolator>> parovi = new LinkedHashMap<>();
			
			for(Izolator izolator : desniIzolatori) {
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
			
			List<Izolator> azuriraniIzolatoriDesno = new LinkedList<>();
			Iterator<Entry<SpojnaTocka, List<Izolator>>> streamParovaIterator = streamParova.iterator();
			
			// prvi par u sortiranom nizu ostaje nepromijenjen i određuje razmak
			List<Izolator> par = streamParovaIterator.next().getValue();
			azuriraniIzolatoriDesno.addAll(par);
			
			double razmak = azuriraniIzolatoriDesno.get(0).getSpojnaTockaIzolatoraX();
			int i = 2;
			
			// koordinata X ostalih STI = koordinata najblize STI * i
			while(streamParovaIterator.hasNext()) {
				par = streamParovaIterator.next().getValue();
				
				for(Izolator izolator : par) {
					double noviX = (double) Math.round(razmak * i * 100.0) / 100.0;
					izolator.setSpojnaTockaIzolatoraX(noviX);
					izolator.setSpojnaTockaVodicaX(noviX);
				}
				
				azuriraniIzolatoriDesno.addAll(par);
				
				i += 1;
			}
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriDesno);
		}
		
		if(lijeviIzolatori.size() > 0) {
			// stup tipa bacva: 6 izolatora sa svake strane -> 3 para jednakih STI
			// grupiranje parova
			Map<SpojnaTocka, List<Izolator>> parovi = new LinkedHashMap<>();
			
			for(Izolator izolator : lijeviIzolatori) {
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
			
			List<Izolator> azuriraniIzolatoriLijevo = new LinkedList<>();
			Iterator<Entry<SpojnaTocka, List<Izolator>>> streamParovaIterator = streamParova.iterator();
			
			// prvi par u sortiranom nizu ostaje nepromijenjen i određuje razmak
			List<Izolator> par = streamParovaIterator.next().getValue();
			azuriraniIzolatoriLijevo.addAll(par);
			
			double razmak = azuriraniIzolatoriLijevo.get(0).getSpojnaTockaIzolatoraX();
			int i = 2;
			
			// koordinata X ostalih STI = koordinata najblize STI * i
			while(streamParovaIterator.hasNext()) {
				par = streamParovaIterator.next().getValue();
				
				for(Izolator izolator : par) {
					double noviX = (double) Math.round(razmak * i * 100.0) / 100.0;
					izolator.setSpojnaTockaIzolatoraX(noviX);
					izolator.setSpojnaTockaVodicaX(noviX);
				}
				
				azuriraniIzolatoriLijevo.addAll(par);
				
				i += 1;
			}
			
			azuriraniIzolatori.addAll(azuriraniIzolatoriLijevo);
		}
		
		stup.setIzolatori(azuriraniIzolatori);
		
		return stup;
	}	
	
	public JSONObject convertUtmToWgs(JSONObject stupJson) {
		double geoSirinaStupa = stupJson.getDouble("geoSirina");
		double geoDuzinaStupa = stupJson.getDouble("geoDuzina");
		
		try {
			UtmCoordinate stupUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(geoSirinaStupa, geoDuzinaStupa));
			
			JSONArray izolatoriJson = stupJson.getJSONArray("izolatori");
			
			for(int i = 0; i < izolatoriJson.length(); i++) {
				JSONObject izolator = izolatoriJson.getJSONObject(i);
				
				if(!(izolator.isNull("spojnaTockaIzolatoraX")) && 
						!(izolator.isNull("spojnaTockaIzolatoraZ"))) {
					double spojnaTockaIzolatoraX = izolator.getDouble("spojnaTockaIzolatoraX");
					double spojnaTockaIzolatoraZ = izolator.getDouble("spojnaTockaIzolatoraZ");
					
					UtmCoordinate spojnaTockaIzolatoraUtm = new UtmCoordinate(
							stupUtm.getLongZone(), stupUtm.getLatZone(), 
							stupUtm.getEasting() + spojnaTockaIzolatoraX, 
							stupUtm.getNorthing() + spojnaTockaIzolatoraZ);
					
					WgsCoordinate spojnaTockaIzolatoraWgs = UtmWgsConverter.convertToWgs(spojnaTockaIzolatoraUtm);
					
					izolator.put("spojnaTockaIzolatoraGeoSirina", spojnaTockaIzolatoraWgs.getGeoSirina());
					izolator.put("spojnaTockaIzolatoraGeoDuzina", spojnaTockaIzolatoraWgs.getGeoDuzina());
				}
				
				if(!(izolator.isNull("spojnaTockaVodicaX")) &&
						!(izolator.isNull("spojnaTockaVodicaZ"))) {
					double spojnaTockaVodicaX = izolator.getDouble("spojnaTockaVodicaX");
					double spojnaTockaVodicaZ = izolator.getDouble("spojnaTockaVodicaZ");
					
					UtmCoordinate spojnaTockaVodicaUtm = new UtmCoordinate(
							stupUtm.getLongZone(), stupUtm.getLatZone(),
							stupUtm.getEasting() + spojnaTockaVodicaX,
							stupUtm.getNorthing() + spojnaTockaVodicaZ);
					
					WgsCoordinate spojnaTockaVodicaWgs = UtmWgsConverter.convertToWgs(spojnaTockaVodicaUtm);
					
					izolator.put("spojnaTockaVodicaGeoSirina", spojnaTockaVodicaWgs.getGeoSirina());
					izolator.put("spojnaTockaVodicaGeoDuzina", spojnaTockaVodicaWgs.getGeoDuzina());
				}
			}
		} catch (Exception e) {
			System.out.println("Neuspješna konverzija!");
		}
		
		return stupJson;
	}
	
	public abstract TipStupa getType();

}
