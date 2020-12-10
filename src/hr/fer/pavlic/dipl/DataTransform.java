package hr.fer.pavlic.dipl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataTransform {

	public static void main(String[] args) {
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
			} else {
				stup = null;
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
			
			if(tipStupa == TipStupa.BACVA || tipStupa == TipStupa.JELA) {
				for(Izolator izolator : azuriraniStup.getIzolatori()) {
					System.out.println("(" + izolator.getSpojnaTockaIzolatoraX() 
						+ ", " + izolator.getSpojnaTockaIzolatoraZ() + ")");
					System.out.println("(" + izolator.getSpojnaTockaVodicaX() 
						+ ", " + izolator.getSpojnaTockaVodicaZ() + ")");
				}
			} 
			
			System.out.println("-------------");
		}
		
		// WGS -> UTM
	}
	
}
