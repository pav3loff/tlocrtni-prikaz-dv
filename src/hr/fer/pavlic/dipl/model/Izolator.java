package hr.fer.pavlic.dipl.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.json.JSONObject;

import hr.fer.pavlic.dipl.util.Pravac;
import hr.fer.pavlic.dipl.util.UidGenerator;
import hr.fer.pavlic.dipl.utmwgstransf.UtmCoordinate;
import hr.fer.pavlic.dipl.utmwgstransf.UtmWgsConverter;
import hr.fer.pavlic.dipl.utmwgstransf.WgsCoordinate;

public class Izolator {
	
	private final static double DECIMALNO_MJESTO = 100000000.0;
	private final static double RADIJUS_KRUZNICE_IZOLATORA = 0.1;
	private final static double RAZMAK_IZOLATORA_I_ST = 1;
	private final static double SIRINA_IZOLATORA = 0.2;
	private int idIzolatora;
	private String materijal;
	private String izvedba;
	private int brojClanaka;
	private double geoSirina;
	private double geoDuzina;
	private SpojnaTocka sti;
	private SpojnaTocka stv;
	private long uid;
	
	public Izolator(int idIzolatora, String materijal, String izvedba, int brojClanaka, SpojnaTocka sti, SpojnaTocka stv) {
		super();
		this.idIzolatora = idIzolatora;
		this.materijal = materijal;
		this.izvedba = izvedba;
		this.brojClanaka = brojClanaka;
		this.geoSirina = this.geoDuzina = 0;
		this.sti = sti;
		this.stv = stv;
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}
	
	public Izolator(Izolator izolator) {
		super();
		this.idIzolatora = izolator.idIzolatora;
		this.materijal = izolator.materijal;
		this.izvedba = izolator.izvedba;
		this.brojClanaka = izolator.brojClanaka;
		this.geoSirina = this.geoDuzina = 0;
		this.sti = izolator.sti;
		this.stv = izolator.stv;
		this.uid = izolator.uid;
	}

	public Izolator(JSONObject izolatorJson) {
		if(!(izolatorJson.isNull("idIzolatora"))) {
			this.idIzolatora = izolatorJson.getInt("idIzolatora");
		}
		
		if(!(izolatorJson.isNull("spojnaTockaIzolatora"))) {
			JSONObject stiJson = izolatorJson.getJSONObject("spojnaTockaIzolatora");
			
			this.sti = new SpojnaTocka(stiJson);
		}
		
		if(!(izolatorJson.isNull("spojnaTockaVodica"))) {
			JSONObject stvJson = izolatorJson.getJSONObject("spojnaTockaVodica");
			
			this.stv = new SpojnaTocka(stvJson);
		}
		
		if(!(izolatorJson.isNull("materijal"))) {
			this.materijal = izolatorJson.getString("materijal");
		}
		
		if(!(izolatorJson.isNull("izvedba"))) {
			this.izvedba = izolatorJson.getString("izvedba");
		}
		
		if(!(izolatorJson.isNull("brojClanaka"))) {
			this.brojClanaka = izolatorJson.getInt("brojClanaka");
		}
		
		if(!(izolatorJson.isNull("geoSirina"))) {
			this.geoSirina = izolatorJson.getDouble("geoSirina");
		} else {
			this.geoSirina = 0;
		}
		
		if(!(izolatorJson.isNull("geoDuzina"))) {
			this.geoDuzina = izolatorJson.getDouble("geoDuzina");
		} else {
			this.geoDuzina = 0;
		}
		
		this.uid = Long.valueOf(UidGenerator.getUidString());
	}

	public int getIdIzolatora() {
		return idIzolatora;
	}

	public void setIdIzolatora(int idIzolatora) {
		this.idIzolatora = idIzolatora;
	}

	public String getMaterijal() {
		return materijal;
	}

	public void setMaterijal(String materijal) {
		this.materijal = materijal;
	}

	public String getIzvedba() {
		return izvedba;
	}

	public void setIzvedba(String izvedba) {
		this.izvedba = izvedba;
	}

	public int getBrojClanaka() {
		return brojClanaka;
	}

	public void setBrojClanaka(int brojClanaka) {
		this.brojClanaka = brojClanaka;
	}

	public double getGeoSirina() {
		return geoSirina;
	}

	public void setGeoSirina(double geoSirina) {
		this.geoSirina = geoSirina;
	}

	public double getGeoDuzina() {
		return geoDuzina;
	}

	public void setGeoDuzina(double geoDuzina) {
		this.geoDuzina = geoDuzina;
	}
	
	public SpojnaTocka getSti() {
		return sti;
	}

	public void setSti(SpojnaTocka sti) {
		this.sti = sti;
	}

	public SpojnaTocka getStv() {
		return stv;
	}

	public void setStv(SpojnaTocka stv) {
		this.stv = stv;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
	
	private List<TockaPrikazaIzolatora> izracunajVrhovePravokutnika() {	
		List<TockaPrikazaIzolatora> vrhoviPravokutnika = new LinkedList<>();
		
		SpojnaTocka sti = this.getSti();
		SpojnaTocka stv = this.getStv();
		
		try {
			UtmCoordinate stiUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(sti.getGeoSirina(), sti.getGeoDuzina()));
			UtmCoordinate stvUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(stv.getGeoSirina(), stv.getGeoDuzina()));
			
			// Potrebno je odrediti kut izmedu pravca p1 (koji ide od STI do STV) i pravca p2 (koji ide od STI a paralelan je s ekvatorom)
			// Pravac p2 kroz tocke STI i (stvEasting, stiNorthing)
			Pravac p1 = new Pravac(stiUtm.getEasting(), stiUtm.getNorthing(), stvUtm.getEasting(), stvUtm.getNorthing());
			Pravac p2 = new Pravac(stiUtm.getEasting(), stiUtm.getNorthing(), stvUtm.getEasting(), stiUtm.getNorthing());
			double kut = p1.nadiKutIzmeduPravca(p2);
			
			// Potrebno je odrediti tocke u kojima se stranice pravokutnika sijeku s pravcem p1: sjeciste1 i sjeciste2
			// Pravokutnik treba biti tocno izmedu STI i STV: sjeciste1 nalazi se duz pravca p1 i od STI je udaljeno za RAZMAK_IZOLATORA_I_ST
			//												  sjeciste2 nalazi se duz pravca p2 i od STV je udaljeno za RAZMAK_IZOLATORA_I_ST
			// sjeciste1 ce u odnosu na STI imati koordinate (xSti +/- x, zSti +/- z)
			// sjeciste2 ce u odnostu na STV imati koordinate (xStv +/- x, zStv +/- z)
			// Predznak ce ovisiti o polozaju STI i STV
			double x = RAZMAK_IZOLATORA_I_ST * Math.abs(Math.sin(Math.toRadians(90 - kut)));
			double z = RAZMAK_IZOLATORA_I_ST * Math.abs(Math.sin(Math.toRadians(kut)));
		
			double sjeciste1UtmEasting = 0, sjeciste1UtmNorthing = 0;
			double sjeciste2UtmEasting = 0, sjeciste2UtmNorthing = 0;
			
			if(stiUtm.getEasting() > stvUtm.getEasting()) {
				if(stiUtm.getNorthing() > stvUtm.getNorthing()) {
					sjeciste1UtmEasting = stiUtm.getEasting() - x;
					sjeciste1UtmNorthing = stiUtm.getNorthing() - z;
					sjeciste2UtmEasting = stvUtm.getEasting() + x;
					sjeciste2UtmNorthing = stvUtm.getNorthing() + z;
				} else if(stiUtm.getNorthing() < stvUtm.getNorthing()) {
					sjeciste1UtmEasting = stiUtm.getEasting() - x;
					sjeciste1UtmNorthing = stiUtm.getNorthing() + z;
					sjeciste2UtmEasting = stvUtm.getEasting() + x;
					sjeciste2UtmNorthing = stvUtm.getNorthing() - z;
				} else { // susjedne STI su tocno horizontalno
					sjeciste1UtmEasting = stiUtm.getEasting() - x;
					sjeciste1UtmNorthing = stiUtm.getNorthing();
					sjeciste2UtmEasting = stvUtm.getEasting() + x;
					sjeciste2UtmNorthing = stvUtm.getNorthing();
				}
			} else if(stiUtm.getEasting() < stvUtm.getEasting()) {
				if(stiUtm.getNorthing() > stvUtm.getNorthing()) {
					sjeciste1UtmEasting = stiUtm.getEasting() + x;
					sjeciste1UtmNorthing = stiUtm.getNorthing() - z;
					sjeciste2UtmEasting = stvUtm.getEasting() - x;
					sjeciste2UtmNorthing = stvUtm.getNorthing() + z;
				} else if(stiUtm.getNorthing() < stvUtm.getNorthing()) {
					sjeciste1UtmEasting = stiUtm.getEasting() + x;
					sjeciste1UtmNorthing = stiUtm.getNorthing() + z;
					sjeciste2UtmEasting = stvUtm.getEasting() - x;
					sjeciste2UtmNorthing = stvUtm.getNorthing() - z;
				} else { // susjedne STI su tocno horizontalno
					sjeciste1UtmEasting = stiUtm.getEasting() + x;
					sjeciste1UtmNorthing = stiUtm.getNorthing();
					sjeciste2UtmEasting = stvUtm.getEasting() - x;
					sjeciste2UtmNorthing = stvUtm.getNorthing();
				}
			} else { // susjedne STI su tocno vertikalno
				if(stiUtm.getNorthing() > stvUtm.getNorthing()) {
					sjeciste1UtmEasting = stiUtm.getEasting();
					sjeciste1UtmNorthing = stiUtm.getNorthing() - z;
					sjeciste2UtmEasting = stvUtm.getEasting();
					sjeciste2UtmNorthing = stvUtm.getNorthing() + z;
				} else if(stiUtm.getNorthing() < stvUtm.getNorthing()) {
					sjeciste1UtmEasting = stiUtm.getEasting();
					sjeciste1UtmNorthing = stiUtm.getNorthing() + z;
					sjeciste2UtmEasting = stvUtm.getEasting();
					sjeciste2UtmNorthing = stvUtm.getNorthing() - z;
				} else {
					sjeciste1UtmEasting = stiUtm.getEasting();
					sjeciste1UtmNorthing = stiUtm.getNorthing();
					sjeciste2UtmEasting = stvUtm.getEasting();
					sjeciste2UtmNorthing = stvUtm.getNorthing();
				}
			}
			
			// Prva dva vrha pravokutnika nalaze se duz pravca koji je okomit na pravac p1, a sijece ga u tocki sjeciste 1
			// Ostala dva vrha pravokutnika nalaze se duz pravca koji je okomit na pravac p1, a sijece ga u tocki sjeciste 2
			// Vrhovi pravokutnika onda imaju koordinate X oblika: (sjecisteX +/- SIRINA_IZOLATORA * cos(90 + kut)), a koordinate Z oblika: (sjecisteZ +/- SIRINA_IZOLATORA * sin(90 + kut))
			TockaPrikazaIzolatora vrh1 = new TockaPrikazaIzolatora(new UtmCoordinate(
					stiUtm.getLongZone(), stiUtm.getLatZone(), sjeciste1UtmEasting + SIRINA_IZOLATORA * Math.cos(Math.toRadians(90 + kut)), 
					sjeciste1UtmNorthing + SIRINA_IZOLATORA * Math.sin(Math.toRadians(90 + kut))));
			TockaPrikazaIzolatora vrh2 = new TockaPrikazaIzolatora(new UtmCoordinate(
					stiUtm.getLongZone(), stiUtm.getLatZone(), sjeciste1UtmEasting - SIRINA_IZOLATORA * Math.cos(Math.toRadians(90 + kut)), 
					sjeciste1UtmNorthing - SIRINA_IZOLATORA * Math.sin(Math.toRadians(90 + kut))));
			TockaPrikazaIzolatora vrh3 = new TockaPrikazaIzolatora(new UtmCoordinate(
					stiUtm.getLongZone(), stiUtm.getLatZone(), sjeciste2UtmEasting + SIRINA_IZOLATORA * Math.cos(Math.toRadians(90 + kut)), 
					sjeciste2UtmNorthing + SIRINA_IZOLATORA * Math.sin(Math.toRadians(90 + kut))));
			TockaPrikazaIzolatora vrh4 = new TockaPrikazaIzolatora(new UtmCoordinate(
					stiUtm.getLongZone(), stiUtm.getLatZone(), sjeciste2UtmEasting - SIRINA_IZOLATORA * Math.cos(Math.toRadians(90 + kut)), 
					sjeciste2UtmNorthing - SIRINA_IZOLATORA * Math.sin(Math.toRadians(90 + kut))));
			
			vrhoviPravokutnika.addAll(Arrays.asList(vrh1, vrh3, vrh4, vrh2)); // Redoslijed vrhova je bitan zbog odgovarajuceg spajanja
			
			for(TockaPrikazaIzolatora vrhPravokutnika : vrhoviPravokutnika) {
				vrhPravokutnika.izracunajWgs();
			}
		} catch (Exception e) {
			System.out.println("Neuspjesna konverzija!");
		}
		
		return vrhoviPravokutnika;
	}
	
	private List<TockaPrikazaIzolatora> izracunajTockeNaKruznici() {
		List<TockaPrikazaIzolatora> tockeKruznice = new LinkedList<>();
		
		SpojnaTocka sti = this.getSti();

		try {
			UtmCoordinate stiUtm = UtmWgsConverter.convertToUtm(new WgsCoordinate(sti.getGeoSirina(), sti.getGeoDuzina()));
			
			// Za prikaz izolatora nosivog stupa (oblik kruga) potrebno je oko STI generirati kruznicu, buduci da se STI i izolator na takvom stupu preklapaju (u tlocrtnom prikazu)
			for(int i = 0; i < 360; i += 30) {
				double x = stiUtm.getEasting() + RADIJUS_KRUZNICE_IZOLATORA * Math.cos(Math.toRadians(i));
				double z = stiUtm.getNorthing() + RADIJUS_KRUZNICE_IZOLATORA * Math.sin(Math.toRadians(i));
				
				tockeKruznice.add(new TockaPrikazaIzolatora(new UtmCoordinate(stiUtm.getLongZone(), stiUtm.getLatZone(), x, z)));
			}
			
		} catch (Exception e) {
			System.out.println("Neuspjesna konverzija!");
		}
		
		for(TockaPrikazaIzolatora tockaKruznice : tockeKruznice) {
			tockaKruznice.izracunajWgs();
		}
		
		return tockeKruznice;
	}

	public JSONObject getJson() {
		JSONObject izolatorJson = new JSONObject();
		izolatorJson.put("idIzolatora", this.idIzolatora);
		izolatorJson.put("materijal", this.materijal);
		izolatorJson.put("izvedba", this.izvedba);
		izolatorJson.put("brojClanaka", this.brojClanaka);
		izolatorJson.put("geoSirina", this.geoSirina);
		izolatorJson.put("geoDuzina", this.geoDuzina);
		izolatorJson.put("spojnaTockaIzolatora", this.sti.getJson());
		izolatorJson.put("spojnaTockaVodica", this.stv.getJson());
		
		return izolatorJson;
	}
	
	public void getAsOsmXmlElement(Element parent, boolean isStupZatezni) {
		this.sti.getAsOsmXmlElement(parent, isStupZatezni);
		this.stv.getAsOsmXmlElement(parent, isStupZatezni);
		
		List<TockaPrikazaIzolatora> tockePrikaza;
		
		// Ako je stup zatezni, njegovi izolatori prikazuju se kao pravokutnici (OSM ne podrzava ikone oblika pravokutnika)
		// Ako je stup nosivi, njegovi izolatori prikazuju se kao krugovi (OSM podrzava ikone oblika kruga)
		if(isStupZatezni) {			
			// Odrediti vrhove pravokutnika izolatora
			tockePrikaza = izracunajVrhovePravokutnika();
		} else {
			// Odrediti tocke kruznice izolatora
			tockePrikaza = izracunajTockeNaKruznici();
		}
		
		PrikazIzolatora prikazIzolatora = new PrikazIzolatora(this, tockePrikaza);
		
		prikazIzolatora.getAsOsmXmlElement(parent);
	}
	
	/**
	 * Geo. širina izolatora dobiva se kao aritmeti�?ka sredina geo. širine STI i geo. širine STV
	 * Geo. dužina izolatora dobiva se kao arirmeti�?ka sredina geo. dužine STI i geo. dužine STV
	 */
	public void updateLatLong() {
		this.geoSirina = Math.round(
				((this.sti.getGeoSirina() + this.stv.getGeoSirina()) / 2) * DECIMALNO_MJESTO) / DECIMALNO_MJESTO;
		this.geoDuzina = Math.round(
				((this.sti.getGeoDuzina() + this.stv.getGeoDuzina()) / 2) * DECIMALNO_MJESTO) / DECIMALNO_MJESTO;
	}
	
	@Override
	public String toString() {
		return String.format("---[IZOLATOR] ID: %d\n", this.idIzolatora);
	}

}
