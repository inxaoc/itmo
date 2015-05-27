package edu.itmo.pbks.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DistrictServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		try {
//			URL url = new URL("http://localhost:8888/io/districts.csv");
			//Надо заменить URL на ваш при загрузке на AppEngine, например 
			URL url = new URL("http://examples-web.appspot.com/io/districts.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(),"UTF-8"));
			String line;

			resp.getWriter().println("<h1>Список всех районов</h1>");
			
			resp.getWriter().println("<table border=1 cellspacing=0 cellpadding=5><tr><td>Название района</td><td>ОКАТО</td><td>Код района</td></tr>");
			for (int i = 0; (line = reader.readLine()) != null; i++) {
				if (i > 0) {
					String[] values = line.split(",");
					 resp.getWriter().println("<tr><td><a href=\"readFile?okato="+values[1]+"\">"+values[0]+"</a></td><td>"+values[1]+"</td><td>"+values[2]+"</td>");
				}
			}
			reader.close();
			resp.getWriter().println("</table>");

		} catch (IOException e) {
			resp.getWriter().println("Не могу прочитать файл.");
		}
	}

}
