package edu.itmo.pbks.io;

import java.util.ArrayList;

public class Utils {

	public static District getDistrictByName(ArrayList<District> districts, String name) {
		
		for(District dist : districts ){
			if(name.toLowerCase().equals(dist.getTitle().toLowerCase())){
				return dist;
			}
		}
		
		return districts.get(0);
	}

	public static String getDistrictNameByOkato(ArrayList<District> districts,
			Integer okato) {
		for (District dist : districts){
			if(okato.equals(dist.getOkato())){
				return dist.getTitle();
			}
		}
		return "Неизвестный";
	}

	
	
}
