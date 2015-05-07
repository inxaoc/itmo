package edu.itmo.pbks.fruits.model;

public class Fridge {
	
	public String model;
	public String name;
	public double frozenCapacity;
	public int bottlesCapacity;
	public double shelfCapacity;
	
	public Double checkFrozenCapacity(Meat meat){
		if(meat.weight<frozenCapacity){
			return meat.weight;
		} else {
			return null;
		}
	}
	
	public Integer checkBottlesCapacity(Milk milk){
		if(milk.bottles<bottlesCapacity){
			return milk.bottles;
		} else {
			return null;
		}
	}
	
	public Double checkShelfCapacity(Fruit fruit, Cheese cheese){
		if((fruit.totalweight+(cheese.weight/1000))<shelfCapacity){
			return fruit.totalweight+cheese.weight;
		} else {
			return null;
		}
	}

}
