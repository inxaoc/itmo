package edu.itmo.pbks.fruits.model;

public class Milk {
	
	public int bottles;
	//litres
	public double totalCapacity;

	public void calculateWeight(int bottles){
		totalCapacity = bottles*0.8;
	}
	
}
