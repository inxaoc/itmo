package edu.itmo.pbks.fruits.model;

public class Fruit {
	
	public Integer amount;
	public String type;
	
	//kg
	public Double totalweight;
	
	public Double calculateWeight(){
		if(amount == null || type == null) {
			System.out.print("Сначала запишите количество и тип в объект.");
		}
		if(type.equals("яблоко")){
			totalweight = amount*0.2;
		}
		if(type.equals("дыня")){
			totalweight = amount*1.0;
		}
		if(type.equals("груша")){
			totalweight = amount*0.3;
		}
		
		return totalweight;
	}

}
