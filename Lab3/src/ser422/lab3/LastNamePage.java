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
		if (request.getParameter("nav") != null
				&& (request.getParameter("nav").equalsIgnoreCase("Back to Last Name Page") || request.getParameter("nav").equalsIgnoreCase(
						"To Last Name Page")))
		{
			PrintWriter out= response.getWriter();
			try
			{
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Enter your last name</title>");
				out.println("<body bgcolor=\"pink\"><form method=\"get\">");
				out.println("<h2>Your last name</h2>");
				out.println("Last name: <input type=\"text\" name=\"lastname\">");
				out.println("<input type=\"submit\" name=\"nav\" value=\"Back to First Name Page\">");
				out.println("<input type=\"submit\" name=\"nav\" value=\"To Langs Page\" default>");
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
			if (request.getParameter("nav").equalsIgnoreCase("Back to First Name Page"))
			{
				response.sendRedirect("/firstName");
			}
			else if (request.getParameter("nav").equalsIgnoreCase("To Langs Page"))
			{
				response.sendRedirect("/langs");
			}
			// rd.forward(request, response);
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
