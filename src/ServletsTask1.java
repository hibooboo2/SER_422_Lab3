
package src;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletsTask1 extends HttpServlet
{

	private static final long	serialVersionUID	= -3764767856748158770L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		res.setContentType("text/html");
		PrintWriter out= res.getWriter();
		out.println("<html>");
		out.println("<head><title>Lab 3 Part 1</title></head>");
		out.println("<body><form method=\"post\">");
		out.println("<h2>Your name</h2>");
		out.println("First name: <input type=\"text\" name=\"firstname\"><br>");
		out.println("Last name: <input type=\"text\" name=\"lastname\">");
		out.println("<h2>Programming languages you know");
		out.println("<input type=\"checkbox\" name=\"java\">Java<br>");
		out.println("<input type=\"checkbox\" name=\"c\">C<br>");
		out.println("<input type=\"checkbox\" name=\"cpp\">C++<br>");
		out.println("<input type=\"checkbox\" name=\"objc\">Objective-C<br>");
		out.println("<input type=\"checkbox\" name=\"csharp\">C#<br>");
		out.println("<input type=\"checkbox\" name=\"php\">PHP<br>");
		out.println("<input type=\"checkbox\" name=\"perl\">Perl<br>");
		out.println("<input type=\"checkbox\" name=\"python\">Python<br>");
		out.println("<input type=\"checkbox\" name=\"scala\">Scala<br>");
		out.println("<input type=\"checkbox\" name=\"scheme\">Scheme<br>");
		out.println("<input type=\"checkbox\" name=\"prolog\">Prolog<br>");
		out.println("<input type=\"checkbox\" name=\"other\">Other<br>");
	}
}
