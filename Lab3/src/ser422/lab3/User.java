
package ser422.lab3;

import java.util.Vector;

public class User
{
	String					fName;
	String					lName;
	Vector<String>	languages;
	Vector<String>	days;
	Vector<String>	colors;

	/**
	 * @param fName
	 * @param lName
	 * @param languages
	 * @param days
	 * @param colors
	 */
	public User(String fName, String lName, Vector<String> languages, Vector<String> days, Vector<String> colors)
	{

		super();
		this.fName= fName;
		this.lName= lName;
		this.languages= languages;
		this.days= days;
		this.colors= colors;
	}
}
