
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		if (request.getParameter("nav") != null && request.getParameter("nav").equalsIgnoreCase("To Colors Page"))
		{
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
				out.println("<input type=\"text\" name=\"color\"><br>");
				out.println("<input type=\"submit\" name=\"naves\" value=\"Back To Langs\">");
				out.println("<input type=\"submit\" name=\"nav\" value=\"Submit\">");
				out.println("</form></body>");
				out.println("</html>");
			}
			finally
			{
				out.close();
			}
		}
		else if (request.getParameter("nav") != null)
		{
			RequestDispatcher rd= null;
			if (request.getParameter("nav").equalsIgnoreCase("Back To Days"))
			{
				rd= sc.getRequestDispatcher("/Days");
			}
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
}
