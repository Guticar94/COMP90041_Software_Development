import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public abstract class Character{
	private String bodyType;
	private int age;
	private String gender;

	/**
     * Abstract class for representing a character in a scenario
	 * @param bodyType 	Character body type
	 * @param age		Character age
	 * @param gender	Character gender
     */

	/**
     * Constructor to create character
     */
	public Character(String bodyType, int age, String gender) {
		super();
		this.bodyType = bodyType;
		this.age = age;
		this.gender = gender;
	}

	/**
     * Getter function to obtain character body type
	 * @return string specifying body type inputed
     */
	public String getBodyType() {
		return bodyType;
	}

	/**
     * Getter function to obtain character age
	 * @return string specifying age inputed
     */
	public int getAge() {
		return age;
	}

	/**
     * Getter function to obtain character Gender
	 * @return string specifying Gender inputed
     */
	public String getGender() {
		return gender;
	}

	/**
     * Abstract method to describe the character - Further extention on children classes
     */
	public abstract String describe();

	/**
     * Abstract method to get the character type - Further extention on children classes
     */
	public abstract String tipeChar();

	/**
     * Abstract method to get the character features - Further extention on children classes
     */
	public abstract ArrayList<String> getFeatures();

}
