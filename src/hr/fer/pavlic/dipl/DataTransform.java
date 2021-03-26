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
import hr.fer.pavlic.dipl.model.JelaStup;
import hr.fer.pavlic.dipl.model.MackaStup;
import hr.fer.pavlic.dipl.model.PortalStup;
import hr.fer.pavlic.dipl.model.Stup;
import hr.fer.pavlic.dipl.model.YStup;

public class DataTransform {
	
	private final static String CURRENT_DIR = System.getProperty("user.dir");

	public static void main(String[] args) throws Exception {		
		// procitati podatke u izvornom obliku i prilagoditi ih u JSON oblik
		IDataLoader dataLoader = new JsonObjectLoader();
		
		Path path = Paths.get(CURRENT_DIR + "\\inputData.json");
		
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
			
			stup.transform();
			
			azuriraniStupovi.add(stup);
		}
		
		JSONArray dalekovodiJson = jsonObject.getJSONArray("dalekovodi");
		List<Dalekovod> azuriraniDalekovodi = new LinkedList<>();
		
		for(int i = 0; i < dalekovodiJson.length(); i++) {
			azuriraniDalekovodi.add(new Dalekovod(dalekovodiJson.getJSONObject(i)));
		}
		
		
		
		// zapisati json u datoteku (izvornim podatcima samo dodati informacije o polozajima svih elemenata u WGS84 sustavu
		if(args[0].equals("-json")) {
			JSONArray stupoviOut = new JSONArray();
			
			for(Stup stup : azuriraniStupovi) {
				stupoviOut.put(stup.getJson());
			}
			
			try(FileWriter writer = new FileWriter(
					Paths.get(CURRENT_DIR + "\\Data-Display\\src\\dummyData.js").toFile())) {
				writer.write("export const STUPOVI = ");
				writer.write(stupoviOut.toString());
			} catch (IOException exc) {
				System.out.println("Neuspješno pisanje u datoteku!");
			}
		} else if(args[0].equals("-xml")) {
			Document document = DocumentHelper.createDocument();
			
			Element root = document.addElement("osm");
			root.addAttribute("version", "0.6");
			
			for(Stup stup : azuriraniStupovi) {
				stup.getAsOsmXmlElement(root);
			}
			
			try(FileWriter writer = new FileWriter(
					Paths.get(CURRENT_DIR + "\\outputData.xml").toFile())) {
				writer.write(root.asXML().toString());
			} catch (IOException exc) {
				System.out.println("Neuspješno pisanje u datoteku!");
			}
		} else {
			System.out.println("Neispravan format za izvoz podataka!");
		}
	}
	
}
