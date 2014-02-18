
package ser422.lab3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletsTask1
 */
@WebServlet("/ServletsTask1")
public class ServletsTask1 extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletsTask1()
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
		super.init(config);
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
		Map<String,String[]> query= request.getParameterMap();
		UserContainer userCont= null;
		try
		{
			userCont= UserContainer.getContainer("Users.saved");
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			response.sendError(500);
		}
		Vector<User> validUsers= userCont.queryUsers(query);
		PrintWriter out= response.getWriter();
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 1</title>");
			out.println("<style>{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif;}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			for (User u : validUsers)
			{
				out.println(u.toString());
			}
			out.println("<h2>Your name</h2>");
			out.println("First name: <input type=\"text\" name=\"firstname\"><br>");
			out.println("Last name: <input type=\"text\" name=\"lastname\">");
			out.println("<h2>Programming languages you know</h2>");
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
			out.println("<input type=\"checkbox\" name=\"langs\" value=\"otherlang\">Other");
			out.println("<h2>Days of the week you can meet</h2>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"sun\">Sunday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"mon\">Monday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"tue\">Tuesday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"wed\">Wednesday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"thu\">Thursday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"fri\">Friday<br>");
			out.println("<input type=\"checkbox\" name=\"days\" value=\"sat\">Saturday");
			out.println("<h2>Your favorite color</h2>");
			out.println("<input type=\"text\" name=\"favcolor\"><br>");
			out.println("<input type=\"submit\" value=\"Submit\">");
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
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		res.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.addHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", -1);
		res.setContentType("text/html");
		Map<String,String[]> formData= req.getParameterMap();
		UserContainer userCont= null;
		try
		{
			userCont= UserContainer.getContainer("Users.saved");
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			res.sendError(500);
		}
		userCont.addUser(new User(formData));
		PrintWriter out= res.getWriter();
		try
		{

			out.println("POST~!<BR> ");
			out.println("<a href=\"" + req.getHeader("referer") + "\"/>test</a>");

		}
		finally
		{
			out.close();
		}
	}
}
