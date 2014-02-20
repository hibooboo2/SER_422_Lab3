
package ser422.lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
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
@WebServlet("/")
public class LandingPage extends HttpServlet
{

	private static final long	serialVersionUID	= 1L;

	private static String		_filename			= null;

	private UserContainer		userCont;

	// UserContainer userCont = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LandingPage()
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
		;
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
		Map<String,String[]> cookiesMap= new HashMap<String,String[]>();
		if (request.getCookies() != null)
		{
			for (Cookie coo : request.getCookies())
			{
				cookiesMap.put(coo.getName(), coo.getValue().split(":"));
				coo.setMaxAge(0);
				response.addCookie(coo);
			}
			if (cookiesMap.containsKey("action") && cookiesMap.get("action")[0].equalsIgnoreCase("adduser"))
			{
				this.userCont.addUser(new User(cookiesMap));
			}
		}
		Map<String,String[]> query= request.getParameterMap();
		// UserContainer userCont= null;
		// try
		// {
		// userCont= UserContainer.getContainer(sc.getResourceAsStream(_filename));
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
		if (validUsers != null && !validUsers.isEmpty())
		{
			for (User u : validUsers)
			{
				out.println(u.toString() + "<BR>");
			}
		}
		else
		{
			out.println("No valid Users!" + "<BR>");
		}
		try
		{
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Lab 3 Part 2</title>");
			out.println("<style>{font-family:\"Trebuchet MS\", Calibri, Verdana, sans-serif;}</style>");
			out.println("</head>");
			out.println("<body bgcolor=\"pink\"><form method=\"post\">");
			out.println("<h2>Landing Page</h2>");
			out.println("</body>");
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
		// UserContainer userCont= null;
		// try
		// {
		// userCont= UserContainer.getContainer(sc.getResourceAsStream(_filename));
		// }
		// catch (ClassNotFoundException e)
		// {
		// out.println(_filename + (new File(_filename)).exists());
		// out.close();
		// // res.sendError(500);
		// }
		this.userCont.addUser(new User(formData));
		// userCont.writeToFile(_filename);
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

	private String					fName;

	private String					lName;

	private LinkedHashSet<String>	langs;

	private LinkedHashSet<String>	days;

	private String					color;

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
		this.langs= languages;
		this.days= days;
		this.color= color;
	}

	public User(Map<String,String[]> formMap)
	{

		this.fName= formMap.get("firstname")[0];
		this.lName= formMap.get("lastname")[0];
		this.langs= new LinkedHashSet<String>();
		if (formMap.get("langs") != null)
		{
			for (int i= 0; i < formMap.get("langs").length; i++)
			{
				this.langs.add(formMap.get("langs")[i]);
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

	public User(Cookie[] userCookies)
	{
		this(User.parseCookiesToMap(userCookies));
	}

	/**
	 * @param userCookies
	 * @return
	 */
	private static Map<String,String[]> parseCookiesToMap(Cookie[] userCookies)
	{
		Map<String,String[]> formMap= new HashMap<String,String[]>();
		for (Cookie coo : userCookies)
		{
			formMap.put(coo.getName(), coo.getValue().split(":"));
		}
		return formMap;
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
	public LinkedHashSet<String> getLangs()
	{

		return this.langs;
	}

	/**
	 * @param langs
	 *            the languages to set
	 */
	public void setLangs(LinkedHashSet<String> langs)
	{

		this.langs= langs;
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

		return "firstname=" + this.fName + ",lastname=" + this.lName + ",langs=" + this.langs + ",days=" + this.days + ",color="
				+ this.color;
	}
}

class UserContainer
{

	/**
	 * @param uslers
	 */
	public UserContainer(LinkedHashSet<User> users)
	{

		super();
		this.users= users;
	}

	private final LinkedHashSet<User>	users;

	public void writeToFile(String fileName) throws IOException
	{

		try
		{
			PrintWriter pw= new PrintWriter(new FileOutputStream(fileName));
			for (User u : this.users)
			{
				pw.println(u.toString());
			}
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error saving users.");
		}
	}

	public static UserContainer getContainer(String fileName) throws IOException, ClassNotFoundException
	{

		UserContainer userCont= new UserContainer(new LinkedHashSet<User>());
		String user= null;
		String nextLine= null;
		BufferedReader br= new BufferedReader(new FileReader(fileName));
		while ((nextLine= br.readLine()) != null)
		{
			user= nextLine;
			HashMap<String,String[]> userParsed= new HashMap<String,String[]>();
			String[] userSplit= user.split(",");
			String[] value= new String[1];
			for (int i= 0; i < userSplit.length; i+= 2)
			{
				value[0]= userSplit[i + 1];
				userParsed.put(userSplit[i], value);
			}
			userCont.addUser(new User(userParsed));
		}
		br.close();
		return userCont;
	}

	public static UserContainer getContainer(InputStream input) throws IOException, ClassNotFoundException
	{

		UserContainer userCont= new UserContainer(new LinkedHashSet<User>());
		String user= null;
		String nextLine= null;
		BufferedReader br= new BufferedReader(new InputStreamReader(input));
		// TODO: Test this now!
		while ((nextLine= br.readLine()) != null)
		{
			user= nextLine;
			HashMap<String,String[]> userParsed= new HashMap<String,String[]>();
			String[] userSplit= user.split(",");
			for (String s : userSplit)
			{
				String[] nameValuesplit= s.split("=");
				userParsed.put(nameValuesplit[0], new String[]{nameValuesplit[1]});
			}
			userCont.addUser(new User(userParsed));
		}
		br.close();
		return userCont;
	}

	public LinkedHashSet<User> findFname(String fName)
	{

		LinkedHashSet<User> matchedUsers= new LinkedHashSet<User>();
		String[] fNameArray= fName.split(" ");
		for (User u : this.users)
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

	public LinkedHashSet<User> findLname(String lName)
	{

		LinkedHashSet<User> validUsers= new LinkedHashSet<User>();
		String[] lNameArray= lName.split(" ");
		for (User u : this.users)
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

	public LinkedHashSet<User> findLanguages(String langs)
	{

		LinkedHashSet<User> matchedUsers= new LinkedHashSet<User>();
		String[] langsArray= langs.split(" ");
		for (User u : this.users)
		{
			LinkedHashSet<String> userLangs= u.getLangs();
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

	public LinkedHashSet<User> findDays(String days)
	{

		LinkedHashSet<User> validUsers= new LinkedHashSet<User>();
		String[] daysArray= days.split(" ");
		for (User u : this.users)
		{
			LinkedHashSet<String> userDays= u.getDays();
			for (int i= 0; i < daysArray.length; i++)
			{
				if (userDays.contains(daysArray[i]))
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
		String[] colorArray= color.split(" ");
		for (User u : this.users)
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

	public LinkedHashSet<User> queryUsers(Map<String,String[]> query)
	{

		LinkedHashSet<User> allMatches= new LinkedHashSet<User>();
		LinkedHashSet<User> fNameUsers= null;
		LinkedHashSet<User> lNameUsers= null;
		LinkedHashSet<User> langsUsers= null;
		LinkedHashSet<User> daysUsers= null;
		LinkedHashSet<User> colorUsers= null;
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
		LinkedHashSet<User> toRemove= new LinkedHashSet<User>();
		for (User u : allMatches)
		{
			if ((query.containsKey("fName") && !fNameUsers.contains(u)) | (query.containsKey("lName") && !lNameUsers.contains(u))
					| (query.containsKey("langs") && !langsUsers.contains(u)) | (query.containsKey("days") && !daysUsers.contains(u))
					| (query.containsKey("color") && !colorUsers.contains(u)))
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

		this.users.add(user);
	}
}
