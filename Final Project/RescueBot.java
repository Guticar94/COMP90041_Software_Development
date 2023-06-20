import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class RescueBot {
    private static Scanner input = new Scanner(System.in);     

    private boolean mainMenu;                 
    private boolean deployMenu;		
    private boolean statisticsMenu;
    private boolean importedScenarios;
    private boolean showScenarios;
    private boolean logClosed;
    

    private String option;
    private String consent;
    private int countScenarios;
    private String mode;

    private ArrayList<Scenario> scenariosMatrix;
    private ArrayList<Scenario> processedScenarios;
    private ArrayList<String> generalFeatures;
    private ArrayList<String> selectedFeatures;
    private LinkedHashSet<String> uniqueFeatures;
    private ArrayList<String> featPropResc;
    private ArrayList<Integer> rescuedAge;
    private ArrayList<ArrayList<String>> ordered;

    /**
	 * Main class to run the RescueBot scenarios
     * @param input                 Scanner object instance for user inputs
     *
     * @param importedScenarios     States if there are imported scenarios in the inputed arguments (Func 0)
	 * @param mainMenu              States if the main menu is active (Func 1)
	 * @param deployMenu            States if the deploy menu is active (Func 3)
	 * @param statisticsMenu        States if the statistics menu is active (Func 4)
     * @param showScenarios         States if the number of scenarios has already been shown in the main menu (Func 1)
	 * @param logClosed             States whether if the logfile (if exists) is closed or not (Func 1.n)
     *
	 * @param option                String parameter to store the user inputed options            
     * @param consent               String parameter to store the conscent decition when judging (Func 1.1.1)
	 * @param countScenarios        Integer parameter to count the scenarios processed in the program
	 * @param mode                  String parameter to store the program mode (Judging, Simulating, Auditing)
     *
     * @param scenariosMatrix       Array List to store the inputed or simulated scenarios
	 * @param processedScenarios    Array List to store the processed scenarios
	 * @param generalFeatures       Array List to store general features of the scenarios characters to get statistics
     * @param selectedFeatures      Array List to store general features of the selected characters to get statistics
	 * @param uniqueFeatures        Set list to get the unique features to get statistics
	 * @param featPropResc          Array List to store the features proportions in the statistics
     * @param rescuedAge            Array List to store the age of the rescued humans for statistics
	 * @param ordered               Array List of ordered atributes for statistics
	 */
    

    public RescueBot(){
        mainMenu = true;
        deployMenu = false;
        statisticsMenu = false;
        importedScenarios = true;
        showScenarios = true;
        logClosed = true;


        option = "";
        consent = "";
        mode = "";

        countScenarios=0;

        scenariosMatrix = new ArrayList<Scenario>();
        processedScenarios = new ArrayList<Scenario>();
        generalFeatures = new ArrayList<String>();
        selectedFeatures = new ArrayList<String>();
        uniqueFeatures = new LinkedHashSet<String>();
        featPropResc = new ArrayList<String>();
        rescuedAge = new ArrayList<Integer>();
        ordered = new ArrayList<ArrayList<String>>();
    }

    /**
     * Program main function to run the project
     */
    public static void main(String[] args) {
        RescueBot rescBot = new RescueBot();
        FileReading readFile = new FileReading();
        GetStatistics statistics = new GetStatistics();
        commandInput inLine = new commandInput();
        CreateScenarios newScen = new CreateScenarios();
        FileWriting write = new FileWriting();
        Audit audit = new Audit();

        boolean iter = true;

        /**     
        * @param inLine         Function to instanciate the inLine class to identify the input parameters in the command line code setup
        * @param rescBot        Function to call the main class current instance
        * @param readFile       Function to instanciate the readFile class to open and read the input files
        * @param statistics     Function to instanciate the statistics class to calculate the scenarios decisions statistics
        * @param newScen        Function to instanciate the newScen class to simulate scenarios 
        * @param write          Function to instanciate the write class to write the log file results
        * @param audit          Function to instanciate the audit class to audit the logfile
        * @param iter           Boolean to access the main loop
        */

        // If there is help needed print help menu
        if(inLine.argsGet(args, "help") == "help"){
            inLine.printHelp();
            System.exit(0);
        }

        // Find if there is an inputed scenarios file (Func 0)
        rescBot.inputedScenarios(rescBot, readFile, inLine, args);


        // Iterate over program to deploy in different scenarios
        do{
            // Print main menu (Func 1)
            rescBot.printMainMenu(rescBot);  

            // Menu selection option (Func 1.1)
            rescBot.mainMenuOptions(rescBot, statistics, inLine, args, write, audit);

            // Create scenarios (If file was not provided). Otherwise the were already created in Func 0. (Func 2)
            rescBot.instanciateScenarios(rescBot, newScen);

            // Deploy bots acording Func 1 selections (Func3)
            rescBot.deployBots(rescBot, statistics, write);

            // Show deployment statistics (Func 4)
            rescBot.showStatistics(rescBot, statistics, write);

        }while(iter);

    }

    /**
     * Method to find and instanciate inputed Scenarios if any (Func 0)
     * If there is a inputed scenario it is set in the scenariosMatrix ArrayList
     * Otherwise the variable importedScenarios is set to false
    **/
    private void inputedScenarios(
        RescueBot rescBot, 
        FileReading readFile, 
        commandInput inLine, 
        String[] args){
        // Get input parameters (Get scenario path if any)
        String scenariosPath = inLine.argsGet(args, "scenarios");

        // If there is a path for the scenarios file read it
        if(scenariosPath != ""){

            // Print error if there is a file reading Exception
            readFile.readFile(scenariosPath);

            // Print welcome menu
            readFile.printWelcome();

            scenariosMatrix = readFile.readScenarios(scenariosPath);
        }
        // Else create scenarios
        else{
            // Print welcome menu
            readFile.printWelcome();
            importedScenarios = false;
        }
    }

    /**
     * Method to print main menu (Func 1)
    **/
	private void printMainMenu(RescueBot bot){
		if(mainMenu){
            // Only print imported scenarios once if there is a scenarios file
            if(importedScenarios && showScenarios){
                System.out.printf("%d scenarios imported.\n", scenariosMatrix.size());
                showScenarios = false;
            }
            System.out.println("Please enter one of the following commands to continue:");
            System.out.println("- judge scenarios: [judge] or [j]");
			System.out.println("- run simulations with the in-built decision algorithm: [run] or [r]");
			System.out.println("- show audit from history: [audit] or [a]");
            System.out.println("- quit the program: [quit] or [q]");
            System.out.print(">\s");
			option = input.nextLine();   // Main menu selection
		}
	}
     /**
     * Method to iterate over menu 1 options (Func 1.1)
    **/
	private void mainMenuOptions(
        RescueBot rescBot, GetStatistics statistics, commandInput inLine, String[] args, FileWriting write, Audit audit){
		if(mainMenu){
			switch(option){
				// judge scenarios [judge] or [j]
				case("judge"):
				case("j"):
                        // Run the scenarios and manually judge them
						rescBot.consentMenu(rescBot, inLine, args, write);
						mainMenu = false;
						deployMenu = true;
                        mode = "j";
					break;
				
				case("run"):
				case("r"):
                        // Run the scenarios and simulate the responses
                        mainMenu = false;
						deployMenu = true;
                        mode = "r";
                        consent = "yes";
                        if(logClosed){
                             // Get log filePath
                            String logPath = inLine.argsGet(args, "logs");
                            // Open filepath to write
                            write.setWriteFile(logPath);
                            logClosed = false;

                        }
                        write.writeLines("======================================");
                        write.writeLines("# Algorithm Audit");
                        write.writeLines("======================================");
					break;

				case("audit"):
				case("a"):
                        // Audit the scenarios response
                        audit.getFile(rescBot, inLine, args, input); // Get the logs file
                        audit.auditFile(rescBot); // Audit the logs file
                        enterReturn(rescBot); // Enter to return to the main menu
					break;

				case("quit"):
				case("q"):
                    System.exit(0);
					break;
				default:
                    System.out.println("Invalid response! ");
                    showScenarios = true;
			}
		}
	}

     /**
     * Concent menu (Func 1.1.1)
    **/
	private void consentMenu(RescueBot rescBot, commandInput inLine, String[] args, FileWriting write){
		boolean consentBool = true;
		while(consentBool){
			System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
			System.out.print(">");
			consent = input.nextLine();
			
            // If concent is correctly respond
			if(consent.equals("yes") || consent.equals("no")){
                // If concent is given
                if(consent.equals("yes")){
                    if(logClosed){
                        // Get log filePath
                        String logPath = inLine.argsGet(args, "logs");
                        // Open filepath to write
                        write.setWriteFile(logPath);
                        logClosed = false;
                    }
                    // Write to the log file the user audit header
                    write.writeLines("======================================");
                    write.writeLines("# User Audit");
                    write.writeLines("======================================");
                }
                // Set consent given to false
				consentBool = false;
			}
			else{
                // Retry the input
				System.out.print("Invalid response! ");
			}
		}
	}

     /**
     * Simulate scenarios if no scenarios file was given (Func 2)
    **/
    private void instanciateScenarios(RescueBot rescBot, CreateScenarios newScen){
        // In no inported scenarios
        if(importedScenarios == false){
            boolean wrongScen = true; // Set boolean to iterate

            while(wrongScen){
                // Ask for scenarios number to simulate
                System.out.println("How many scenarios should be run?");
                System.out.print(">");
                try{
                    // Confirm the correctness of the input
                    int val = Integer.valueOf(input.nextLine());  
                    option = Integer.toString(val);
                }
                catch(Exception e){
                    option = "0";
                }

                // If the input was correct
                if(Integer.valueOf(option)>0){
                    // Sumulate the scenarios
                    newScen.scenarios(rescBot.scenariosMatrix, Integer.valueOf(option));
                    importedScenarios = true;
                    wrongScen = false;
                }
                // Otherwise try to input a number again
                else{
                    System.out.print("Invalid response! ");
                }
            }
        }
        // Else: scenarios already instanciated in Func 0.
    }

    /**
     * Deploy bots according previous selections (Func 3)
     * This method deploy the bots in any case (Judging & running)
    **/
    private void deployBots(RescueBot rescBot, GetStatistics statistics, FileWriting write){
        // Deploy bot according to selection
        if(deployMenu){
            // If the mode is judging
            if(mode=="j"){
                // Print scenarios and choose locations to deploy (3.1)
                chooseLocation(rescBot, statistics, write);
            }
            // If the mode is running
            else if(rescBot.mode=="r"){
                // Simulate locations to deploy
                simulateLocation(rescBot, statistics);
            }
        }
    }

    /**
     * Method to print scenarios and specify location to deploy rescueBot if we are judging (Func 3.1)
    **/
    private void chooseLocation(RescueBot rescBot, GetStatistics statistics, FileWriting write){
        int deployLocation;
        Location location;
        ArrayList<String> features = new ArrayList<String>();

        if(deployMenu){
            // Iterate 3 times over the scenarios
            for(int i=1; i<4; i++){

                // If the counter of processed scenarios is lesser than the available scenarios
                if(countScenarios < scenariosMatrix.size()){

                    //Cache current scenario to process
                    processedScenarios.add(scenariosMatrix.get(countScenarios));

                    // Display scenario desctription
                    System.out.println(processedScenarios.get(countScenarios).displayDescription());

                    // Take deploy decision
                    deployLocation = rescBot.deployRescueBot(countScenarios, processedScenarios);
                    
                    // Save location where bot was deployed
                    location = processedScenarios.get(countScenarios).getLocations().get(deployLocation);

                    // Save features where bot was deployed
                    statistics.getSelectedFeatures(rescBot, location, selectedFeatures, rescuedAge);

                    // Save interaction in the logfile
                    if(consent.equals("yes")){
                        write.writeLines(processedScenarios.get(countScenarios).displayDescription());
                        write.writeLines("To which location should RescueBot be deployed?");
                        write.writeLines(">" + Integer.toString(deployLocation+1));
                    }

                    // Increase the selected scenarios counter by 1
                    countScenarios += 1;
                }
            }
        }
        if(option != ""){
            // Activate statistics function (Func 4) when 3 scenarios have been chosen
            deployMenu = false;
            statisticsMenu = true;
        }
    }
    
     /**
     * Submethod to specify location to deploy rescueBot if we are judging (Func 3.1.1)
     * @return location to deploy the rescue bot
    **/
    private int deployRescueBot(int scenarioNumber, ArrayList<Scenario> scenariosMatrix){
        int deployLocation = 0;
        boolean depNum = true;
        while(depNum){
            // Select location to deploy rescue bot
            System.out.println("To which location should RescueBot be deployed?");
            System.out.print(">");
            // Evaluate correctness of the input
            try{
                // Set location number
                deployLocation = Integer.parseInt(input.nextLine());
            }
            catch(Exception e){
                    deployLocation = 0;
                }
            // If the input is not correct try again
            if((deployLocation < 1) || (scenariosMatrix.get(scenarioNumber).countLocations() < deployLocation)){
                System.out.print("Invalid response! ");
            }
            // otherwise continue
            else{
                depNum = false;
            }
        }
        return deployLocation - 1;
    }

    /**
     * Method to specify location to deploy rescueBot if we are simulating (Func 3.2)
    **/
    public void simulateLocation(RescueBot rescBot, GetStatistics statistics){
        int deployLocation;
        Location location;
        // Iterate over all the scenarios
            for(int i=0; i<scenariosMatrix.size(); i++){
                //Cache current scenario to process
                processedScenarios.add(scenariosMatrix.get(i));

                // Take deploy decision
                deployLocation = (int)(Math.random()*scenariosMatrix.get(i).countLocations());

                // Save location where bot was deployed
                location = processedScenarios.get(i).getLocations().get(deployLocation);
                
                // Save features where bot was deployed
                statistics.getSelectedFeatures(rescBot, location, selectedFeatures, rescuedAge);
            }
        // Activate statistics menu (Menu 3) when all scenarios have been chosen
        deployMenu = false;
        statisticsMenu = true;
    }

    /**
     * Method to show statistics (Func 4)
    **/
    public void showStatistics(RescueBot rescBot, GetStatistics statistics, FileWriting write){
        String stats = ""; // String to store statistics

        if(statisticsMenu){
            // Calculate deploying statistics
            statistics.calculateStatistics(
                rescBot, processedScenarios, generalFeatures, 
                    selectedFeatures, uniqueFeatures, featPropResc);

            // Save interaction in the logfile
            if(consent.equals("yes")){
                write.writeLines(stats);
            }
            // Print statistics
            stats += " ======================================\n";
            stats += "# Statistic\n";
            stats += "======================================\n";
            stats += "- % SAVED AFTER " + processedScenarios.size() + " RUNS\n";

            // Calculate statistics
            ordered = statistics.orderStatistics(uniqueFeatures, featPropResc, rescuedAge);

            // Store statistic in a string
            for(int i=0; i<ordered.size(); i++){
                stats += ordered.get(i).get(0) + ": ";
                stats += ordered.get(i).get(1) + "\n";
            }
            stats += "--\n";

            double totAge = 0;
            for(int i=0; i<rescuedAge.size(); i++){
                totAge += rescuedAge.get(i);
            }
            double avgAge = totAge/rescuedAge.size();
            stats += "average age: " + String.format("%.2f", avgAge);

            // Print statistics
            System.out.println(stats);

            // Save interaction in the logfile
            if(consent.equals("yes")){
                write.writeLines(stats);
            }
            // Ask to user if he wants to continue or return to main menu and proceed (Func 4.1)
            rescBot.repeatReturn(rescBot);
        }
        
    }

    /**
     * Method to repeat or return (Func 4.1)
    **/
    private void repeatReturn(RescueBot rescBot){
        boolean cont = true;
        while(cont){
            if(mode=="j"){
                // Ask the user if he wants to return or repeat
                System.out.println("Would you like to continue? (yes/no)");
                System.out.print(">");

                option = input.nextLine();
            }
            else{
                option = "no";
            }
            // If the response was adequate
            if(option.equals("yes") || option.equals("no")){
                cont = false;
                //  If yes, continue
                if(option.equals("yes") && countScenarios < scenariosMatrix.size()){
                    featPropResc = new ArrayList<String>();
                    statisticsMenu = false;
                    deployMenu = true;
                }
                // Otherwise return to the main menu
                else {
                    enterReturn(rescBot); // Function to press enter to return
                }
            }
            // Else reiterate until satisfactory input
        }
    }

    /**
     * Method return when enter is pressed (Func 4.1.1)
    **/
    private void enterReturn(RescueBot rescBot){
        System.out.println(" That's all. Press Enter to return to main menu.");
        System.out.print("> ");
        option = input.nextLine();
        if(option == ""){
            rescBot.resetScenarios(rescBot);
        }
    }

     /**
     * Sub-Method to restart statistics (Func 4.1.2)
     * This method restart many the scenario parameters 
     * when the user goes back to the main menu
    **/
    private void resetScenarios(RescueBot rescBot){
        statisticsMenu = false;
        mainMenu = true;
        showScenarios = false;
        option = "";
        consent = "";
        mode = "";
        countScenarios=0;
        processedScenarios = new ArrayList<Scenario>();
        generalFeatures = new ArrayList<String>();
        selectedFeatures = new ArrayList<String>();
        uniqueFeatures = new LinkedHashSet<String>();
        featPropResc = new ArrayList<String>();
        rescuedAge = new ArrayList<Integer>();
        ordered = new ArrayList<ArrayList<String>>();
    }
}





















    
