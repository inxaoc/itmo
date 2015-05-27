package edu.itmo.pbks.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Этот сервлет связан со страницей вида: http://examples-web.appspot.com/readFile?okato=40265
 * На нее пользователь может перейти со страницы списка всех районов вида: http://examples-web.appspot.com/districts
 * @author inxaoc
 *
 */
public class SimpleReadFileServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		//код района, по которому необходимо найти все планировки
		Integer okato = Integer.parseInt(req.getParameter("okato"));

		//создаем коллекцию всех районов (из файла districts.csv)
		ArrayList<District> districts = new ArrayList<District>();
		
		//создаем специальный объект района "Неизвестный", который будет использован если не удастся определить район корректно из исходного файла
		District dist0 = new District();
		dist0.setTitle("Неизвестный");
		dist0.setOkato(0);
		dist0.setCode(0);

		districts.add(dist0);

		//коллекция всех планировок (из файла plans.csv)
		ArrayList<Plan> plans = new ArrayList<Plan>();

		try {
			//TODO ВНИМАНИЕ! Перед загрузкой на AppEngine замените этот адрес на ваш URL (см. закомментированный код ниже)
			URL url = new URL("http://localhost:8888/io/districts.csv");
			//TODO URL url = new URL("http://examples-web.appspot.com/io/districts.csv");
			
			
			//обязательно укажите кодировку иначе на сервере AppEngine будут проблемы с кодировкой
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(),"UTF-8"));
			
			String line;

			for (int i = 0; (line = reader.readLine()) != null; i++) {
				if (i > 0) {
					//парсинг данных строки из CSV происходит c помощью метода String.split(), то есть разделением строки по символу ","
					//такой способ подходит только для простых файлов, лучше использовать OpenCSV
					String[] values = line.split(",");
					District dist = new District();
					dist.setTitle(values[0]);
					dist.setOkato(Integer.parseInt(values[1]));
					dist.setCode(Integer.parseInt(values[2]));
					districts.add(dist);
				}
			}
			reader.close();

		} catch (IOException e) {
			resp.getWriter().println("Не могу прочитать файл районов.");
		}

		if (!districts.isEmpty()) {

			try {
				//TODO аналогично выше
				URL url = new URL("http://localhost:8888/io/plans.csv");
//				URL url = new URL("http://examples-web.appspot.com/io/plans.csv");
				
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(url.openStream(),"UTF-8"));

				//не забудьте подключить библиотеку OpenCSV, скачать ее можно тут: http://opencsv.sourceforge.net/
				CSVReader csv = new CSVReader(reader);
				List<String[]> rows = csv.readAll();

				for (String[] row : rows) {

					Plan plan = new Plan();
					plan.setAddress(row[1]);
					//находим объект района из коллекции районов
					plan.setDistrict(Utils.getDistrictByName(districts, row[2]));
					plans.add(plan);

				}
				reader.close();

			} catch (IOException e) {
				resp.getWriter().println("Не могу прочитать файл планировок.");
			}

			if (!plans.isEmpty()) {

				//с помощью специального метода получаем название района по его коду ОКАТО
				resp.getWriter()
						.println(
								"Следующие проекты планировок запланированы для района <b>"
										+ Utils.getDistrictNameByOkato(
												districts, okato) + "</b>");
				resp.getWriter().println("<ul>");

				for (Plan plan : plans) {
					//выводим только те планировки,и в которых код ОКАТО района совпадает с искомым
					if (okato.equals(plan.getDistrict().getOkato())) {
						resp.getWriter().println(
								"<li>" + plan.getAddress() + "</li>");
					}
				}

				resp.getWriter().println("</ul>");

			}

		}

	}

}
