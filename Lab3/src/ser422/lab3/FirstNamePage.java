
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
		Map<String,String[]> cookiesMap= new HashMap<String,String[]>();
		if (request.getCookies() != null)
		{
			for (Cookie coo : request.getCookies())
			{
				cookiesMap.put(coo.getName(), coo.getValue().split(":"));
				response.addCookie(coo);
			}
		}
		if (request.getCookies() == null
				|| (cookiesMap.containsKey("alreadyRegisetered") && !cookiesMap.get("alreadyRegisetered")[0].equalsIgnoreCase("true")))
		{
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
				out.println("<h2>Enter First Name Please:</h2>");
				if (cookiesMap.containsKey("firstname") && cookiesMap.get("firstname").length > 0)
				{
					out.println("First Name:<input type=\"text\" name=\"firstname\" value=\"" + cookiesMap.get("firstname")[0] + "\"><br>");
				}
				else
				{
					out.println("First Name: <input type=\"text\" name=\"firstname\"><br>");
				}
				out.println("<input type=\"submit\" name=\"nav\" value=\"Don't register, go to Landing Page\">");
				out.println("<input type=\"submit\" name=\"nav\" value=\"To Last Name Page\">");
				out.println("</form></body>");
				out.println("</html>");
			}
			finally
			{
				out.close();
			}
		}
		else if (cookiesMap.containsKey("alreadyRegisetered") && cookiesMap.get("alreadyRegisetered")[0].equalsIgnoreCase("true"))
		{
			response.sendRedirect(request.getContextPath() + "/");
		}
		else
		{
			if (request.getCookies() != null)
			{
				for (Cookie coo : request.getCookies())
				{
					response.addCookie(coo);
				}
				PrintWriter out= response.getWriter();
				out.println(cookiesMap.get("alreadyRegisetered")[0]);
				out.close();
				// response.sendError(response.SC_BAD_REQUEST);
			}
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
			if (request.getParameter("nav").equalsIgnoreCase("To Last Name Page"))
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
				response.addCookie(new Cookie("alreadyRegisetered", "registering"));
				response.sendRedirect(request.getContextPath() + "/lastName");
			}
			else if (request.getParameter("nav").equalsIgnoreCase("Don't register, go to Landing Page"))
			{
				if (request.getCookies() != null)
				{
					for (Cookie coo : request.getCookies())
					{
						if (!coo.getName().equalsIgnoreCase("alreadyRegisetered"))
						{
							coo.setMaxAge(0);
							response.addCookie(coo);
						}
					}
				}
				Cookie cookie= new Cookie("alreadyRegisetered", "dontRegister");
				response.addCookie(cookie);
				response.sendRedirect(request.getContextPath() + "/");
			}
		}

		// TODO Auto-generated method stub
	}
}
