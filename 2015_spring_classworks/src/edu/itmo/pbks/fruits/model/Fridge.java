package edu.itmo.pbks.fruits.model;

public class Fridge {
	
	public String model;
	public String name;
	public double frozenCapacity;
	public int bottlesCapacity;
	public double shelfCapacity;
	
	/**
	 * Принимает объект Мясо, в котором должен быть сохранен вес.
	 * Если вес больше, чем может вместить, то возвращается null.
	 * Null надо проверять в вызываемом классе.
	 * @param meat
	 * @return
	 */
	public Double checkFrozenCapacity(Meat meat){
		if(meat.weight<=frozenCapacity){
			return meat.weight;
		} else {
			return null;
		}
	}
	
	/**
	 * Принимает объект Молоко, в котором должно быть сохранено количество бутылок.
	 * Если их количество больше, чем может вместить, то возвращается null.
	 * Null надо проверять в вызываемом классе.
	 * @param milk
	 * @return
	 */
	public Integer checkBottlesCapacity(Milk milk){
		if(milk.bottles<=bottlesCapacity){
			return milk.bottles;
		} else {
			return null;
		}
	}
	
	/**
	 * Принимает объекты Фрукты и Сыр, в которых должны быть сохранены веса.
	 * Если суммарный вес больше, чем может вместить, то возвращается null.
	 * Null надо проверять в вызываемом классе.
	 * @param fruit
	 * @param cheese
	 * @return
	 */
	public Double checkShelfCapacity(Fruit fruit, Cheese cheese){
		if((fruit.totalweight+(cheese.weight/1000))<=shelfCapacity){
			return fruit.totalweight+cheese.weight;
		} else {
			return null;
		}
	}

}
