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

@WebServlet("/search")
public class SearchPage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	public SearchPage()
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
		Map<String,String[]> query= request.getParameterMap();
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
			out.println("Programming languages: " + query.get("langs"));
			out.println("Days I can meet: " + query.get("days"));
			out.println("</body>");
			out.println("</html>");
		}
		finally
		{
			out.close();
		}
	}
}
