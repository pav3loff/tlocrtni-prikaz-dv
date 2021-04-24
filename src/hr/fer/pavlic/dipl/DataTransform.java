package hr.fer.pavlic.dipl;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.inputreader.IDataLoader;
import hr.fer.pavlic.dipl.inputreader.JsonObjectLoader;
import hr.fer.pavlic.dipl.model.BacvaStup;
import hr.fer.pavlic.dipl.model.Dalekovod;
import hr.fer.pavlic.dipl.model.DunavStup;
import hr.fer.pavlic.dipl.model.DvostrukaJelaStup;
import hr.fer.pavlic.dipl.model.DvostrukaMackaStup;
import hr.fer.pavlic.dipl.model.DvostrukiPortalStup;
import hr.fer.pavlic.dipl.model.DvostrukiYStup;
import hr.fer.pavlic.dipl.model.Izolator;
import hr.fer.pavlic.dipl.model.JelaStup;
import hr.fer.pavlic.dipl.model.MackaStup;
import hr.fer.pavlic.dipl.model.PortalStup;
import hr.fer.pavlic.dipl.model.Stup;
import hr.fer.pavlic.dipl.model.Vodic;
import hr.fer.pavlic.dipl.model.YStup;
import hr.fer.pavlic.dipl.model.ZastitnoUze;

public class DataTransform {
	
	private final static String CURRENT_DIR = System.getProperty("user.dir");

	public static void main(String[] args) throws Exception {		
		// procitati podatke u izvornom obliku i prilagoditi ih u JSON oblik
		IDataLoader dataLoader = new JsonObjectLoader();
		
		Path path = Paths.get(CURRENT_DIR + "\\input-data.json");
		
		JSONObject jsonObject = dataLoader.parseData(path);	
		
		// Mapiranje svih JSON objekata na Java razrede
		// Stupovi
		JSONArray stupoviJson = jsonObject.getJSONArray("stupovi");
		List<Stup> stupovi = new LinkedList<>();
	
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
			
			stupovi.add(stup);
		}
		
		// Dalekovodi
		JSONArray dalekovodiJson = jsonObject.getJSONArray("dalekovodi");
		List<Dalekovod> dalekovodi = new LinkedList<>();
		
		for(int i = 0; i < dalekovodiJson.length(); i++) {
			dalekovodi.add(new Dalekovod(dalekovodiJson.getJSONObject(i)));
		}
		
		// Zastitna uzad
		JSONArray zastitnaUzadJson = jsonObject.getJSONArray("zastitnaUzad");
		List<ZastitnoUze> zastitnaUzad = new LinkedList<>();
		
		for(int i = 0; i < zastitnaUzadJson.length(); i++) {
			zastitnaUzad.add(new ZastitnoUze(zastitnaUzadJson.getJSONObject(i)));
		}
		
		// Transformacije se obavljaju striktno redom
		// 1. transf: Razdvajanje STI i STZU
		for(Stup stup : stupovi) {
			stup.separateSti();
			stup.separateStzu();
		}
		
		// 2. transf: Konverzija STI, STZU UTM -> WGS (ukljucuje i azuriranje polozaja STI i STZU s obzirom na orijentaciju stupa)
		for(Stup stup : stupovi) {
			stup.convertStiUtmToWgs();
			stup.convertStzuUtmToWgs();
		}
		
		// 3. transf: Generiranje polja izolatora unutar vodica - to polje odgovarat ce polju identifikatora STV na koje se vodic spaja
		for(Dalekovod dalekovod : dalekovodi) {
			for(Vodic vodic : dalekovod.getVodici()) {
				vodic.nadiIzolatore(stupovi);
			}
		}
		
		// 4. transf: Pomocu polja izolatora (sadrzi vec proracunate STI) izracunati polozaje STV
		for(Dalekovod dalekovod : dalekovodi) {
			for(Vodic vodic : dalekovod.getVodici()) {
				vodic.azurirajStv();
			}
		}
		
		// Azurirati one izolatore onog stupa cije STV je vodic konfigurirao
		for(Stup stup : stupovi) {
			for(Izolator izolator : stup.getIzolatori()) {
				for(Dalekovod dalekovod : dalekovodi) {
					for(Vodic vodic : dalekovod.getVodici()) {
						List<Izolator> azuriraniIzolatori = vodic.getSpojniIzolatori();
						
						for(Izolator azuriraniIzolator : azuriraniIzolatori) {
							if(izolator.getIdIzolatora() == azuriraniIzolator.getIdIzolatora()) {
								izolator.setStv(azuriraniIzolator.getStv());
							}
						}
					}
				}
			}
		}
		
		// Generiranje oblika konzole
		for(Stup stup : stupovi) {
			stup.generateKonzola();
		}
		
		// Spajanje zastitne uzadi na STZU
		for(ZastitnoUze zastitnoUze : zastitnaUzad) {
			zastitnoUze.updateKoordinateStzu(stupovi);
		}
		
		// zapisati u datoteku
		if(args[0].equals("-json")) {
			System.out.println("Izvoz podataka u .json formatu...");
			
			JSONObject output = new JSONObject();
			
			JSONArray stupoviJsonOut = new JSONArray();
			
			for(Stup stup : stupovi) {
				stupoviJsonOut.put(stup.getAsJson());
			}
			
			output.append("stupovi", stupoviJsonOut);
			
			JSONArray dalekovodiJsonOut = new JSONArray();
			
			for(Dalekovod dalekovod : dalekovodi) {
				dalekovodiJsonOut.put(dalekovod.getAsJson());
			}
			
			output.append("dalekovodi", dalekovodiJsonOut);
			
			JSONArray zastitnaUzadJsonOut = new JSONArray();
			
			for(ZastitnoUze zastitnoUze : zastitnaUzad) {
				zastitnaUzadJsonOut.put(zastitnoUze.getAsJson());
			}
			
			output.append("zastitnaUzad", zastitnaUzadJsonOut);
			
			try(FileWriter writer = new FileWriter(
					Paths.get(CURRENT_DIR + "\\output-data.json").toFile())) {
				writer.write(output.toString());
			} catch (IOException exc) {
				System.out.println("Neuspješno pisanje u datoteku!");
			}
			
			System.out.println("Izvoz podataka uspješan!");
		} else if(args[0].equals("-osmxml")) {
			System.out.println("Izvoz podataka u OSM XML formatu...");
			
			Document document = DocumentHelper.createDocument();
			
			Element root = document.addElement("osm");
			root.addAttribute("version", "0.6");
			
			for(Stup stup : stupovi) {
				stup.getAsOsmXmlElement(root);
			}
			
			for(Dalekovod dalekovod : dalekovodi) {
				dalekovod.getAsOsmXmlElement(root);
			}
			
			for(ZastitnoUze zastitnoUze : zastitnaUzad) {
				zastitnoUze.getAsOsmXmlElement(root);
			}
			
			try(FileWriter writer = new FileWriter(
					Paths.get(CURRENT_DIR + "\\output-data.osm").toFile())) {
				writer.write(root.asXML().toString());
			} catch (IOException exc) {
				System.out.println("Neuspješno pisanje u datoteku!");
			}
			
			System.out.println("Izvoz podataka uspješan!");
		} else {
			System.out.println("Neispravan format za izvoz podataka!");
		}
	}
	
}
