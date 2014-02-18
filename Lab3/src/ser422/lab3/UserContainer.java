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

	Vector<User>	users;

	public void writeToFile(String fileLocation) throws IOException
	{

		File userFile= new File(fileLocation);
		if (!userFile.exists())
		{
			userFile.createNewFile();
		}
		FileOutputStream fout= new FileOutputStream(userFile);
		ObjectOutputStream oos= new ObjectOutputStream(fout);
		oos.writeObject(this);
		oos.close();
	}

	public static Vector<User> getAllusers(String fileLocation) throws IOException, ClassNotFoundException
	{

		Vector<User> users= new Vector<User>();
		FileInputStream fin= new FileInputStream(fileLocation);
		ObjectInputStream ois= new ObjectInputStream(fin);
		User user= null;
		while ((user= (User) ois.readObject()) != null)
		{
			users.add(user);
		}
		ois.close();
		return users;
	}

	public Vector<User> findFname(String fName)
	{

		return this.users;

	}

	public Vector<User> findLname(String lName)
	{
		return this.users;

	}

	public Vector<User> findLanguages(String[] langs)
	{

		return this.users;

	}

	public Vector<User> findDays(String[] days)
	{

		return this.users;

	}

	public Vector<User> findColor(String color)
	{

		return this.users;

	}

	public Vector<User> queryUsers(Map<String,String[]> query)
	{

		return this.users;
	}
}
