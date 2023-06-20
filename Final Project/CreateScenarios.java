import java.lang.Math;
import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

// Create scenarios if not input file was provided
public class CreateScenarios{

	/**
	 * Class to simulate the RescueBot scenarios
	*/

	/**
	 * Main method to create the scenarios
	*/
    public void scenarios(ArrayList<Scenario> scenariosMatrix, int totScenarios){
        Scenario scen = new Scenario("");
        Location loc = new Location("", "");
        String[] charSettings;
        String charact;
        int totLoc = 0;
        int totChar = 0;

		/**
		 * @param scen               Scenarios class instanciation 
         * @param loc                Locations class instanciation 
		 * @param charSettings  		Array of string containing the characters input features
		 * @param charact        	String to define character type to create (Human or Animal)
		 * @param totLoc         	Integer to specify number of locations to create
		 * @param totChar            Integer to specify number of characters to create
		*/

        // Fill scenarios randomly
        for(int i=0; i<totScenarios; i++){                            // For each scenario
            scen = new Scenario(createScenario());                    // create Scenario (Scenario name)

            totLoc = (int)(Math.random()*3)+2;                       // Define number of locations
            for(int l=0; l<totLoc; l++){                             // For each location
                loc = new Location(createLocation(l+1), createtresp());     // create Location (Location and trespassing)
                scen.add(loc);                                           // Add location to scenario 

                totChar = (int)(Math.random()*5)+1;                      // Define number of characters
                String[] chars = {"human", "animal"};                    // Specify possible Type of characters

                for(int j=0; j<totChar; j++){                            // For each character within the scenario
                    charact = chars[(int)(Math.random()*chars.length)];
                    switch(charact){  // Choose character 
                        case("human"):                                   // Create Human
                            charSettings = createHuman();
                            // Add new human [String bodyType, int age, String profession, String gender, String pregnant]
                            loc.add(new Human(
                                charSettings[0], 
                                Integer.parseInt(charSettings[1]), 
                                charSettings[2], 
                                charSettings[3], 
                                charSettings[4]));
                        break;
                        case("animal"):                                  // Create Animal
                            charSettings = createAnimal();
                            // Add new Animal [String bodyType, int age, String specie, String gender, String pet]
                            loc.add(new Animal(
                                charSettings[0], 
                                Integer.parseInt(charSettings[1]), 
                                charSettings[2], 
                                charSettings[3], 
                                charSettings[4]));
                        break;
                    }
                }
            }
        scenariosMatrix.add(scen);  // Save scenario info
        }
    }

	/**
	 * Method to create scenario randomly from a list of options
	 * @return 	String with scenario chosen randomly
	*/
    private String createScenario(){
        // Set of possible Scenarios
        String[] list = {"flood", "bushfire", "cyclone"};

        return list[(int)(Math.random()*list.length)];
    }

    /**
	 * Method to create location randomly from: options list for directions, and numeric interval for degrees
	 * @return 	String with location chosen randomly
	*/
    private String createLocation(int countLoc){
        // Latitude between 0 and 90 degrees and N or S directions
        String[] list = {"N", "S"};
        String lat = "[" + countLoc + "] Location:" + String.format("%.4f", Math.random()*90) + " " + list[(int)(Math.random()*list.length)];

        // Longitude between 0 and 180 degrees and E or W directions
        String[] list_2 = {"W", "E"};
        String longi = String.format("%.4f", Math.random()*180) + " " + list_2[(int)(Math.random()*list_2.length)];
        return lat+", "+longi;
    }

	/**
	 * Method to create trespassing status randomly
	 * @return 	String with trespassing status chosen randomly
	*/
    private String createtresp(){
        // Set of possible status
        String[] list = {"Trespassing: no", "Trespassing: yes"};
        return list[(int)(Math.random()*list.length)];
    }

    /**
	 * Method to create human character randomly from a list of options for each feature
	 * @return 	List with human features chosen randomly
	*/
    private String[] createHuman(){
        String[] human = new String[5];

		// @param human 	Array to store human features

        // Possibles body types list
        String[] bType = {"average", "athletic", "overweight", "unspecified"};
		// Randomly choose one body type
        human[0] = bType[(int)(Math.random()*bType.length)];
        // Randomly choose one age (from 0 to 90)
        human[1] = Integer.toString((int)(Math.random()*90));
        // Possible profession list
        String[] prof = {"doctor", "ceo", "criminal", "homeless", "unemployed", "none", "engineer", "singer"};

		// If humans are in age to have a profession
        if((Integer.valueOf(human[1])>16)&&(Integer.valueOf(human[1])<69)){
			// Randomly choose one profession
            human[2] = prof[(int)(Math.random()*prof.length)];
        }
        else{
			// Otherwise assign none
            human[2] = "none";
        }
        // Possible genders list
        String[] gender = {"male", "female", "unknown"};
		// Randomly choose one gender
        human[3] = gender[(int)(Math.random()*gender.length)];
        // Possible pregnant list
        String[] preg = {"false", "true"};

		// If human is female
        if(human[3]=="female"){
			// Randomly choose one pregnant status
            human[4] = preg[(int)(Math.random()*preg.length)];
        }
        else{
			// Otherwise assign none
            human[4] = "none";
        }

        // [String bodyType, int age, String profession, String gender, String pregnant]
        return human;
    }

    /**
	 * Method to create animal character randomly from a list of options for each feature
	 * @return 	List with animal features chosen randomly
	*/
    private String[] createAnimal(){
        String[] animal = new String[5];
        /// Possibles body types list
        String[] bType = {"average", "athletic", "overweight", "unspecified"};
		// Randomly choose one body type
        animal[0] = bType[(int)(Math.random()*bType.length)];
        // Randomly choose one age (from 0 to 20)
        animal[1] = Integer.toString((int)(Math.random()*20));
        // Possible Species list
        String[] animalType = {"dog", "cat", "ferret", "platypus", "koala", "snake", "shark", "lion"};
		// Randomly choose one specie
        animal[2] = animalType[(int)(Math.random()*animalType.length)];
        // Possible gender list
        String[] gender = {"male", "female", "unknown"};
		// Randomly choose one gender
        animal[3] = gender[(int)(Math.random()*gender.length)];
		// Possible pet list
		String[] pet = {"is pet", ""};
        
		// If the animal can be pet
        if(animal[0]=="dog" || animal[0]=="cat" || animal[0]=="ferret"){
			// Randomly assing pet status
            animal[4] = pet[(int)(Math.random()*pet.length)];
        }
        else{
			// Otherwise assign none
            animal[4] = "none";
        }

        // [String bodyType, int age, String specie, String gender, String pet]
        return animal;
    }
}
