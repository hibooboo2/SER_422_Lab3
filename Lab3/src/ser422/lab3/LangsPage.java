
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class langs
 */
@WebServlet("/langs")
public class LangsPage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LangsPage()
	{

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// TODO Auto-generated method stub
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.setContentType("text/html");
		for (Cookie coo : request.getCookies())
		{
			response.addCookie(coo);
		}
		PrintWriter out= response.getWriter();
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Langs Page</title>");
			out.println("<style>{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif;}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>What programming languages you know?</h2>");
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
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"otherlang\">Other<br>");
			out.println("<input type=\"submit\" name=\"nav\" value=\"Back to Last Name Page\">");
			out.println("<input type=\"submit\" name=\"nav\" value=\"To Days Page\">");
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
			if (request.getParameter("nav").equalsIgnoreCase("Back to Last Name Page"))
			{
				response.sendRedirect("/Lab3/lastName");
			}
			if (request.getParameter("nav").equalsIgnoreCase("To Days Page"))
			{
				for (Cookie coo : request.getCookies())
				{
					response.addCookie(coo);
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
				response.sendRedirect("/Lab3/Days");
			}
		}

	}
}
