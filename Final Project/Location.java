import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Final Project 
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class Location{
	private String loc;
    private String trespassing;
	private ArrayList<Character> characters = new ArrayList<Character>();

	/**
     * Class representing a location within each Scenario. Here we add the scenario locations to manage.
	 * @param loc 				Specifies scenario location
	 * @param trespassing		Specifies if there is a trespassing status in the location 
	 * @param characters		Characters inside this location, that this location manages
     */
	

	/**
     * Constructor to create a new location with a given name
     */
	public Location(String loc, String trespassing) {
		super();
		this.loc = loc;
        this.trespassing = trespassing;
	}

	/**
     * Method to add a new character in this location
     */
	public void add(Character character) {
		characters.add(character);
	}

	/**
     * Method to get the current location
	 * @return loc 		The scenario location
     */
    public String getLocation() {
		return loc;
	}

	/**
     * Method to get the current trespassing status
	 * @return trespassing 		The location trespassing status
     */
	public String getTrespassing() {
		return trespassing;
	}

	/**
     * Method to to get the current number of characters
	 * @return characters.size() 		The number of characters in the location
     */
    public int getCharactersNumber() {
		return characters.size();
	}

	/**
     * Method to to get the current characters list in the location
	 * @return characters 		The list of characters in the location
     */
    public ArrayList<Character> getCharacters(){
        return characters;
    }

}
