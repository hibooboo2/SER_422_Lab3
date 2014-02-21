
package ser422.lab3;

import java.io.BufferedReader;
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
		boolean userAdded= false;
		if (cookiesMap.containsKey("action") && cookiesMap.get("action")[0].equalsIgnoreCase("adduser"))
		{
			for (Cookie coo : request.getCookies())
			{
				if (!coo.getName().equalsIgnoreCase("alreadyRegisetered"))
				{
					coo.setMaxAge(0);
				}
				response.addCookie(coo);
			}
			if (this.userCont.addUser(new User(cookiesMap)))
			{
				userAdded= true;
			}
		}

		if (cookiesMap.containsKey("alreadyRegisetered")
				&& (cookiesMap.get("alreadyRegisetered")[0].equalsIgnoreCase("true") || cookiesMap.get("alreadyRegisetered")[0]
						.equalsIgnoreCase("dontRegister")))
		{
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
				if (userAdded)
				{
					out.println(" <script>confirm(\"User Successfully Added!\");</script>");
				}
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
				out.println("<h2>Landing Page</h2>");
				if (cookiesMap.containsKey("username") && cookiesMap.get("username").length > 0)
				{
					out.println("Welcome back " + cookiesMap.get("username")[0]);
				}
				else
				{
					out.println("Welcome Anonymous");
				}
				out.println(" <script>function myFunction(){confirm(\"Press a button!\");}</script>");
				out.println("<input type=\"submit\" name=\"nav\" value=\"Not you?\">");
				out.println("<input type=\"submit\" name=\"nav\" value=\"Clear Cookies!\">");
				if (cookiesMap.get("userCreationCookiesCleared") != null)
				{
					out.println("Cookies for making new user Cleared! Creating new User has been canceled.");
				}
				out.println("</body>");
				out.println("</html>");
			}
			finally
			{
				out.close();
			}
		}
		else
		{
			response.addCookie(new Cookie("alreadyRegisetered", "starting"));
			response.sendRedirect("/Lab3/firstName");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		if (req.getParameter("nav") != null)
		{
			res.addCookie(new Cookie("alreadyRegisetered", "false"));
			if (req.getParameter("nav").equalsIgnoreCase("Not you?"))
			{
				res.sendRedirect("/Lab3/firstName");
			}
			else if (req.getParameter("nav").equalsIgnoreCase("Clear Cookies!"))
			{
				for (Cookie coo : req.getCookies())
				{
					coo.setMaxAge(0);
					res.addCookie(coo);
				}
				res.sendRedirect("/Lab3/");
			}
		}
	}
}

class User
{

	private String					userName;
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
	public User(String userName, String fName, String lName, LinkedHashSet<String> languages, LinkedHashSet<String> days, String color)
	{

		super();
		this.userName= userName;
		this.fName= fName;
		this.lName= lName;
		this.langs= languages;
		this.days= days;
		this.color= color;
	}

	public User(Map<String,String[]> formMap)
	{

		if (formMap.containsKey("username") && (formMap.get("username") != null) && formMap.get("username").length > 0)
		{
			if (formMap.get("username").length > 0)
			{
				this.userName= formMap.get("username")[0];
			}
		}
		else
		{
			this.userName= "";
		}
		if (formMap.containsKey("firstname") && formMap.get("firstname") != null && formMap.get("firstname").length > 0)
		{
			this.fName= formMap.get("firstname")[0];
		}
		else
		{
			this.fName= "";
		}
		if (formMap.containsKey("lastname") && formMap.get("lastname") != null && formMap.get("lastname").length > 0)
		{
			this.lName= formMap.get("lastname")[0];
		}
		else
		{
			this.lName= "";
		}
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
		if (formMap.containsKey("color") && formMap.get("color") != null && formMap.get("color").length > 0)
		{
			this.color= formMap.get("color")[0];
		}
		else
		{
			this.color= "";
		}
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

	/**
	 * @return the userName
	 */
	public String getUserName()
	{

		return this.userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName)
	{

		this.userName= userName;
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

	public LinkedHashSet<User> findUserName(String userName)
	{

		LinkedHashSet<User> matchedUsers= new LinkedHashSet<User>();
		String[] userNameArray= userName.split(" ");
		for (User u : this.users)
		{
			for (int i= 0; i < userNameArray.length; i++)
			{
				if (u.getUserName().equalsIgnoreCase(userNameArray[i]))
				{
					matchedUsers.add(u);
				}
			}
		}
		return matchedUsers;
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
		LinkedHashSet<User> userNameUsers= null;
		LinkedHashSet<User> fNameUsers= null;
		LinkedHashSet<User> lNameUsers= null;
		LinkedHashSet<User> langsUsers= null;
		LinkedHashSet<User> daysUsers= null;
		LinkedHashSet<User> colorUsers= null;
		if (query.containsKey("username"))
		{
			userNameUsers= this.findUserName(query.get("username")[0]);
			allMatches.addAll(userNameUsers);
		}
		if (query.containsKey("fname"))
		{
			fNameUsers= this.findFname(query.get("fname")[0]);
			allMatches.addAll(fNameUsers);
		}
		if (query.containsKey("lname"))
		{
			lNameUsers= this.findLname(query.get("lname")[0]);
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
			if ((query.containsKey("userName") && !userNameUsers.contains(u)) | (query.containsKey("fName") && !fNameUsers.contains(u))
					| (query.containsKey("lName") && !lNameUsers.contains(u))
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
	 * @return
	 */
	public boolean addUser(User user)
	{

		if (this.findUserName(user.getUserName()).isEmpty())
		{
			this.users.add(user);
			return true;
		}
		else
		{
			return false;
		}
	}
}
