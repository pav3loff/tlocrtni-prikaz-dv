package hr.fer.pavlic.dipl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import org.json.JSONObject;

public class JsonObjectLoader implements IDataLoader {

	@Override
	public JSONObject parseData(Path sourcePath) {
		try(BufferedReader br = new BufferedReader(new FileReader(sourcePath.toFile()))) {
			StringBuilder sb = new StringBuilder();
			
			String line = br.readLine();			
			
			while(line != null) {
				sb.append(line);
				
				line = br.readLine();
			}
			
			return  new JSONObject(sb.toString());
		} catch(IOException exc) {
			System.err.println("Error reading file " + sourcePath);
			
			return null;
		}
	}

}
