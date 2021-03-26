package hr.fer.pavlic.dipl.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Vodic {
	
	private int idVodica;
	private String oznakaFaze;
	private String materijal;
	private List<Integer> idStv;
	
	public Vodic() {
		super();
	}
	
	public Vodic(int idVodica, String oznakaFaze, String materijal, List<Integer> idStv) {
		super();
		this.idVodica = idVodica;
		this.oznakaFaze = oznakaFaze;
		this.materijal = materijal;
		this.idStv = idStv;
	}
	
	public Vodic(JSONObject vodicJson) {
		if(!(vodicJson.isNull("idVodica"))) {
			this.setIdVodica(vodicJson.getInt("idVodica"));
		}
		
		if(!(vodicJson.isNull("oznakaFaze"))) {
			this.setOznakaFaze(vodicJson.getString("oznakaFaze"));
		}
		
		if(!(vodicJson.isNull("materijal"))) {
			this.setMaterijal(vodicJson.getString("materijal"));
		}
		
		if(!(vodicJson.isNull("idStv"))) {
			JSONArray idStvJson = vodicJson.getJSONArray("idStv");
			
			this.idStv = new LinkedList<>();
			
			for(int i = 0; i < idStvJson.length(); i++) {
				int idStv = idStvJson.getInt(i);
				
				this.idStv.add(idStv);
			}
		}
	}

	public int getIdVodica() {
		return idVodica;
	}

	public void setIdVodica(int idVodica) {
		this.idVodica = idVodica;
	}

	public String getOznakaFaze() {
		return oznakaFaze;
	}

	public void setOznakaFaze(String oznakaFaze) {
		this.oznakaFaze = oznakaFaze;
	}

	public String getMaterijal() {
		return materijal;
	}

	public void setMaterijal(String materijal) {
		this.materijal = materijal;
	}

	public List<Integer> getIdStv() {
		return idStv;
	}

	public void setIdStv(List<Integer> idStv) {
		this.idStv = idStv;
	}

}
