import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class Scenario{
	private String name;
	private ArrayList<Location> locationArray = new ArrayList<Location>();

	/**
     * Class representing a Scenario. Entities can be added to the scenario for this class to manage.
	 * @param name 				Name of the scenario
	 * @param locationArray		Locations inside this scenario, that this scenario manages
     */

	/**
     * Constructor to create a new scenario with a given name
     */
	public Scenario(String name) {
		super();
		this.name = name;
	}
	
	/**
     * Method to add a new location in this scenario
     */
	public void add(Location location) {
		locationArray.add(location);
	}

	/**
     * Method to print out a description of the scenario and characters
	 * @return scenDesc 	String with the scenario description
     */
	public String displayDescription() {
		String scenDesc = "";
		// Print scenarios
		scenDesc = " ======================================";
		scenDesc += "\n# Scenario: " + name;
		scenDesc += "\n======================================";

		// Print location
		for(Location location : locationArray) {
			scenDesc += "\n" + location.getLocation();
			scenDesc += "\n" + location.getTrespassing();

			// Print characters
			scenDesc += "\n" + location.getCharactersNumber() + " Characters: ";
			for(int i=0; i<location.getCharacters().size(); i++){
				scenDesc += "\n- " + location.getCharacters().get(i).describe();
			}
		}
		
		return scenDesc;
	}

	/**
     * Method to count scenario Locations
	 * @return locationArray.size() 	Number of locations within the scenario
     */
	public int countLocations(){
		return locationArray.size();
	}

	/**
     * Method to get scenario Locations
	 * @return locationArray 	List of locations within the scenario
     */
	public ArrayList<Location> getLocations(){
		return locationArray;
	}
	
	
}
