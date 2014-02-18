
package ser422.lab3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Vector;

public class UserContainer
{

	/**
	 * @param users
	 * @param fileLocation
	 */
	public UserContainer(Vector<User> users, String fileLocation)
	{

		super();
		this.users= users;
		this.setFileLocation(fileLocation);
	}

	private final Vector<User>	users;

	private String			fileLocation;


	public void writeToFile() throws IOException
	{

		File userFile= new File(this.fileLocation);
		if (!userFile.exists())
		{
			userFile.createNewFile();
		}
		FileOutputStream fout= new FileOutputStream(userFile);
		ObjectOutputStream oos= new ObjectOutputStream(fout);
		oos.writeObject(this);
		oos.close();
	}

	public static UserContainer getContainer(String fileLocation) throws IOException, ClassNotFoundException
	{
		File userFile= new File(fileLocation);
		UserContainer userCont= null;
		if (userFile.exists())
		{
			FileInputStream fin= new FileInputStream(fileLocation);
			ObjectInputStream ois= new ObjectInputStream(fin);
			userCont= (UserContainer) ois.readObject();
			ois.close();
		}else {
			userCont= new UserContainer((new Vector<User>()), fileLocation);
		}
		return userCont;
	}

	public Vector<User> findFname(String fName)
	{

		Vector<User> matchedUsers= new Vector<User>();
		for (User u : this.users)
		{
			if (u.getfName().contains(fName))
			{
				matchedUsers.add(u);
			}
		}
		return matchedUsers;
	}

	public Vector<User> findLname(String lName)
	{

		Vector<User> validUsers= new Vector<User>();
		for (User u : this.users)
		{
			if (u.getfName().contains(lName))
			{
				validUsers.add(u);
			}
		}
		return validUsers;
	}

	public Vector<User> findLanguages(String[] langs)
	{

		Vector<User> matchedUsers= new Vector<User>();
		for (User u : this.users)
		{
			Vector<String> userLangs= u.getLanguages();
			for (int i= 0; i < langs.length; i++)
			{
				if (userLangs.contains(langs[i]))
				{
					matchedUsers.add(u);
				}
			}
		}
		return matchedUsers;
	}

	public Vector<User> findDays(String[] days)
	{

		Vector<User> validUsers= new Vector<User>();
		for (User u : this.users)
		{
			for (int i= 0; i < days.length; i++)
			{
				if (u.getDays().contains(days[i]))
				{
					validUsers.add(u);
				}
			}
		}
		return validUsers;
	}

	public Vector<User> findColor(String color)
	{

		Vector<User> validUsers= new Vector<User>();
		for (User u : this.users)
		{
			if (u.getColor().equalsIgnoreCase(color))
			{
				validUsers.add(u);
			}
		}
		return validUsers;
	}

	public Vector<User> queryUsers(Map<String,String[]> query)
	{

		Vector<User> desiredUsers= new Vector<User>();
		for (String fName : query.get("fName"))
		{
			desiredUsers.addAll(this.findFname(fName));
		}
		for (String lName : query.get("lName"))
		{
			desiredUsers.addAll(this.findFname(lName));
		}
		desiredUsers.addAll(this.findLanguages(query.get("langs")));
		desiredUsers.addAll(this.findLanguages(query.get("days")));
		for (String color : query.get("color"))
		{
			desiredUsers.addAll(this.findColor(color));
		}
		return this.users;
	}

	/**
	 * @return the users
	 */
	public Vector<User> getUsers()
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
