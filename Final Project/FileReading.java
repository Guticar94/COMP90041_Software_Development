import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class FileReading{
    private static Scanner inputStream = null;
    private Scenario scen = new Scenario("");
    private Location loc = new Location("", "");
    private int locCount = 0;
    private String[] charSettings;

    /**
     * class to open and read inputed files
     * @param inputStream        Scanner object instance for files reading
     * @param scen               Scenarios class instanciation 
     * @param loc                Locations class instanciation 
     * @param locCount           Locations counter
     * @param charSettings       Array of string containing the characters input features
     */

    /**
     * Default method to open and instanciate files
     * @return      inputStream  Scanner object with read file ready to parse
     * @throws      FileNotFoundException if the file to open is not found
     */
    public static Scanner readFile(String filename){
        commandInput inLine = new commandInput();
        try {
            inputStream = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.err.println("java.io.FileNotFoundException: could not find scenarios file.");
            inLine.printHelp();
            System.exit(1);
        }
        return inputStream;
    }

    /**
     * Method to print the message in the welcome.ascii file
     */
    public void printWelcome(){
        inputStream = readFile("welcome.ascii");
        while (inputStream.hasNextLine()) {
            System.out.println(inputStream.nextLine());
        } 
    }

    /**
     * Method to parse scenarios and return matrix in the provided scenarios.csv file
     *@return this function return the scenarios Matrix with all the scenarios readed from the scenarios file
     */
    public ArrayList<Scenario> readScenarios(String filename){
        ArrayList<Scenario> scenariosMatrix = new ArrayList<Scenario>();
        int countScenarios = 0;
        String input = "";
        String location = "";
        String tresp = "";
        int countline=0;

        /**
         * @param scenariosMatrix    Array List to store the inputed or simulated scenarios
         * @param countScenarios     Scenarios counter 
         * @param input              String to store file input line by line
         * @param location           String variable to store locations in location class
         * @param tresp              String variable to store trespassing status in location class
         * @param countline          Line counter over the inputed file
        */

        // Read scenarios file (if exists)
        inputStream = readFile(filename);

        // Iterate over the file
        while (inputStream.hasNextLine()) {
            // Get next file line
            input = inputStream.nextLine();
            // Counter of lines
            countline +=1;

            // Capture header and ignore
            if (input.matches("^.*gender,age.*$")){
                continue;
            }
            // Capture scenario and create object
            else if(input.matches("^scenario.*$")){
                // If the scenario is completely read, save scenario in scenarios matrix
                if(countScenarios > 0){
                    // Add the scenario to the scenarios matrix
                    scenariosMatrix.add(scen);
                }
                // Instanciate new scenario
                scen = new Scenario(input.split(",")[0].split(":")[1]);
                // Increas by one the scenarios counter
                countScenarios += 1;
                // Reset locations counter
                locCount = 0;
            }
            // Capture location and trespassing status (If lime chatch with location)
            else if(input.matches("^location.*$")){
                setLocation(location, tresp, input);
            }
            // Capture elements and fill matrix if we are in a character row
            else if(input.matches("^(human|animal).*$")){
                setCharacters(countline, input);
            }
        }
        // Add last scenario to the scenarios matrix
        scenariosMatrix.add(scen);
        return scenariosMatrix;
    }

    /**
     * Method to parse locations from the inputed file
     */
    private void setLocation(String location, String tresp, String input){
        // If lime chatch with location: Split line and get locations
        location = input.split(",")[0].split(":")[1].split(";")[0]+", "+input.split(",")[0].split(":")[1].split(";")[1];
        // If lime chatch with location: Split line and get trespassing status
        tresp = input.split(",")[0].split(":")[1].split(";")[2];
        // Increase location counter
        locCount += 1;
        // Instanciate location string to add to the matrix
        location = "[" + locCount + "] Location: " + location;

        // Instanciate trespassing string to add to the matrix
        if(tresp.equals("legal")){
            tresp = "Trespassing: no";
            }
        else{
            tresp = "Trespassing: yes";
            }

        // Add new location to the location classs
        loc = new Location(location, tresp);
        // Add location to the scenario class
        scen.add(loc);
    } 

    /**
     * Method to parse characters from the inputed file
     */
    private void setCharacters(int countline, String input){
        boolean error = false;

        // Get vector of features
        charSettings = input.split(",", -1);

        // 1. Invalid Data Format
        error = dataFormatError(error, countline);
        // 2. Invalid Number Format
        numFormatError(countline);
        // 3. Invalid Field Values
        invFieldVals(countline);

        // If there is not a error line 
        if(error == false){
            switch(charSettings[0]){
            // If we are on a human character
            case("human"):
                // Add new human [String bodyType, int age, String profession, String gender, String pregnant]
                loc.add(new Human(charSettings[3], Integer.parseInt(charSettings[2]), charSettings[4], charSettings[1], charSettings[5]));
                break;
            // If we are on an animal character
            case("animal"):
                // Add new animal [String bodyType, int age, String specie, String gender, String pet]
                loc.add(new Animal(charSettings[3], Integer.parseInt(charSettings[2]), charSettings[6], charSettings[1], charSettings[7]));
                break;
            }
        }
    }

    /**
     * Method to evaluate errors
     * 1. Invalid Data Format
     * @return      formError boolean to state if the error is triggered
     * @throws      WARNING message stating invalid data format
     */
    private boolean dataFormatError(boolean error, int countline){
        boolean formError = false;
        // 1. Invalid Data Format error
        if (charSettings.length != 8){
            System.out.printf("WARNING: invalid data format in scenarios file in line %d\n", countline);
            formError = true;
        }
        return formError;
    }

    /**
     * Method to evaluate errors
     * 2. Invalid Number Format
     * @throws      WARNING message stating invalid number format
     */
    private void numFormatError(int countline){
        // Cast age as integer
        try{
            Integer.valueOf(charSettings[2]); 
        }
        catch(Exception e){
            System.out.printf("WARNING: invalid number format in scenarios file in line %d\n", countline);
            charSettings[2] = "0";
        }
    }

    /**
     * Method to evaluate errors
     * 3. Invalid Field Values
     * @throws      WARNING message stating invalid characteristic
     */
    private void invFieldVals(int countline){
        // Invalid Field Values
        boolean accepted_1 = false;
        boolean accepted_2 = false;
        String[] bType = {"average", "athletic", "overweight", "unspecified"};
        String[] gender = {"male", "female", "unknown"};

        /**
         * @param accepted_1    States if the body type was accepted or not
         * @param accepted_2    States if the gender was accepted or not
         * @param bType         Body type list of possible values
         * @param gender        Gender list of possible values
        **/

        // For each body type in the list
        for(String btype : bType){
            // If the inputed value match accept the value
            if(charSettings[3].equals(btype)){
                accepted_1 = true;
            }
        }
        // If the inputed value does not match with any value in the list: assign a default value instead
        if(accepted_1 == false){
                charSettings[3] = "average";
        }

        // For each gender in the list
        for(String gen : gender){
            // If the inputed value match accept the value
            if(charSettings[1].equals(gen)){
                accepted_2 = true;
            }
        }
        // If the inputed value does not match with any value in the list: assign a default value instead
        if(accepted_2 == false){
            charSettings[1] = "unknown";
        }

        // BodyTypes, age and gender restrictions and compare
        if((accepted_1==false) | (accepted_2==false) | (Integer.parseInt(charSettings[2])<0)){
            if((Integer.parseInt(charSettings[2])<0)){
                charSettings[2] = "0";
            }
            System.out.printf("WARNING: invalid characteristic in scenarios file in line %d\n", countline);
        }
    }
}
