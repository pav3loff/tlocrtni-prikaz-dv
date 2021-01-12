package hr.fer.pavlic.dipl;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.inputreader.IDataLoader;
import hr.fer.pavlic.dipl.inputreader.JsonObjectLoader;
import hr.fer.pavlic.dipl.stupovi.BacvaStup;
import hr.fer.pavlic.dipl.stupovi.DunavStup;
import hr.fer.pavlic.dipl.stupovi.DvostrukaJelaStup;
import hr.fer.pavlic.dipl.stupovi.DvostrukaMackaStup;
import hr.fer.pavlic.dipl.stupovi.DvostrukiPortalStup;
import hr.fer.pavlic.dipl.stupovi.DvostrukiYStup;
import hr.fer.pavlic.dipl.stupovi.Izolator;
import hr.fer.pavlic.dipl.stupovi.JelaStup;
import hr.fer.pavlic.dipl.stupovi.MackaStup;
import hr.fer.pavlic.dipl.stupovi.PortalStup;
import hr.fer.pavlic.dipl.stupovi.SpojnaTockaZastitnogUzeta;
import hr.fer.pavlic.dipl.stupovi.Stup;
import hr.fer.pavlic.dipl.stupovi.YStup;

public class DataTransform {

	public static void main(String[] args) throws Exception {
		// procitati podatke u izvornom obliku i prilagoditi ih u JSON oblik
		IDataLoader dataLoader = new JsonObjectLoader();
		
		Path path = Paths.get("C:/Users/mario/Desktop/tlocrtni_prikaz_dalekovoda/podatci.json");
		
		JSONObject jsonObject = dataLoader.parseData(path);	
		
		// transformirati podatke da nema preklapanja spojnih tocaka + promjena orijentacije tocaka
		JSONArray stupoviJson = jsonObject.getJSONArray("stupovi");
		List<Stup> azuriraniStupovi = new LinkedList<>();
	
		for(int i = 0; i < stupoviJson.length(); i++) {
			JSONObject stupJson = stupoviJson.getJSONObject(i);
			Stup stup;
			
			if(stupJson.getString("oblikGlaveStupa").equals("BACVA")) {
				stup = new BacvaStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("JELA")) {
				stup = new JelaStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("DUNAV")) {
				stup = new DunavStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("PORTAL")) {
				stup = new PortalStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("Y")) {
				stup = new YStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("MACKA")) {
				stup = new MackaStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("DVOSTRUKA_JELA")) {
				stup = new DvostrukaJelaStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("DVOSTRUKI_Y")) {
				stup = new DvostrukiYStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("DVOSTRUKI_PORTAL")) {
				stup = new DvostrukiPortalStup(stupJson);
			} else if(stupJson.get("oblikGlaveStupa").equals("DVOSTRUKA_MACKA")) {
				stup = new DvostrukaMackaStup(stupJson);
			} else {
				throw new Exception("Oblik stupa nije prepoznat!");
			}
			
			// ukloniti preklapanje
			stup = stup.separateST(stup);
			
			// promjena orijentacije tocaka
			stup = stup.changeOrientation(stup);
			
			// transf UTM <-> WGS
			stup = stup.convertUtmToWgs(stup);
			
			azuriraniStupovi.add(stup);
		}
		
		// zapisati json u datoteku (izvornim podatcima samo dodati informacije o polozajima svih elemenata u WGS84 sustavu
		JSONObject dataOut = new JSONObject();
		dataOut.put("stupovi", stupoviJson);
		
		JSONArray stupoviJsonOut = dataOut.getJSONArray("stupovi");
		
		for(int i = 0; i < stupoviJsonOut.length(); i++) {
			JSONObject stupJson = stupoviJsonOut.getJSONObject(i);
			Stup azuriraniStup = azuriraniStupovi.get(i);
			
			stupJson.put("geoSirina", azuriraniStup.getGeoSirina());
			stupJson.put("geoDuzina", azuriraniStup.getGeoDuzina());
			
			JSONArray izolatoriJson = stupJson.getJSONArray("izolatori");
			List<Izolator> azuriraniIzolatori = azuriraniStup.getIzolatori();
			
			for(int j = 0; j < izolatoriJson.length(); j++) {
				JSONObject izolatorJson = izolatoriJson.getJSONObject(j);
				Izolator azuriraniIzolator = azuriraniIzolatori.get(j);
				
				JSONObject stiJson = izolatorJson.getJSONObject("spojnaTockaIzolatora");
				stiJson.put("geoSirina", azuriraniIzolator.getStiGeoSirina());
				stiJson.put("geoDuzina", azuriraniIzolator.getStiGeoDuzina());
				
				JSONObject stvJson = izolatorJson.getJSONObject("spojnaTockaVodica");
				stvJson.put("geoSirina", azuriraniIzolator.getStvGeoSirina());
				stvJson.put("geoDuzina", azuriraniIzolator.getStvGeoDuzina());
			}
			
			JSONArray spojneTockeZuJson = stupJson.getJSONArray("spojneTockeZastitneUzadi");
			List<SpojnaTockaZastitnogUzeta> azuriraneSpojneTockeZu = azuriraniStup.getSpojneTockeZu();
			
			for(int j = 0; j < spojneTockeZuJson.length(); j++) {
				JSONObject spojnaTockaZuJson = spojneTockeZuJson.getJSONObject(j);
				SpojnaTockaZastitnogUzeta azuriranaSpojnaTockaZu = azuriraneSpojneTockeZu.get(j);
				
				spojnaTockaZuJson.put("geoSirina", azuriranaSpojnaTockaZu.getGeoSirina());
				spojnaTockaZuJson.put("geoDuzina", azuriranaSpojnaTockaZu.getGeoDuzina());
			}
		}
		
		try(FileWriter writer = new FileWriter(
				Paths.get("C:/Users/mario/Desktop/tlocrtni_prikaz_dalekovoda/dataOut2.json").toFile())) {
			writer.write(stupoviJsonOut.toString());
		} catch (IOException exc) {
			System.out.println("Neuspješno pisanje u datoteku!");
		}
	}
	
}
