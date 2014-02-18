package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletsTask1
 */
@WebServlet("/ServletsTask1")
public class ServletsTask1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletsTask1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 1</title>");
			out.println("<style>{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif;}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>Your name</h2>");
			out.println("First name: <input type=\"text\" name=\"firstname\"><br>");
			out.println("Last name: <input type=\"text\" name=\"lastname\">");
			out.println("<h2>Programming languages you know</h2>");
			out.println("<input type=\"checkbox\" name=\"java\">Java<br>");
			out.println("<input type=\"checkbox\" name=\"c\">C<br>");
			out.println("<input type=\"checkbox\" name=\"cpp\">C++<br>");
			out.println("<input type=\"checkbox\" name=\"objc\">Objective-C<br>");
			out.println("<input type=\"checkbox\" name=\"csharp\">C#<br>");
			out.println("<input type=\"checkbox\" name=\"php\">PHP<br>");
			out.println("<input type=\"checkbox\" name=\"perl\">Perl<br>");
			out.println("<input type=\"checkbox\" name=\"python\">Python<br>");
			out.println("<input type=\"checkbox\" name=\"js\">JavaScript<br>");
			out.println("<input type=\"checkbox\" name=\"scala\">Scala<br>");
			out.println("<input type=\"checkbox\" name=\"scheme\">Scheme<br>");
			out.println("<input type=\"checkbox\" name=\"prolog\">Prolog<br>");
			out.println("<input type=\"checkbox\" name=\"otherlang\">Other");
			out.println("<h2>Days of the week you can meet</h2>");
			out.println("<input type=\"checkbox\" name=\"sun\">Sunday<br>");
			out.println("<input type=\"checkbox\" name=\"mon\">Monday<br>");
			out.println("<input type=\"checkbox\" name=\"tue\">Tuesday<br>");
			out.println("<input type=\"checkbox\" name=\"wed\">Wednesday<br>");
			out.println("<input type=\"checkbox\" name=\"thu\">Thursday<br>");
			out.println("<input type=\"checkbox\" name=\"fri\">Friday<br>");
			out.println("<input type=\"checkbox\" name=\"sat\">Saturday");
			out.println("<h2>Your favorite color</h2>");
			out.println("<input type=\"radio\" name=\"red\">Red<br>");
			out.println("<input type=\"radio\" name=\"blue\">Blue<br>");
			out.println("<input type=\"radio\" name=\"green\">Green<br>");
			out.println("<input type=\"radio\" name=\"yellow\">Yellow<br>");
			out.println("<input type=\"radio\" name=\"orange\">Orange<br>");
			out.println("<input type=\"radio\" name=\"purple\">Purple<br>");
			out.println("<input type=\"radio\" name=\"pink\">Pink<br>");
			out.println("<input type=\"radio\" name=\"brown\">Brown<br>");
			out.println("<input type=\"radio\" name=\"black\">Black<br>");
			out.println("<input type=\"radio\" name=\"white\">White<br>");
			out.println("<input type=\"radio\" name=\"gray\">Gray<br>");
			out.println("<input type=\"radio\" name=\"othercolor\">Other<br>");
			out.println("<input type=\"submit\" value=\"Submit\">");
			out.println("</form></body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		res.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.addHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", -1);
		res.setContentType("text/html");
		PrintWriter out= res.getWriter();
		try
		{

			out.println("POST~!<BR>");

		}
		finally
		{
			out.close();
		}
	}

}
