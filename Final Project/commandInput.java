/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class commandInput{

    /**
     * Class to extract the command line inputs
     */

    /**
     * Method to get the command line inputs acording the parameter configuration
     @return Path       Path of the inputed file
     */
    public String argsGet(String[] args, String path){
        String Path ="";

        /**
         * @param args                     Line input values
         * @param Path (by function)       String stating the desired path file (scenarios, logs, help)
         * @param Path (declared)          Path of the inputed file
        */

        // value in the command line inputed values
        for(int i = 0; i < args.length; i++){
            // Set searching condition
            switch(path){
                // If the desired option is scenarios
                case("scenarios"):
                    // If there is a scenarios file trigger
                    if(args[i].matches("^(-s|--scenarios)$")){
                        // If there is a scenarios file
                        if(i+1 < args.length){
                            // If the file is in format csv
                            if(args[i+1].matches("^.*.csv$")){
                                // Get path string
                                Path = args[i+1];
                            }
                        }
                    }
                break;
                // If the desired option is logs
                case("logs"):
                    // If there is a logs file trigger
                    if(args[i].matches("^(-l|--log)$")){
                        // If there is a logs file
                        if(i+1 < args.length){
                            // If the file is in format .csv, .txt, or .log
                            if(args[i+1].matches("^.*.(csv|txt|log)$")){
                                // Get path string
                                Path = args[i+1];
                            }
                        }
                    }
                break;
                // If the desired option is help
                case("help"):
                    // If there is a help trigger
                    if(args[i].matches("^(-h|--help)$")){
                        // Set string to help
                        Path = "help";
                    }
                    // If there is another trigger
                    else if(args[i].matches("^(-l|--log|-s|--scenarios)$")){
                        // And there is a file
                        if(i+1 < args.length){
                            // But it does not match the options
                            if(!args[i+1].matches("^.*.(csv|txt|log)$")){
                                // Set string to help
                                Path = "help";
                            }
                        }
                    }
                break;
            }
        }
    return Path;
    }

    /**
     * Method to print the help menu
     */
    public void printHelp(){
        System.out.println("RescueBot - COMP90041 - Final Project");
        System.out.println("");
        System.out.println("Usage: java RescueBot [arguments]");
        System.out.println("");
        System.out.println("Arguments:");
        System.out.println("-s or --scenarios\tOptional: path to scenario file");
        System.out.println("-h or --help\t\tOptional: Print Help (this message) and exit");
        System.out.println("-l or --log\t\tOptional: path to data log file");
    }

}
