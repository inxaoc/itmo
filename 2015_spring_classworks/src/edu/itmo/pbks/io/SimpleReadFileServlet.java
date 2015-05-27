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

public class SimpleReadFileServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		Integer okato = Integer.parseInt(req.getParameter("okato"));

		ArrayList<District> districts = new ArrayList<District>();
		District dist0 = new District();
		dist0.setTitle("Неизвестный");
		dist0.setOkato(0);
		dist0.setCode(0);

		districts.add(dist0);

		ArrayList<Plan> plans = new ArrayList<Plan>();

		try {
//			URL url = new URL("http://localhost:8888/io/districts.csv");
			//Надо заменить URL на ваш при загрузке на AppEngine, например 
			URL url = new URL("http://examples-web.appspot.com/io/districts.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(),"UTF-8"));
			String line;

			for (int i = 0; (line = reader.readLine()) != null; i++) {
				if (i > 0) {
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
			resp.getWriter().println("Не могу прочитать файл.");
		}

		if (!districts.isEmpty()) {

			try {
//				URL url = new URL("http://localhost:8888/io/plans.csv");
				//Надо заменить URL на ваш при загрузке на AppEngine, например 
				URL url = new URL("http://examples-web.appspot.com/io/plans.csv");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(url.openStream(),"UTF-8"));

				CSVReader csv = new CSVReader(reader);
				List<String[]> rows = csv.readAll();

				for (String[] row : rows) {

					Plan plan = new Plan();
					plan.setAddress(row[1]);
					plan.setDistrict(Utils.getDistrictByName(districts, row[2]));
					plans.add(plan);

				}
				reader.close();

			} catch (IOException e) {
				resp.getWriter().println("Не могу прочитать файл.");
			}

			if (!plans.isEmpty()) {

				resp.getWriter()
						.println(
								"Следующие проекты планировок запланированы для района <b>"
										+ Utils.getDistrictNameByOkato(
												districts, okato) + "</b>");
				resp.getWriter().println("<ul>");

				for (Plan plan : plans) {
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
