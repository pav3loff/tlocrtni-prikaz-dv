package hr.fer.pavlic.dipl.model;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.Pravac;
import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class Vodic {
	
	private final static double RAZMAK_STI_I_STV = 4;
	private int idVodica;
	private String oznakaFaze;
	private String materijal;
	private List<Integer> idSt; // identifikatori spojnih točaka na koje se spaja
	private List<Izolator> spojniIzolatori; // Izolatori kojima pripadaju STV na koje je spojen vodic
	
	public Vodic(int idVodica, String oznakaFaze, String materijal, List<Integer> idSt) {
		super();
		this.idVodica = idVodica;
		this.oznakaFaze = oznakaFaze;
		this.materijal = materijal;
		this.idSt = idSt;
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
		
		if(!(vodicJson.isNull("idSt"))) {
			JSONArray idStJson = vodicJson.getJSONArray("idSt");
			
			this.idSt = new LinkedList<>();
			
			for(int i = 0; i < idStJson.length(); i++) {
				int idSt = idStJson.getInt(i);
				
				this.idSt.add(idSt);
			}
		}
		
		this.spojniIzolatori = new LinkedList<>();
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

	public List<Integer> getIdSt() {
		return idSt;
	}

	public void setIdSt(List<Integer> idSt) {
		this.idSt = idSt;
	}

	public List<Izolator> getSpojniIzolatori() {
		return spojniIzolatori;
	}

	public void setSpojneTocke(List<Izolator> spojniIzolatori) {
		this.spojniIzolatori = spojniIzolatori;
	}
	
	public void nadiIzolatore(List<Stup> stupovi) {
		for(Integer idSt : this.idSt) {
			for(Stup stup : stupovi) {
				for(Izolator izolator : stup.getIzolatori()) {
					SpojnaTocka stv = izolator.getStv();
					
					if(idSt == stv.getIdSt()) {
						this.spojniIzolatori.add(izolator);
					}
				}
			}
		}
	}
	
	public void azurirajStv() {
		List<RasponVodicaIzmeduDvaIzolatora> rasponi = generirajRaspone();
		
		for(RasponVodicaIzmeduDvaIzolatora raspon : rasponi) {
			// Nagib pravca izmedu STI susjednih stupova
			SpojnaTocka pocSti = raspon.getPocIzolator().getSti();
			SpojnaTocka krajSti = raspon.getKrajIzolator().getSti();
			
			// Dobiti STI u UTM sustavu
			try {
				UtmCoordinate pocStiUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(pocSti.getGeoSirina(), pocSti.getGeoDuzina()));
				UtmCoordinate krajStiUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(krajSti.getGeoSirina(), krajSti.getGeoDuzina()));
			
				// Pravac p prolazi kroz sljedece 2 tocke: (pocStiUtm.easting, pocStiUtm.northing) i (krajStiUtm.easting, krajStiUtm.northing)
				// Iz tih dvaju tocaka dobiva se kut nagiba pravca
				Pravac p = new Pravac(pocStiUtm.getEasting(), pocStiUtm.getNorthing(), krajStiUtm.getEasting(), krajStiUtm.getNorthing());
				double kut = p.nadiKutNagibaPravca();
				
				// Duz pravca p pocStv ce se postaviti na udaljenost RAZMAK_STI_I_STV od pocSti
				// Duz pravca p krajStv ce se postaviti na udaljenost RAZMAK_STI_I_STV od krajSti
				// Stv ce u odnosu na pripadni Sti imati koordinate (xSti +/- x, zSti +/- z)
				// Predznak ce ovisiti o polozaju dvaju susjednih stupova (efektivno njihovih STI)
				double x = RAZMAK_STI_I_STV * Math.abs(Math.sin(Math.toRadians(90 - kut)));
				double z = RAZMAK_STI_I_STV * Math.abs(Math.sin(Math.toRadians(kut)));
				
				double pocStvUtmEasting = 0;
				double pocStvUtmNorthing = 0;
				double krajStvUtmEasting = 0;
				double krajStvUtmNorthing = 0;
				
				if(pocStiUtm.getEasting() > krajStiUtm.getEasting()) {
					if(pocStiUtm.getNorthing() > krajStiUtm.getNorthing()) {
						pocStvUtmEasting = pocStiUtm.getEasting() - x;
						pocStvUtmNorthing = pocStiUtm.getNorthing() - z;
						krajStvUtmEasting = krajStiUtm.getEasting() + x;
						krajStvUtmNorthing = krajStiUtm.getNorthing() + z;
					} else if(pocStiUtm.getNorthing() < krajStiUtm.getNorthing()) {
						pocStvUtmEasting = pocStiUtm.getEasting() - x;
						pocStvUtmNorthing = pocStiUtm.getNorthing() + z;
						krajStvUtmEasting = krajStiUtm.getEasting() + x;
						krajStvUtmNorthing = krajStiUtm.getNorthing() - z;
					} else { // susjedne STI su tocno horizontalno
						pocStvUtmEasting = pocStiUtm.getEasting() - x;
						pocStvUtmNorthing = pocStiUtm.getNorthing();
						krajStvUtmEasting = krajStiUtm.getEasting() + x;
						krajStvUtmNorthing = krajStiUtm.getNorthing();
					}
				} else if(pocStiUtm.getEasting() < krajStiUtm.getEasting()) {
					if(pocStiUtm.getNorthing() > krajStiUtm.getNorthing()) {
						pocStvUtmEasting = pocStiUtm.getEasting() + x;
						pocStvUtmNorthing = pocStiUtm.getNorthing() - z;
						krajStvUtmEasting = krajStiUtm.getEasting() - x;
						krajStvUtmNorthing = krajStiUtm.getNorthing() + z;
					} else if(pocStiUtm.getNorthing() < krajStiUtm.getNorthing()) {
						pocStvUtmEasting = pocStiUtm.getEasting() + x;
						pocStvUtmNorthing = pocStiUtm.getNorthing() + z;
						krajStvUtmEasting = krajStiUtm.getEasting() - x;
						krajStvUtmNorthing = krajStiUtm.getNorthing() - z;
					} else { // susjedne STI su tocno horizontalno
						pocStvUtmEasting = pocStiUtm.getEasting() + x;
						pocStvUtmNorthing = pocStiUtm.getNorthing();
						krajStvUtmEasting = krajStiUtm.getEasting() - x;
						krajStvUtmNorthing = krajStiUtm.getNorthing();
					}
				} else { // susjedne STI su tocno vertikalno
					if(pocStiUtm.getNorthing() > krajStiUtm.getNorthing()) {
						pocStvUtmEasting = pocStiUtm.getEasting();
						pocStvUtmNorthing = pocStiUtm.getNorthing() - z;
						krajStvUtmEasting = krajStiUtm.getEasting();
						krajStvUtmNorthing = krajStiUtm.getNorthing() + z;
					} else if(pocStiUtm.getNorthing() < krajStiUtm.getNorthing()) {
						pocStvUtmEasting = pocStiUtm.getEasting();
						pocStvUtmNorthing = pocStiUtm.getNorthing() + z;
						krajStvUtmEasting = krajStiUtm.getEasting();
						krajStvUtmNorthing = krajStiUtm.getNorthing() - z;
					} 
				}
				
				WgsCoordinate pocStvWgs = UtmWgsConverter.convertToWgs(
						new UtmCoordinate(pocStiUtm.getLongZone(), pocStiUtm.getLatZone(), 
								pocStvUtmEasting, pocStvUtmNorthing));
				
				WgsCoordinate krajStvWgs = UtmWgsConverter.convertToWgs(
						new UtmCoordinate(krajStiUtm.getLongZone(), krajStiUtm.getLatZone(), 
								krajStvUtmEasting, krajStvUtmNorthing));
				
				if(raspon.getPocIzolator().isStupZatezni()) {
					raspon.getPocIzolator().getStv().setGeoSirina(pocStvWgs.getGeoSirina());
					raspon.getPocIzolator().getStv().setGeoDuzina(pocStvWgs.getGeoDuzina());
				} else {
					raspon.getPocIzolator().getStv().setGeoSirina(pocSti.getGeoSirina());
					raspon.getKrajIzolator().getStv().setGeoDuzina(pocSti.getGeoDuzina());
				}
				
				if(raspon.getKrajIzolator().isStupZatezni()) {
					raspon.getKrajIzolator().getStv().setGeoSirina(krajStvWgs.getGeoSirina());
					raspon.getKrajIzolator().getStv().setGeoDuzina(krajStvWgs.getGeoDuzina());
				} else {
					raspon.getKrajIzolator().getStv().setGeoSirina(krajSti.getGeoSirina());
					raspon.getKrajIzolator().getStv().setGeoDuzina(krajSti.getGeoDuzina());
				}
				
				// Izolatori (STV) u rasponima su prilagodeni, sad ih je potrebno kopirati u originalno polje izolatora
				for(Izolator izolator : this.spojniIzolatori) {
					if(izolator.getIdIzolatora() == raspon.getPocIzolator().getIdIzolatora()) {
						izolator.setStv(raspon.getPocIzolator().getStv());
					} else if(izolator.getIdIzolatora() == raspon.getKrajIzolator().getIdIzolatora()) {
						izolator.setStv(raspon.getKrajIzolator().getStv());
					}
				}
			} catch (Exception e) {
				System.out.println("Neuspjesna konverzija STI!");
			}
		}
	}
	
	private List<RasponVodicaIzmeduDvaIzolatora> generirajRaspone() {
		List<RasponVodicaIzmeduDvaIzolatora> rasponi = new LinkedList<>();
		
		for(int i = 1; i < this.spojniIzolatori.size(); i += 2) {
			Izolator pocIzolator = this.spojniIzolatori.get(i - 1);
			Izolator krajIzolator = this.spojniIzolatori.get(i);
			
			if((!(pocIzolator.isStupZatezni()) && !(krajIzolator.isStupZatezni())) || 
					(!(pocIzolator.isStupZatezni()) && i == 1) || 
					(!(krajIzolator.isStupZatezni()) && i == 1)) {
				i--;
			}
			
			rasponi.add(new RasponVodicaIzmeduDvaIzolatora(pocIzolator, krajIzolator));
		}
		
		return rasponi;
	}
	
	public JSONObject getAsJson() {
		JSONObject vodicJson = new JSONObject();
		
		vodicJson.put("idVodica", this.idVodica);
		vodicJson.put("oznakaFaze", this.oznakaFaze);
		vodicJson.put("materijal", this.materijal);
		
		JSONArray koordinateSpojistaJson = new JSONArray();
		for(Izolator izolator : this.spojniIzolatori) {
			koordinateSpojistaJson.put(izolator.getStv().getAsSimpleJson());
		}
		
		vodicJson.put("koordinateSpojista", koordinateSpojistaJson);
		
		return vodicJson;
	}
	
	public void getAsOsmXmlElement(Element root, int idDalekovoda, int naponDalekovoda) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Element vodicWay = root.addElement("way")
				.addAttribute("id", UidGenerator.getUidString())
				.addAttribute("version", "1")
				.addAttribute("timestamp", timestamp.toString());
		
		vodicWay.addElement("tag").addAttribute("k", "idVodica").addAttribute("v", Integer.toString(this.idVodica));
		vodicWay.addElement("tag").addAttribute("k", "type").addAttribute("v", "vodic");
		vodicWay.addElement("tag").addAttribute("k", "idDalekovoda").addAttribute("v", Integer.toString(idDalekovoda));
		vodicWay.addElement("tag").addAttribute("k", "naponDalekovoda").addAttribute("v", Integer.toString(naponDalekovoda));	
		vodicWay.addElement("tag").addAttribute("k", "oznakaFaze").addAttribute("v", this.oznakaFaze);
		vodicWay.addElement("tag").addAttribute("k", "materijal").addAttribute("v", this.materijal);
		
		// Generirati referentne STI I STV
		List<SpojnaTocka> referentneSt = nadiReferentneSt();
		for(SpojnaTocka refSt : referentneSt) {
			vodicWay.addElement("nd").addAttribute("ref", Long.toString(refSt.getUid()));
		}
	}
	
	/**
	 * Metoda se koristi za generiranje referentnih cvorova kroz koje prolazi vodicWay u OSM XML-u
	 */
	private List<SpojnaTocka> nadiReferentneSt() {
		List<SpojnaTocka> referetneSt = new LinkedList<>();
		
		for(int i = 0; i < this.idSt.size(); i++) {
			for(Izolator izolator : this.spojniIzolatori) {
				if(this.idSt.get(i) == izolator.getStv().getIdSt()) {
					SpojnaTocka sti = izolator.getSti();
					SpojnaTocka stv = izolator.getStv();
					
					// Samo u jednom slucaju ce vodic krenuti od STI pa tek onda STV -> samo za pocetni stup (onaj od kojeg vodic krece)
					// U svim ostalim slucajevima ce vodic prvo ici na STV pa na STI
					if(i == 0) { 
						if(!(referetneSt.contains(sti))) {
							referetneSt.add(sti);
						}
						
						referetneSt.add(stv);
					} else {
						referetneSt.add(stv);
						
						if(!(referetneSt.contains(sti))) {
							referetneSt.add(sti);
						}
					}
				}
			}
		}
		
		return referetneSt;
	}
	
	public String toString() {
		return String.format("[VODIC] ID: %d\n", this.idVodica);
	}
	
	public boolean equals(Object object) {
		if(object instanceof Vodic) {
			Vodic other = (Vodic) object;
			
			return this.idVodica == other.idVodica;
		}
		
		return false;
	}

}
