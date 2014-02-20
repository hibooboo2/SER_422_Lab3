
package ser422.lab3;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletsTask1
 */
@WebServlet("/ServletsTask2")
public class ServletsTask2 extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	private static String		_filename			= null;

	UserContainer2				userCont			= null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletsTask2()
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
		this.userCont= new UserContainer2(new LinkedHashSet<User2>());
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
		// UserContainer2 userCont= null;
		// try
		// {
		// this.userCont= UserContainer2.getContainer(sc.getResourceAsStream(_filename));
		// }
		// catch (ClassNotFoundException e)
		// {
		// response.sendError(500);
		// }
		LinkedHashSet<User2> validUsers= null;
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
			for (User2 u : validUsers)
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
			Cookie[] cookies= request.getCookies();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 2</title>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\">");
			out.print("<h2>");
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("fName") || cookies[i].getName().equals("lName")) 
				{
					out.print(cookies[i].getValue() + " ");
				}
			}
			out.println("- My Homepage</h2>");
			out.println("I know the following programming languages:");
			for (int i = 0; i < cookies.length; i++) 
			{
				if (cookies[i].getName().equals("langs")) 
				{
					out.println(cookies[i].getValue());
				}
				
			}
			out.println("I am available the following days:");
			for (int i = 0; i < cookies.length; i++) 
			{
				if (cookies[i].getName().equals("days")) 
				{
					out.println(cookies[i].getValue());
				}
			}
			
			out.print("My favorite color is ");
			for (int i = 0; i < cookies.length; i++) 
			{
				if (cookies[i].getName().equals("color")) 
				{
					out.println(cookies[i].getValue() + ".");
				}
				
			}
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
	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{

		ServletContext sc= this.getServletContext();
		res.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.addHeader("Pragma", "no-cache");
		res.setDateHeader("Expires", -1);
		res.setContentType("text/html");
		PrintWriter out= res.getWriter();
		Map<String,String[]> formData= req.getParameterMap();
		Cookie c= new Cookie("data", "" + (long) Math.random());
		c.setMaxAge(2592000); // Means cookie persists on disk
		res.addCookie(c);
		// TODO This is causing http://i.imgur.com/0Qt0AVu.png why?

		// TODO Keeps giving stream errors why?
		// UserContainer2 userCont= null;
		// try
		// {
		// userCont= UserContainer2.getContainer(sc.getResourceAsStream(_filename));
		// }
		// catch (ClassNotFoundException e)
		// {
		// // TODO Auto-generated catch block
		// out.println(_filename + (new File(_filename)).exists());
		// out.close();
		// // res.sendError(500);
		// }
		this.userCont.addUser(new User2(formData));
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
	}*/
}

class User2
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
	public User2(String fName, String lName, LinkedHashSet<String> languages, LinkedHashSet<String> days, String color)
	{

		super();
		this.fName= fName;
		this.lName= lName;
		this.languages= languages;
		this.days= days;
		this.color= color;
	}

	public User2(Map<String,String[]> formMap)
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

		return "User2 [fName=" + this.fName + ", lName=" + this.lName + ", languages=" + this.languages + ", days=" + this.days
				+ ", color="
				+ this.color + "]";
	}
}

class UserContainer2
{

	/**
	 * @param users
	 */
	public UserContainer2(LinkedHashSet<User2> users)
	{

		super();
		this.users= users;
	}

	private final LinkedHashSet<User2>	users;

	private String			fileLocation;


	public void writeToFile(OutputStream outputStream) throws IOException
	{
		ObjectOutputStream oos= new ObjectOutputStream(outputStream);
		oos.writeObject(this);
		oos.close();
	}

	public static UserContainer2 getContainer(InputStream inputStream) throws IOException, ClassNotFoundException
	{

		UserContainer2 userCont= null;
		InputStream fin= inputStream;
		ObjectInputStream ois= new ObjectInputStream(fin);
		userCont= (UserContainer2) ois.readObject();
		ois.close();
		if (userCont == null)
		{
			userCont= new UserContainer2(new LinkedHashSet<User2>());
		}
		return userCont;
	}

	public LinkedHashSet<User2> findFname(String fName)
	{

		LinkedHashSet<User2> matchedUsers= new LinkedHashSet<User2>();
		String[] fNameArray= fName.split(" ");
		for (User2 u : this.users)
		{
			for (int i= 0; i < fNameArray.length; i++)
			{
				if (u.getfName().contains(fNameArray[i]))
				{
					matchedUsers.add(u);
				}
			}
		}
		return matchedUsers;
	}

	public LinkedHashSet<User2> findLname(String lName)
	{

		LinkedHashSet<User2> validUsers= new LinkedHashSet<User2>();
		String[] lNameArray= lName.split(" ");
		for (User2 u : this.users)
		{
			for (int i= 0; i < lNameArray.length; i++)
			{
				if (u.getlName().contains(lNameArray[i]))
				{
					validUsers.add(u);
				}
			}
		}
		return validUsers;
	}

	public LinkedHashSet<User2> findLanguages(String langs)
	{

		LinkedHashSet<User2> matchedUsers= new LinkedHashSet<User2>();
		String[] langsArray= langs.split(" ");
		for (User2 u : this.users)
		{
			LinkedHashSet<String> userLangs= u.getLanguages();
			for (int i= 0; i < langsArray.length; i++)
			{
				if (userLangs.contains(langsArray[i]))
				{
					matchedUsers.add(u);
				}
			}
		}
		return matchedUsers;
	}

	public LinkedHashSet<User2> findDays(String days)
	{

		LinkedHashSet<User2> validUsers= new LinkedHashSet<User2>();
		String[] daysArray= days.split(" ");
		for (User2 u : this.users)
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

	public LinkedHashSet<User2> findColor(String color)
	{

		LinkedHashSet<User2> validUsers= new LinkedHashSet<User2>();
		String[] colorArray= color.split(" ");
		for (User2 u : this.users)
		{
			for (int i= 0; i < colorArray.length; i++)
			{
				if (u.getColor().equalsIgnoreCase(colorArray[i]))
				{
					validUsers.add(u);
				}
			}
		}
		return validUsers;
	}

	public LinkedHashSet<User2> queryUsers(Map<String,String[]> query)
	{

		LinkedHashSet<User2> allMatches= new LinkedHashSet<User2>();
		LinkedHashSet<User2> fNameUsers= null;
		LinkedHashSet<User2> lNameUsers= null;
		LinkedHashSet<User2> langsUsers= null;
		LinkedHashSet<User2> daysUsers= null;
		LinkedHashSet<User2> colorUsers= null;
		if (query.containsKey("fName"))
		{
			fNameUsers= this.findFname(query.get("fName")[0]);
			allMatches.addAll(fNameUsers);
		}
		if (query.containsKey("lName"))
		{
			lNameUsers= this.findLname(query.get("lName")[0]);
			allMatches.addAll(lNameUsers);
		}
		if (query.containsKey("langs"))
		{
			langsUsers= this.findLanguages(query.get("langs")[0]);
			allMatches.addAll(langsUsers);
		}
		if (query.containsKey("days"))
		{
			daysUsers= this.findLanguages(query.get("days")[0]);
			allMatches.addAll(daysUsers);
		}
		if (query.containsKey("color"))
		{
			colorUsers= this.findColor(query.get("color")[0]);
			allMatches.addAll(colorUsers);
		}

		LinkedHashSet<User2> toRemove= new LinkedHashSet<User2>();
		for (User2 u : allMatches)
		{
			if (!fNameUsers.contains(u) && query.containsKey("fName"))
			{
				toRemove.add(u);
			}
			if (!lNameUsers.contains(u) && query.containsKey("lName"))
			{
				toRemove.add(u);
			}
			if (!langsUsers.contains(u) && query.containsKey("langs"))
			{
				toRemove.add(u);
			}
			if (!daysUsers.contains(u) && query.containsKey("days"))
			{
				toRemove.add(u);
			}
			if (!colorUsers.contains(u) && query.containsKey("color"))
			{
				toRemove.add(u);
			}
		}
		allMatches.removeAll(toRemove);
		return allMatches;
	}

	/**
	 * @return the users
	 */
	public LinkedHashSet<User2> getUsers()
	{

		return this.users;
	}

	/**
	 * @param user
	 *            add User2 to the list of users.
	 */
	public void addUser(User2 user)
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


