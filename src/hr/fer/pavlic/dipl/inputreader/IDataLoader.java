package hr.fer.pavlic.dipl.inputreader;

import java.nio.file.Path;

import org.json.JSONObject;

public interface IDataLoader {
	
	// Za zadanu putanju do datoteke s podatcima, parsirati podatke i stvoriti JSON objekt
	JSONObject parseData(Path sourcePath);

}
