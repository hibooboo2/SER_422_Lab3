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
 * Servlet implementation class lastName
 */
@WebServlet("/lastName")
public class LastNamePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LastNamePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext sc= this.getServletContext();
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
			out.println("<title>Enter your last name</title>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>Your last name</h2>");
			out.println("Username: <input type=\"text\" name=\"lastname\"><br>");
			out.println("<input type=\"submit\" name=\"nav\" value=\"Back to First Name Page\">");
			out.println("<input type=\"submit\" name=\"nav\" value=\"To Username Page\" default>");
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
			if (request.getParameter("nav").equalsIgnoreCase("Back to First Name Page"))
			{
				response.sendRedirect("/Lab3/firstName");
			}
			else if (request.getParameter("nav").equalsIgnoreCase("To Username Page"))
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
				response.sendRedirect("/Lab3/userName");
			}
		}

	}
}
