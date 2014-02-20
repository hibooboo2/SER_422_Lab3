
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class firstName
 */
@WebServlet("/firstName")
public class FirstNamePage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FirstNamePage()
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

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		ServletContext sc= this.getServletContext();
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		response.setContentType("text/html");
		if (request.getParameter("nav") != null && request.getParameter("nav").equalsIgnoreCase("To First Name Page"))
		{
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
					out.println("<body bgcolor=\"pink\"><form method=\"get\">");
					out.println("<h2>Enter First Name Please:</h2>");
					out.println("<input type=\"text\" name=\"firstname\"><br>");
					out.println("<input type=\"submit\" name=\"nav\" value=\"Landing Page\">");
					out.println("<input type=\"submit\" name=\"nav\" value=\"To Last Name Page\">");
					out.println("</form></body>");
					out.println("</html>");
				}
				finally
				{
					out.close();
				}
			}
		}
		else if (request.getParameter("nav") != null)
		{
			RequestDispatcher rd= null;
			if (request.getParameter("nav").equalsIgnoreCase("To Last Name Page"))
			{
				rd= sc.getRequestDispatcher("/lastName");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// TODO Auto-generated method stub
	}
}
