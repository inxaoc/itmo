package edu.itmo.pbks.io;

import java.util.ArrayList;

public class Utils {

	/**
	 * Этот метод из коллекции районов выбирает только тот район, который совпадает по названию из строки и возвращает Объект района
	 * @param districts
	 * @param name
	 * @return
	 */
	public static District getDistrictByName(ArrayList<District> districts, String name) {
		
		for(District dist : districts ){
			//приводим к нижнему регистру перед сравнением (некоторые файлы могут содержать названия районов с буквами в рахном регистре
			if(name.toLowerCase().equals(dist.getTitle().toLowerCase())){
				return dist;
			}
		}
		
		//если не найдено ни одного района, возвращается район "Неизвестный", который сохранен в качестве первого элемента коллекции
		return districts.get(0);
	}

	/**
	 * Возвращаем название района по его коду ОКАТО из коллекции районов  
	 * 
	 * @param districts
	 * @param okato
	 * @return
	 */
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
