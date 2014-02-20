
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class firstName
 */
@WebServlet("/userName")
public class UserNamePage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserNamePage()
	{

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{

		super.init(config);
		// TODO Auto-generated method stub
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
			out.println("<title>Lab3 Username</title>");
			out.println("<style>body{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif; font-size:12pt}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>Enter your desired username</h2>");
			out.println("Username: <input type=\"text\" name=\"username\"><br>");
			out.println("<input type=\"submit\" name=\"nav\" value=\"Back to Last Name Page\">");
			out.println("<input type=\"submit\" name=\"nav\" value=\"To Langs Page\" default>");
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
			if (request.getParameter("nav").equalsIgnoreCase("To Langs Page"))
			{
				response.sendRedirect("/Lab3/langs");
			}
			else if (request.getParameter("nav").equalsIgnoreCase("Back to Last Name Page"))
			{
				response.sendRedirect("/Lab3/lastName");
			}
		}

		// TODO Auto-generated method stub
	}
}
