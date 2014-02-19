
package ser422.lab3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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

	private static String		_filename			= null;

	UserContainer				userCont			= null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletsTask1()
	{

		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException
	{

		super.init(config);
		_filename= config.getInitParameter("userFile");
		this.userCont= new UserContainer(new LinkedHashSet<User>());
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
		PrintWriter out= response.getWriter();
		Map<String,String[]> query= request.getParameterMap();
		// TODO Make sure that this is in the right place and working. Keeps giving stream errors.
		// UserContainer userCont= null;
		// try
		// {
		// this.userCont= UserContainer.getContainer(sc.getResourceAsStream(_filename));
		// }
		// catch (ClassNotFoundException e)
		// {
		// response.sendError(500);
		// }
		LinkedHashSet<User> validUsers= null;
		if (!query.isEmpty())
		{
			validUsers= this.userCont.queryUsers(query);
		}
		else
		{
			validUsers= this.userCont.getUsers();
		}
		if (validUsers != null)
		{
			for (User u : validUsers)
			{
				out.println(u.toString());
			}
		}
		else
		{
			out.println("No valid Users!");
		}
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 1</title>");
			out.println("<style>{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif;}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
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
			out.println("<input type=\"text\" name=\"color\"><br>");
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

		ServletContext sc= this.getServletContext();
		res.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.addHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", -1);
		res.setContentType("text/html");
		PrintWriter out= res.getWriter();
		Map<String,String[]> formData= req.getParameterMap();
		// TODO This is causing http://i.imgur.com/0Qt0AVu.png why?

		// TODO Keeps giving stream errors why?
		// UserContainer userCont= null;
		// try
		// {
		// userCont= UserContainer.getContainer(sc.getResourceAsStream(_filename));
		// }
		// catch (ClassNotFoundException e)
		// {
		// // TODO Auto-generated catch block
		// out.println(_filename + (new File(_filename)).exists());
		// out.close();
		// // res.sendError(500);
		// }
		this.userCont.addUser(new User(formData));
		// this.userCont.writeToFile(sc.getResource(_filename).openConnection().getOutputStream());
		try
		{

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("POST~!<BR> ");
			out.println(this.userCont.getUsers().toString() + "<BR>");
			out.println(_filename + "<BR>" + (new File(_filename)).exists() + "<BR>");
			out.println("<a href=\"" + req.getHeader("referer") + "\"/>Back to Form!</a>");
			out.println("</body></html>");

		}
		finally
		{
			out.close();
		}
	}
}
class User
{

	private String			fName;

	private String			lName;

	private LinkedHashSet<String>	languages;

	private LinkedHashSet<String>	days;

	private String			color;

	/**
	 * @param fName
	 * @param lName
	 * @param languages
	 * @param days
	 * @param color
	 */
	public User(String fName, String lName, LinkedHashSet<String> languages, LinkedHashSet<String> days, String color)
	{

		super();
		this.fName= fName;
		this.lName= lName;
		this.languages= languages;
		this.days= days;
		this.color= color;
	}

	public User(Map<String,String[]> formMap)
	{

		this.fName= formMap.get("firstname")[0];
		this.lName= formMap.get("lastname")[0];
		this.languages= new LinkedHashSet<String>();
		if (formMap.get("langs") != null)
		{
			for (int i= 0; i < formMap.get("langs").length; i++)
			{
				this.languages.add(formMap.get("langs")[i]);
			}
		}
		this.days= new LinkedHashSet<String>();
		if (formMap.get("days") != null)
		{
			for (int i= 0; i < formMap.get("days").length; i++)
			{
				this.days.add(formMap.get("days")[i]);
			}
		}
		this.color= formMap.get("color")[0];
	}

	/**
	 * @return the fName
	 */
	public String getfName()
	{

		return this.fName;
	}

	/**
	 * @param fName
	 *            the fName to set
	 */
	public void setfName(String fName)
	{

		this.fName= fName;
	}

	/**
	 * @return the lName
	 */
	public String getlName()
	{

		return this.lName;
	}

	/**
	 * @param lName
	 *            the lName to set
	 */
	public void setlName(String lName)
	{

		this.lName= lName;
	}

	/**
	 * @return the languages
	 */
	public LinkedHashSet<String> getLanguages()
	{

		return this.languages;
	}

	/**
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(LinkedHashSet<String> languages)
	{

		this.languages= languages;
	}

	/**
	 * @return the days
	 */
	public LinkedHashSet<String> getDays()
	{

		return this.days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(LinkedHashSet<String> days)
	{

		this.days= days;
	}

	/**
	 * @return the colors
	 */
	public String getColor()
	{

		return this.color;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColor(String color)
	{

		this.color= color;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return "User [fName=" + this.fName + ", lName=" + this.lName + ", languages=" + this.languages + ", days=" + this.days + ", color="
				+ this.color + "]";
	}
}

class UserContainer
{

	/**
	 * @param users
	 */
	public UserContainer(LinkedHashSet<User> users)
	{

		super();
		this.users= users;
	}

	private final LinkedHashSet<User>	users;

	private String			fileLocation;


	public void writeToFile(OutputStream outputStream) throws IOException
	{
		ObjectOutputStream oos= new ObjectOutputStream(outputStream);
		oos.writeObject(this);
		oos.close();
	}

	public static UserContainer getContainer(InputStream inputStream) throws IOException, ClassNotFoundException
	{
		UserContainer userCont= null;
		InputStream fin= inputStream;
		ObjectInputStream ois= new ObjectInputStream(fin);
		userCont= (UserContainer) ois.readObject();
		ois.close();
		if (userCont == null)
		{
			userCont= new UserContainer(new LinkedHashSet<User>());
		}
		return userCont;
	}

	public LinkedHashSet<User> findFname(String fName)
	{

		LinkedHashSet<User> matchedUsers= new LinkedHashSet<User>();
		for (User u : this.users)
		{
			if (u.getfName().contains(fName) && !matchedUsers.contains(u))
			{
				matchedUsers.add(u);
			}
		}
		return matchedUsers;
	}

	public LinkedHashSet<User> findLname(String lName)
	{

		LinkedHashSet<User> validUsers= new LinkedHashSet<User>();
		for (User u : this.users)
		{
			if (u.getfName().contains(lName) && !validUsers.contains(u))
			{
				validUsers.add(u);
			}
		}
		return validUsers;
	}

	public LinkedHashSet<User> findLanguages(String langs)
	{

		LinkedHashSet<User> matchedUsers= new LinkedHashSet<User>();
		String[] langsArray= langs.split(" ");
		for (User u : this.users)
		{
			LinkedHashSet<String> userLangs= u.getLanguages();
			for (int i= 0; i < langsArray.length; i++)
			{
				if (userLangs.contains(langsArray[i]) && !matchedUsers.contains(u))
				{
					matchedUsers.add(u);
				}
			}
		}
		return matchedUsers;
	}

	public LinkedHashSet<User> findDays(String days)
	{

		LinkedHashSet<User> validUsers= new LinkedHashSet<User>();
		String[] daysArray= days.split(" ");
		for (User u : this.users)
		{
			for (int i= 0; i < daysArray.length; i++)
			{
				if (u.getDays().contains(daysArray[i]))
				{
					validUsers.add(u);
				}
			}
		}
		return validUsers;
	}

	public LinkedHashSet<User> findColor(String color)
	{

		LinkedHashSet<User> validUsers= new LinkedHashSet<User>();
		for (User u : this.users)
		{
			if (u.getColor().equalsIgnoreCase(color))
			{
				validUsers.add(u);
			}
		}
		return validUsers;
	}

	public LinkedHashSet<User> queryUsers(Map<String,String[]> query)
	{

		LinkedHashSet<User> desiredUsers= new LinkedHashSet<User>();
		if (query.containsKey("fName"))
		{
			desiredUsers.addAll(this.findFname(query.get("fName")[0]));
		}
		if (query.containsKey("lName"))
		{
			desiredUsers.addAll(this.findLname(query.get("lName")[0]));
		}
		if (query.containsKey("langs"))
		{
			desiredUsers.addAll(this.findLanguages(query.get("langs")[0]));
		}
		if (query.containsKey("days"))
		{
			desiredUsers.addAll(this.findLanguages(query.get("days")[0]));
		}
		if (query.containsKey("color"))
		{
			desiredUsers.addAll(this.findColor(query.get("color")[0]));
		}
		return desiredUsers;
	}

	/**
	 * @return the users
	 */
	public LinkedHashSet<User> getUsers()
	{

		return this.users;
	}

	/**
	 * @param user
	 *            add User to the list of users.
	 */
	public void addUser(User user)
	{
		if (!this.users.contains(user))
		{
			this.users.add(user);
		}
	}

	/**
	 * @return the fileLocation
	 */
	public String getFileLocation()
	{

		return this.fileLocation;
	}

	/**
	 * @param fileLocation
	 *            the fileLocation to set
	 */
	public void setFileLocation(String fileLocation)
	{

		this.fileLocation= fileLocation;
	}
}


