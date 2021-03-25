package hr.fer.pavlic.dipl.util;

import java.util.Random;
import java.util.UUID;

public class UidGenerator {
	
	public static String getUidString() {
		UUID uuid = UUID.randomUUID();
		
		Random random = new Random();
		
		String uidString =uuid.toString().replaceAll("[A-Za-z-]", 
				Integer.toString(random.nextInt(10 - 1 + 1) + 1));
		
		return uidString.substring(0, 15);
	}

}
