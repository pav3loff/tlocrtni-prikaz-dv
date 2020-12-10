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

import org.json.JSONObject;

public class BacvaStup extends Stup {
	
	public BacvaStup() {
		super();
	}
	
	public BacvaStup(int idStupa, boolean isZatezniStup, double orijentacija, double geoSirina, double geoDuzina,
			double visina, String tipStupa, String proizvodac, double tezina, String oznakaUzemljenja,
			String vrstaZastite, List<Izolator> izolatori) {
		super(idStupa, isZatezniStup, orijentacija, geoSirina, geoDuzina, visina, tipStupa, proizvodac, tezina,
				oznakaUzemljenja, vrstaZastite, izolatori);
	}
	
	public BacvaStup(JSONObject stupJson) {
		super(stupJson);
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
	}
	
	@Override
	public TipStupa getType() {
		return TipStupa.BACVA;
	}

	@Override
	public BacvaStup separateST(Stup stup) {
		List<Izolator> izolatori = ((BacvaStup) stup).getIzolatori();
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
		
		return new BacvaStup(this.getIdStupa(), this.isZatezniStup(), this.getOrijentacija(), this.getGeoSirina(), 
				this.getGeoDuzina(), this.getVisina(), this.getTipStupa(),this.getProizvodac(), this.getTezina(), 
				this.getOznakaUzemljenja(), this.getVrstaZastite(), azuriraniIzolatori);
	}	

}
