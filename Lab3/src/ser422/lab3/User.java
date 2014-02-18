
package ser422.lab3;

import java.util.Vector;

public class User
{

	private String			fName;

	private String			lName;

	private Vector<String>	languages;

	private Vector<String>	days;

	private String			color;

	/**
	 * @param fName
	 * @param lName
	 * @param languages
	 * @param days
	 * @param color
	 */
	public User(String fName, String lName, Vector<String> languages, Vector<String> days, String color)
	{

		super();
		this.fName= fName;
		this.lName= lName;
		this.languages= languages;
		this.days= days;
		this.color= color;
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
	public Vector<String> getLanguages()
	{

		return this.languages;
	}

	/**
	 * @param languages
	 *            the languages to set
	 */
	public void setLanguages(Vector<String> languages)
	{

		this.languages= languages;
	}

	/**
	 * @return the days
	 */
	public Vector<String> getDays()
	{

		return this.days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(Vector<String> days)
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
