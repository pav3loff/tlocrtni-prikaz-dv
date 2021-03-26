package hr.fer.pavlic.dipl.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ZastitnoUze {
	
	private int idZastitnogUzeta;
	private List<Integer> idStzu;
	
	public ZastitnoUze() {
		super();
	}

	public ZastitnoUze(int idZastitnogUzeta, List<Integer> idStzu) {
		super();
		this.idZastitnogUzeta = idZastitnogUzeta;
		this.idStzu = idStzu;
	}
	
	public ZastitnoUze(JSONObject zastitnoUzeJson) {
		if(!(zastitnoUzeJson.isNull("idZastitnogUzeta"))) {
			this.setIdZastitnogUzeta(zastitnoUzeJson.getInt("idZastitnogUzeta"));
		}
		
		if(!(zastitnoUzeJson.isNull("idStzu"))) {
			JSONArray idStzuJson = zastitnoUzeJson.getJSONArray("idStzu");
			
			this.idStzu = new LinkedList<>();
			
			for(int i = 0; i < idStzuJson.length(); i++) {
				int idStzu = idStzuJson.getInt(i);
				
				this.idStzu.add(idStzu);
			}
		}
	}

	public int getIdZastitnogUzeta() {
		return idZastitnogUzeta;
	}

	public void setIdZastitnogUzeta(int idZastitnogUzeta) {
		this.idZastitnogUzeta = idZastitnogUzeta;
	}

	public List<Integer> getIdStzu() {
		return idStzu;
	}

	public void setIdStzu(List<Integer> idStzu) {
		this.idStzu = idStzu;
	}

}
