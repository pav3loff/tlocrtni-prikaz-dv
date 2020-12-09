package hr.fer.pavlic.dipl;

import org.json.JSONObject;

public interface IStup {
	
	TipStupa getType();
	
	JSONObject getJson();
	
	IStup separateST(IStup stup);
	
	IStup changeOrientation(IStup stup);

}
