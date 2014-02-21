package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Days
 */
@WebServlet("/Days")
public class DaysPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DaysPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext sc= this.getServletContext();
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.setContentType("text/html");
		Map<String,String[]> cookiesMap= new HashMap<String,String[]>();
		if (request.getCookies() != null)
		{
			for (Cookie coo : request.getCookies())
			{
				cookiesMap.put(coo.getName(), coo.getValue().split(":"));
				response.addCookie(coo);
			}
		}
		PrintWriter out= response.getWriter();
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
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>What days of the week you can meet?</h2>");
			LinkedHashSet<String> daysChoosen= new LinkedHashSet<String>();
			if (cookiesMap.containsKey("days") && cookiesMap.get("days").length > 0)
			{
				for (int i= 0; i < cookiesMap.get("days").length; i++)
				{
					daysChoosen.add(cookiesMap.get("days")[i]);
				}
			}
			out.println("<input type=\"checkbox\" name=\"days\" value=\"sun\"");
			if (daysChoosen.contains("sun"))
			{
				out.println("checked");
			}
			out.println(">Sunday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"mon\"");
			if (daysChoosen.contains("mon"))
			{
				out.println("checked");
			}
			out.println(">Monday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"tue\"");
			if (daysChoosen.contains("tue"))
			{
				out.println("checked");
			}
			out.println(">Tuesday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"wed\"");
			if (daysChoosen.contains("wed"))
			{
				out.println("checked");
			}
			out.println(">Wednesday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"thu\"");
			if (daysChoosen.contains("thu"))
			{
				out.println("checked");
			}
			out.println(">Thursday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"fri\"");
			if (daysChoosen.contains("fri"))
			{
				out.println("checked");
			}
			out.println(">Friday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"sat\"");
			if (daysChoosen.contains("sat"))
			{
				out.println("checked");
			}
			out.println(">Saturday<br>");

			out.println("<input type=\"submit\" name=\"nav\" value=\"Back To Langs Page\">");
			out.println("<input type=\"submit\" name=\"nav\" value=\"To Colors Page\">");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (request.getParameter("nav") != null)
		{
			if (request.getCookies() != null)
			{
				for (Cookie coo : request.getCookies())
				{
					response.addCookie(coo);
				}
			}
			Map<String,String[]> data= request.getParameterMap();
			for (String name : data.keySet())
			{
				String valueCombined= "";
				for (String value : data.get(name))
				{
					valueCombined+= value + ":";
				}
				response.addCookie(new Cookie(name, valueCombined));
			}
			if (request.getParameter("nav").equalsIgnoreCase("To Colors Page"))
			{
				response.sendRedirect("/Lab3/Colors");
			}
			else if (request.getParameter("nav").equalsIgnoreCase("Back To Langs Page"))
			{
				response.sendRedirect("/Lab3/langs");
			}
		}
	}

}
