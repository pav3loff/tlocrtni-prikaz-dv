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

public class BacvaStup implements IStup {
	
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
	
	public BacvaStup() {
		super();
	}
	
	public BacvaStup(int idStupa, boolean isZatezniStup, double orijentacija, double geoSirina, double geoDuzina,
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
	
	public BacvaStup(JSONObject bacvaStupJson) {
		if(!(bacvaStupJson.isNull("idStupa"))) {
			this.setIdStupa(bacvaStupJson.getInt("idStupa"));
		}
		
		if(!(bacvaStupJson.isNull("isZatezniStup"))) {
			this.setZatezniStup(bacvaStupJson.getBoolean("isZatezniStup"));
		}
		
		if(!(bacvaStupJson.isNull("orijentacija"))) {
			this.setOrijentacija(bacvaStupJson.getDouble("orijentacija"));
		}
		
		if(!(bacvaStupJson.isNull("geoSirina"))) {
			this.setGeoSirina(bacvaStupJson.getDouble("geoSirina"));
		}
		
		if(!(bacvaStupJson.isNull("geoDuzina"))) {
			this.setGeoDuzina(bacvaStupJson.getDouble("geoDuzina"));
		}
		
		if(!(bacvaStupJson.isNull("visina"))) {
			this.setVisina(bacvaStupJson.getDouble("visina"));
		}
		
		if(!(bacvaStupJson.isNull("tipStupa"))) {
			this.setTipStupa(bacvaStupJson.getString("tipStupa"));
		}
		
		if(!(bacvaStupJson.isNull("proizvodac"))) {
			this.setProizvodac(bacvaStupJson.getString("proizvodac"));
		}
		
		if(!(bacvaStupJson.isNull("tezina"))) {
			this.setTezina(bacvaStupJson.getDouble("tezina"));
		}
		
		if(!(bacvaStupJson.isNull("oznakaUzemljenja"))) {
			this.setOznakaUzemljenja(bacvaStupJson.getString("oznakaUzemljenja"));
		}
		
		if(!(bacvaStupJson.isNull("vrstaZastite"))) {
			this.setVrstaZastite(bacvaStupJson.getString("vrstaZastite"));
		}
		
		if(!(bacvaStupJson.isNull("izolatori"))) {
			JSONArray izolatoriJson = bacvaStupJson.getJSONArray("izolatori");
			
			this.izolatori = new LinkedList<Izolator>();
			
			for(int i = 0; i < izolatoriJson.length(); i++) {
				JSONObject izolatorJson = izolatoriJson.getJSONObject(i);
				
				this.izolatori.add(new Izolator(izolatorJson));
			}
		}
	}
	
	private static class Util {
		// metoda containsKey sucelja Map objekte ne uspoređuje s equals; potrebno je ručno implementirati
		private static SpojnaTocka getKeyIfExists(Map<SpojnaTocka, List<Izolator>> parovi, SpojnaTocka st) {
			for(Entry<SpojnaTocka, List<Izolator>> par : parovi.entrySet()) {
				if(par.getKey().equals(st)) {
					return par.getKey();
				}
			}
			
			return null;
		}
		
		private static JSONArray getAsJsonArray(List<Izolator> izolatori) {
			JSONArray izolatoriJson = new JSONArray();
			
			for(Izolator izolator : izolatori) {
				izolatoriJson.put(izolator.getJson());
			}
			
			return izolatoriJson;
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

	@Override
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

	@Override
	public BacvaStup separateST(IStup stup) {
		List<Izolator> izolatori = ((BacvaStup) stup).getIzolatori();
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		List<Izolator> desniIzolatori = new LinkedList<>();
		List<Izolator> lijeviIzolatori = new LinkedList<>();
		
		// raspodjela izolatora lijevo i desno
		for(Izolator izolator : izolatori) {
			if(izolator.isDesno()) {
				desniIzolatori.add(izolator);
			} else {
				lijeviIzolatori.add(izolator);
			}
		}
		
		if(desniIzolatori.size() > 0) {
			// stup tipa bacva: 6 izolatora sa svake strane -> 3 para jednakih STI
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
		
		return new BacvaStup(this.idStupa, this.isZatezniStup, this.orijentacija, this.geoSirina, 
				this.geoDuzina, this.visina, this.tipStupa,this.proizvodac, this.tezina, 
				this.oznakaUzemljenja, this.vrstaZastite, azuriraniIzolatori);
	}

	@Override
	public IStup changeOrientation(IStup stup) {
		BacvaStup bacvaStup = (BacvaStup) stup;
		double alfa = Math.toRadians(bacvaStup.getOrijentacija());
		
		// ST s desne strane imaju kut alfa (negativan); ST s lijeve strane imaju kut -180 + alfa
		List<Izolator> azuriraniIzolatori = new LinkedList<>();
		
		for(Izolator izolator : bacvaStup.getIzolatori()) {
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
		
		bacvaStup.setIzolatori(azuriraniIzolatori);
		
		return bacvaStup;
	}

}
