package hr.fer.pavlic.dipl.utmwgstransf;

public class UtmWgsConverter {
	
	private static final double equatorialRadius = 6378137;
	private static final double polarRadius = 6356752.314;
	private static final double e = Math.sqrt(1 - Math.pow(polarRadius / equatorialRadius, 2));
    private static double S = 5103266.421;
    private static final double A0 = 6367449.146;
    private static final double B0 = 16038.42955;
    private static final double C0 = 16.83261333;
    private static final double D0 = 0.021984404;
    private static final double E0 = 0.000312705;
    private static final double k0 = 0.9996;
    private static final double sin1 = 4.84814E-06;
    private static final double e1sq = 0.006739497;
    private static double nu;
    private static double K1;
    private static double K2;
    private static double K3;
    private static double K4;
    private static double K5;
    private static double p;
    private static double phi1;
    private static double fact1;
    private static double fact2;
    private static double fact3;
    private static double fact4;
    private static double zoneCM;
    private static double _a3;
    private static final String southernHemisphere = "ACDEFGHJKLM";
    private static double ei;
    private static double ca;
    private static double cb;
    private static double cc;
    private static double cd;
    private static double n0;
    private static double r0;
    private static double _a1;
    private static double dd0;
    private static double t0;
    private static double Q0;
    private static double lof1;
    private static double lof2;
    private static double lof3;
    private static double _a2;
    private static final double a = 6378137;
    
    public static UtmCoordinate convertToUtm(WgsCoordinate wgsCoordinate) throws Exception {
		
    	double latitude = wgsCoordinate.getGeoSirina();
		double longitude = wgsCoordinate.getGeoDuzina();
		
		validate(latitude, longitude);
		setVariablesWgsToUtm(latitude, longitude);
		
		String longZone = getLongZone(longitude);
		LatZones latZones = new LatZones();
		String latZone = latZones.getLatZone(latitude);
		
		double easting = getEasting();
		double northing = getNorthing(latitude);
		
		return new UtmCoordinate(longZone, latZone, easting, northing);
		
	}
    
    public static WgsCoordinate convertToWgs(UtmCoordinate utmCoordinate) {
		
		int longZone = Integer.parseInt(utmCoordinate.getLongZone());
		String latZone = utmCoordinate.getLatZone();
		double easting = utmCoordinate.getEasting();
		double northing = utmCoordinate.getNorthing();
		String hemisphere = getHemisphere(latZone);
		double latitude = 0.0;
		double longitude = 0.0;
		
		if(hemisphere.equals("S")) {
			northing = 10000000 - northing;
		}
		
		setVariablesUtmToWgs(easting, northing);
		latitude = 180 * (phi1 - fact1 * (fact2 + fact3 + fact4)) / Math.PI;
		
		if(longZone > 0) {
			zoneCM = 6 * longZone - 183.0;
		} else {
			zoneCM = 3.0;
		}
		
		longitude = zoneCM - _a3;
		if(hemisphere.equals("S")) {
			latitude *= -1;
		}
		
		return new WgsCoordinate(latitude, longitude);
		
	}
    
    private static void validate(double latitude, double longitude) throws Exception {
		
		if(latitude < -90.0 || latitude > 90.0 || longitude < -180.0 || longitude > 180.0) {
			throw new Exception("Invalid coordinate.");
		}
		
	}
	
	private static void setVariablesWgsToUtm(double latitude, double longitude) {
		
		latitude = Math.toRadians(latitude);
		nu = equatorialRadius / Math.pow(1 - Math.pow(e * Math.sin(latitude), 2), 1/2.0);
		
		double var1;
		if(longitude < 0.0) {
			var1 = ((int)((180 + longitude)/6.0)) + 1;
		} else {
			var1 = ((int)(longitude/6)) + 31;
		}
		
		double var2 = 6 * var1 - 183;
		double var3 = longitude - var2;
		p = var3 * 3600 / 10000;
		
		S = A0 * latitude - B0 * Math.sin(2 * latitude) + C0 * Math.sin(4 * latitude) - D0
				* Math.sin(6 * latitude) + E0 * Math.sin(8 * latitude);
		
		K1 = S * k0;
		K2 = nu * Math.sin(latitude) * Math.cos(latitude) * Math.pow(sin1, 2) * k0 * (100000000) / 2;
		K3 = ((Math.pow(sin1, 4) * nu * Math.sin(latitude) * Math.pow(Math.cos(latitude), 3)) / 24)
		      * (5 - Math.pow(Math.tan(latitude), 2) + 9 * e1sq * Math.pow(Math.cos(latitude), 2) + 4
		      * Math.pow(e1sq, 2) * Math.pow(Math.cos(latitude), 4))
		      * k0
		      * (10000000000000000L);
		 
		K4 = nu * Math.cos(latitude) * sin1 * k0 * 10000;
		 
		K5 = Math.pow(sin1 * Math.cos(latitude), 3) * (nu / 6)
		      * (1 - Math.pow(Math.tan(latitude), 2) + e1sq * Math.pow(Math.cos(latitude), 2)) * k0
		      * 1000000000000L;
		
	}
	
	private static String getLongZone(double longitude) {
		
		double longZone = 0;
		if(longitude < 0.0) {
			longZone = ((180.0 + longitude) / 6) + 1;
		} else {
			longZone = (longitude / 6) + 31;
		}
		
		String val = String.valueOf((int)longZone);
		if(val.length() == 1) {
			val = "0" + val;
		}
		
		return val;
		
	}
	
	private static double getEasting() {
		return 500000 + (K4 * p + K5 * Math.pow(p, 3));	
	}
	
	private static double getNorthing(double latitude) {
		
		double northing = K1 + K2 * p * p + K3 * Math.pow(p, 4);
		if (latitude < 0.0) {
			northing = 10000000 + northing;
		}
		 
		return northing;
	}
	
	private static String getHemisphere(String latZone) {
		return southernHemisphere.indexOf(latZone) > -1 ? "S" : "N";
	}
	
	private static void setVariablesUtmToWgs(double easting, double northing) {
		double arc = northing / k0;
	    double mu = arc / (a * (1 - Math.pow(e, 2) / 4.0 - 3 * Math.pow(e, 4) / 64.0 - 5 * Math.pow(e, 6) / 256.0));

	    ei = (1 - Math.pow((1 - e * e), (1 / 2.0))) / (1 + Math.pow((1 - e * e), (1 / 2.0)));

	    ca = 3 * ei / 2 - 27 * Math.pow(ei, 3) / 32.0;

	    cb = 21 * Math.pow(ei, 2) / 16 - 55 * Math.pow(ei, 4) / 32;
	    cc = 151 * Math.pow(ei, 3) / 96;
        cd = 1097 * Math.pow(ei, 4) / 512;
	    phi1 = mu + ca * Math.sin(2 * mu) + cb * Math.sin(4 * mu) + cc * Math.sin(6 * mu) + cd * Math.sin(8 * mu);

	    n0 = a / Math.pow((1 - Math.pow((e * Math.sin(phi1)), 2)), (1 / 2.0));

	    r0 = a * (1 - e * e) / Math.pow((1 - Math.pow((e * Math.sin(phi1)), 2)), (3 / 2.0));
	    fact1 = n0 * Math.tan(phi1) / r0;

	    _a1 = 500000 - easting;
	    dd0 = _a1 / (n0 * k0);
	    fact2 = dd0 * dd0 / 2;

	    t0 = Math.pow(Math.tan(phi1), 2);
	    Q0 = e1sq * Math.pow(Math.cos(phi1), 2);
	    fact3 = (5 + 3 * t0 + 10 * Q0 - 4 * Q0 * Q0 - 9 * e1sq) * Math.pow(dd0, 4) / 24;

	    fact4 = (61 + 90 * t0 + 298 * Q0 + 45 * t0 * t0 - 252 * e1sq - 3 * Q0 * Q0) * Math.pow(dd0, 6) / 720;

	    lof1 = _a1 / (n0 * k0);
	    lof2 = (1 + 2 * t0 + Q0) * Math.pow(dd0, 3) / 6.0;
	    lof3 = (5 - 2 * Q0 + 28 * t0 - 3 * Math.pow(Q0, 2) + 8 * e1sq + 24 * Math.pow(t0, 2)) * Math.pow(dd0, 5) / 120;
	    _a2 = (lof1 - lof2 + lof3) / Math.cos(phi1);
	    _a3 = _a2 * 180 / Math.PI;
	}

}
