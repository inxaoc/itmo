package edu.itmo.pbks.fruits.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.itmo.pbks.fruits.model.Cheese;
import edu.itmo.pbks.fruits.model.Fridge;
import edu.itmo.pbks.fruits.model.Fruit;
import edu.itmo.pbks.fruits.model.Meat;
import edu.itmo.pbks.fruits.model.Milk;

public class CapacityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");

		boolean errors = false;

		Fridge[] fridges = createFridges();

		String fridgeStr = req.getParameter("fridge");
		Fridge fridge = null;
		for (int i = 0; i < fridges.length; i++) {
			if (fridges[i].model.equals(fridgeStr)) {
				fridge = fridges[i];
				break;
			}
		}

		if (fridge == null) {
			resp.getWriter().println(
					"<div class=\"bad\">Не выбран холодильник.</div>");
			errors = true;
		}

		String meatWeight = req.getParameter("meat_weight");
		Meat meat = new Meat();
		if (meatWeight.isEmpty()) {
			resp.getWriter().println(
					"<div class=\"bad\">Задайте количество фруктов.</div>");
			errors = true;
		} else {
			Double meatW = Double.parseDouble(meatWeight);
			meat.weight = meatW;
		}
		
		String fruitType = req.getParameter("fruit_type");
		if (fruitType.isEmpty()) {
			resp.getWriter().println(
					"<div class=\"bad\">Выберите тип фруктов.</div>");
			errors = true;
		}

		String fruitAmout = req.getParameter("fruit_amout");
		Fruit fruit = new Fruit();
		if (fruitType.isEmpty()) {
			resp.getWriter().println(
					"<div class=\"bad\">Задайте количество фруктов.</div>");
			errors = true;
		} else {
			Integer fruitA = Integer.parseInt(fruitAmout);
			fruit.type = fruitType;
			fruit.amount = fruitA;
			fruit.calculateWeight();
		}

		String milkAmount = req.getParameter("milk_amount");
		Milk milk = new Milk();
		if (milkAmount.isEmpty()) {
			resp.getWriter().println(
					"<div class=\"bad\">Задайте количество бутылок.</div>");
			errors = true;
		} else {
			Integer milkA = Integer.parseInt(milkAmount);
			milk.bottles = milkA;
			milk.calculateWeight(milk.bottles);
		}

		String cheeseGram = req.getParameter("cheese_gram");
		Cheese chees = new Cheese();
		if (cheeseGram.isEmpty()) {
			resp.getWriter().println(
					"<div class=\"bad\">Задайте массу сыра.</div>");
			errors = true;
		} else {
			Double cheeseG = Double.parseDouble(cheeseGram);
			chees.weight = cheeseG;
		}
		if (errors) {
			return;
		}

		// формирование ответа
		resp.getWriter().println("<div class=\"success\">");
		resp.getWriter().println(
				"<div>Вы выбрали <span class=\"holod\">" + fridge.name
						+ "</span>. " + "<br>Выбранные продукты: молоко "
						+ milk.bottles + " бутылок (общий объем "
						+ milk.totalCapacity + ")" + ", мясо " + meat.weight
						+ "кг." + ", фрукты (" + fruit.type + " "
						+ fruit.totalweight + "кг.)" + ", сыр " + chees.weight
						+ "гр.</div>");

		resp.getWriter().println("<ul>");
		Integer bot = fridge.checkBottlesCapacity(milk);
		if (bot == null) {
			resp.getWriter()
					.println(
							"<li class=\"bad\">Данный холодильник не вместит все бутылки молока (можно разместить только "
									+ fridge.bottlesCapacity
									+ " бутылок).</li>");
		} else {
			resp.getWriter().println(
					"<li class=\"well\">Бутылки в холодильник влезут.</li>");
		}

		Double frozen = fridge.checkFrozenCapacity(meat);
		if (frozen == null) {
			resp.getWriter()
					.println(
							"<li class=\"bad\">Данный холодильник не вместит выбранный объем мяса (вместимость морозильника "
									+ fridge.frozenCapacity + " кг.)</li>");
		} else {
			resp.getWriter().println(
					"<li class=\"well\">Мясо в холодильник влезет.</li>");
		}

		Double shelf = fridge.checkShelfCapacity(fruit, chees);
		if (shelf == null) {
			resp.getWriter()
					.println(
							"<li class=\"bad\">Данный холодильник не вместит общий объем сыра и фруктов (вместимость полки холодильника "
									+ fridge.shelfCapacity + " кг.)</li>");
		} else {
			resp.getWriter()
					.println(
							"<li class=\"well\">Фрукты и сыр в холодильник влезут.</li>");
		}

		resp.getWriter().println("</ul>");
		resp.getWriter().println("</div>");
	}

	private Fridge[] createFridges() {
		Fridge[] fridges = new Fridge[3];

		Fridge holod = new Fridge();
		holod.model = "holod";
		holod.name = "Холодильник Деда Мороза 2025";
		holod.bottlesCapacity = 5;
		holod.frozenCapacity = 20;
		holod.shelfCapacity = 10;
		fridges[0] = holod;

		Fridge cow = new Fridge();
		cow.model = "cow";
		cow.name = "Холодильник Буренки 33";
		cow.bottlesCapacity = 20;
		cow.frozenCapacity = 2;
		cow.shelfCapacity = 10;
		fridges[1] = cow;

		Fridge vegan = new Fridge();
		vegan.model = "cow";
		vegan.name = "Холодильник Буренки 33";
		vegan.bottlesCapacity = 5;
		vegan.frozenCapacity = 5;
		vegan.shelfCapacity = 20;
		fridges[2] = vegan;

		return fridges;
	}

}
