import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class Audit{
    private static Scanner inputStream = null;
    String logPath = "";
    private boolean fileGood = false;


    /**
     * class to audit the rescue statistics, corresponds to the a or audit option in the main menu.
     *@param inputStream    Scanner object instance for files reading
     *@param logPath        String value to store the logfile path
     *@param fileGood       States if the inputed file is in good state for parsing    
     */

    /**
     * Method to check the logs file existance and evaluate if it is in a good state to use
     * @throws FileNotFoundException if file is not found
     */
    public void getFile(RescueBot rescBot, commandInput inLine, String[] args, Scanner input){
        boolean check = true;

        // *@param check    parameter that states the existance of the file

        // Check if specified
        logPath = inLine.argsGet(args, "logs");

        // If no logfile was specified at the start default rescuebot.log
        if(logPath==""){
            logPath = "rescuebot.log";
        }

        // Try to open it: Check whether the file exists
        try {
            inputStream = new Scanner(new FileInputStream(logPath));
        } catch (FileNotFoundException e) {
            check = false;
            System.err.println("java.io.FileNotFoundException: could not find scenarios file.");
            System.err.println("No history found. Press enter to return to main menu.");
            System.out.print(">\s");
            input.nextLine();
        }
        
        // Evaluate if the file has lines
        if(check){
            if(inputStream.hasNextLine()==false){
                System.err.println("fileException: the file is empty.");
                System.err.println("No history found. Press enter to return to main menu.");
                System.out.print(">\s");
                input.nextLine();
            }
            else{
                fileGood = true;
            }
        }
       
    }

    /**
     * Method to print the audit results
     */
    public void auditFile(RescueBot rescBot){
        // If the file is in good state 
        if(fileGood){
            // print simulation audit
            algorithmAudit(rescBot);
            System.out.println("");
            // print judging audit
            userAudit(rescBot);
        }
    }
    
    /**
     * Algorithm audit over simulation
     */
    private void algorithmAudit(RescueBot rescBot){
        boolean algor = false;
        boolean stats = false;
        String input = "";

        /**
        *@param algor   States if we are in the algorithm statistics section
        *@param stats   States if we are in the lines corresponding to the statistics to print
        *@param input   String to store the current line in the file parsing 
        */

        // Open the file to read
        inputStream = readFile(logPath);

        // Print algorithm audit
        System.out.println("======================================");
        System.out.println("# Algorithm Audit");
        System.out.println("======================================");

        //  Get the printed statistics
        while (inputStream.hasNextLine()) {
            // Get next line
            input = inputStream.nextLine();
            // If we are in the user statistics
            if (input.matches("^.*User.*$")){
                // Set parameter to false
                algor = false;
            }
            // If we are in the algorithm statistics
            else if(input.matches("^.*Algorithm.*$")){
                // Set parameter to true
                algor = true;
            }

            // If we are in the lines to print
            if(input.matches("^.*SAVED.*$")){
                // Set parameter to true
                stats = true;
            }
            // If we exit the lines to print
            else if(input.matches("^.*=.*$")){
                // Set parameter to false
                stats = false;
            }

            // If both parameters are true
            if(algor && stats){
                // Print audit lines for Algorithm audit
                System.out.println(input);
            }
        }
    }
    
    /**
     * User audit over the logs file
     */
    private void userAudit(RescueBot rescBot){
        boolean user = false;
        String input = "";
        int position = 0;
        int counter = 0;

        /**
        *@param user        States if we are in the user statistics section
        *@param input       String to store the current line in the file parsing 
        *@param position    Integer to save the position of the last statistics summary
        *@param counter     Counter to know in which line we are
        */

        // Open the file to read
        inputStream = readFile(logPath);

        //  Get the printed statistics 
        while (inputStream.hasNextLine()) {
            // Get next line
            input = inputStream.nextLine();
            //  Count line
            counter += 1;

            // If we are in the user statistics
            if (input.matches("^.*User.*$")){
                // Set parameter to true
                user = true;
            }
            // If we are in the algorithm statistics
            else if(input.matches("^.*Algorithm.*$")){
                // Set parameter to false
                user = false;
            }

            // If the parameter is set to true
            if(user){
                // If the input matches with saved
                if (input.matches("^.*SAVED.*$")){
                    // Store the latest saved position
                    position = counter;
                }
            }   
        }

        // Open the file to read again
        inputStream = readFile(logPath);
        // Restart the counter
        counter = 0;

        // Print user audit
        System.out.println("======================================");
        System.out.println("# User Audit");
        System.out.println("======================================");

        //  Print last statistics 
        while (inputStream.hasNextLine()) {
            // Get next line
            input = inputStream.nextLine();
            //  Count line
            counter += 1;

            // If we reach the last position saved
            if (counter>=position){
                // Print user statistics
                System.out.println(input);
                // Once reached the age of the statistics
                if(input.matches("^.*\sage.*$")){
                    // break the loop
                    break;
                }
            }
        }  
    }

    /**
     * Default method to open and read files
     * Copied with minor changes from FileReading.java
     */
    private static Scanner readFile(String filename){
        try {
            inputStream = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.err.println("java.io.FileNotFoundException: could not find scenarios file.");
            System.exit(1);
        }
        return inputStream;
    }
}
