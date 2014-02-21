package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/searchSettingsPage")
public class searchSettingPage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public searchSettingPage()
	{

		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		Map<String,String[]> cookiesMap= new HashMap<String,String[]>();
		if (request.getCookies() != null)
		{
			for (Cookie coo : request.getCookies())
			{
				cookiesMap.put(coo.getName(), coo.getValue().split(":"));
			}
		}
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 2</title>");
			if (request.getHeader("User-Agent").indexOf("Mobile") != -1)
			{
				out.println("<style>body{font-size:8pt}</style>");
			}
			else
			{
				out.println("<style>body{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif; font-size:12pt}</style>");
			}
			out.println("</head>");
			out.println("<body>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>Names To search for?</h2>");
			out.println("First name: <input type=\"text\" name=\"firstname\"><br>");
			out.println("Last name: <input type=\"text\" name=\"lastname\">");
			out.println("<h2>Programming languages to Search for?</h2>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"java\">Java<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"c\">C<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"cpp\">C++<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"objc\">Objective-C<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"csharp\">C#<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"php\">PHP<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"perl\">Perl<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"python\">Python<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"js\">JavaScript<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"scala\">Scala<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"scheme\">Scheme<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"prolog\">Prolog<br>");
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"otherlang\">Other");
			out.println("<h2>Days of the week you can meet</h2>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"sun\">Sunday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"mon\">Monday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"tue\">Tuesday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"wed\">Wednesday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"thu\">Thursday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"fri\">Friday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"sat\">Saturday");
			out.println("<h2>Favorite Color to search for?</h2>");
			out.println("<input type=\"text\" name=\"color\"><br>");
			out.println("<input type=\"submit\" value=\"Submit\">");
			out.println("</form>");
			out.println("</body>");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Map<String,String[]> queryData= request.getParameterMap();
		String newqueryString= "";
		for (String name : queryData.keySet())
		{
			newqueryString += name +"=";
			for (String value : queryData.get(name))
			{
				newqueryString+= value + "+";
			}
			newqueryString+= "&";
		}
		response.addCookie(new Cookie("search", newqueryString));
		response.sendRedirect("/Lab3/");
	}
}
