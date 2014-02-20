
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ColorPage
 */
@WebServlet("/Colors")
public class ColorPage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ColorPage()
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
		ServletContext sc= this.getServletContext();
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.setContentType("text/html");
		try
		{
			for (Cookie coo : request.getCookies())
			{
				response.addCookie(coo);
			}
		}
		catch (NullPointerException e)
		{
			response.sendError(response.SC_BAD_REQUEST);
		}
		PrintWriter out= response.getWriter();
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 2</title>");
			out.println("<style>{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif;}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>What is your favorite color?</h2>");
			out.println("<input type=\"text\" name=\"color\" value=\"none\"><br>");
			out.println("<input type=\"submit\" name=\"nav\" value=\"Back To Days\">");
			out.println("<input type=\"submit\" name=\"nav\" value=\"Submit\">");
			out.println("<input type=\"submit\" name=\"nav\" value=\"Cancel\">");
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
		if (request.getParameter("nav") != null ) {
			if ( request.getParameter("nav").equalsIgnoreCase("Back To Days"))
			{
				if (request.getParameter("nav").equalsIgnoreCase("Back To Days"))
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
			else if (request.getParameter("nav").equalsIgnoreCase("Submit"))
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
				response.addCookie(new Cookie("alreadyRegisetered", "true"));
				response.addCookie(new Cookie("action", "adduser:"));
				response.sendRedirect("/Lab3/");
			}
			else if (request.getParameter("nav").equalsIgnoreCase("Cancel"))
			{
				if (request.getCookies() != null)
				{
					for (Cookie coo : request.getCookies())
					{
						coo.setMaxAge(0);
						response.addCookie(coo);
					}
				}
				response.addCookie(new Cookie("userCreationCookiesCleared", "true:"));
				response.sendRedirect("/Lab3/");
			}


		}
	}
}
