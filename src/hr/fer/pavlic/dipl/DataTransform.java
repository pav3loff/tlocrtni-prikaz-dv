package hr.fer.pavlic.dipl;

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
import hr.fer.pavlic.dipl.stupovi.TipStupa;
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
			
			azuriraniStupovi.add(stup);
		}
		
		// test ispis svih transformiranih tocaka
		for(Stup azuriraniStup : azuriraniStupovi) {
			TipStupa tipStupa = azuriraniStup.getType();
			
			if(tipStupa == TipStupa.BACVA || tipStupa == TipStupa.JELA || tipStupa == TipStupa.DUNAV || 
					tipStupa == TipStupa.PORTAL || tipStupa == TipStupa.Y || tipStupa == TipStupa.MACKA ||
					tipStupa == TipStupa.DVOSTRUKA_JELA || tipStupa == TipStupa.DVOSTRUKI_Y ||
					tipStupa == TipStupa.DVOSTRUKI_PORTAL || tipStupa == TipStupa.DVOSTRUKA_MACKA) {
				for(Izolator izolator : azuriraniStup.getIzolatori()) {
					System.out.println("(" + izolator.getSpojnaTockaIzolatoraX() 
						+ ", " + izolator.getSpojnaTockaIzolatoraZ() + ")");
					System.out.println("(" + izolator.getSpojnaTockaVodicaX() 
						+ ", " + izolator.getSpojnaTockaVodicaZ() + ")");
				}
				
				for(SpojnaTockaZastitnogUzeta spojnaTockaZu : azuriraniStup.getSpojneTockeZastitneUzadi()) {
					System.out.println("(" + spojnaTockaZu.getSpojnaTockaZastitnogUzetaX()
							+ ", " + spojnaTockaZu.getSpojnaTockaZastitnogUzetaZ() + ")");
				}
			} 
			
			System.out.println("-------------");
		}
		
		// WGS -> UTM test ispis
		for(Stup azuriraniStup : azuriraniStupovi) {
			TipStupa tipStupa = azuriraniStup.getType();
			
			if(tipStupa == TipStupa.BACVA || tipStupa == TipStupa.JELA || tipStupa == TipStupa.DUNAV || 
					tipStupa == TipStupa.PORTAL || tipStupa == TipStupa.Y || tipStupa == TipStupa.MACKA ||
					tipStupa == TipStupa.DVOSTRUKA_JELA || tipStupa == TipStupa.DVOSTRUKI_Y ||
					tipStupa == TipStupa.DVOSTRUKI_PORTAL || tipStupa == TipStupa.DVOSTRUKA_MACKA) {
				Stup azuriraniStupWgs = azuriraniStup.convertUtmToWgs(azuriraniStup);
				
				System.out.println(azuriraniStupWgs.getGeoSirina() + "," +
						azuriraniStupWgs.getGeoDuzina());
				
				List<Izolator> izolatori = azuriraniStupWgs.getIzolatori();
				for(Izolator izolator : izolatori) {
					System.out.println(izolator.getSpojnaTockaIzolatoraGeoSirina() + "," +
							izolator.getSpojnaTockaIzolatoraGeoDuzina());
					System.out.println(izolator.getSpojnaTockaVodicaGeoSirina() + "," + 
							izolator.getSpojnaTockaVodicaGeoDuzina());
				}
				
				List<SpojnaTockaZastitnogUzeta> spojneTockeZu = azuriraniStup.getSpojneTockeZastitneUzadi();
				for(SpojnaTockaZastitnogUzeta spojnaTockaZu : spojneTockeZu) {
					System.out.println(spojnaTockaZu.getSpojnaTockaZastitnogUzetaGeoSirina() + "," +
							spojnaTockaZu.getSpojnaTockaZastitnogUzetaGeoDuzina());
				}
			}
		}
	}
	
}
