import java.lang.Math;
import java.util.*;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class GetStatistics{

    /**
     * class to get the statistics of the run scenarios regardless if we are Judging or running (Simmulating) the program
     */


    /**
     * Method to get selected features from scenarios recieving arrays from the main class
     */
    public void getSelectedFeatures(
        RescueBot rescBot, 
        Location location, 
        ArrayList<String> selectedFeatures,
        ArrayList<Integer> rescuedAge){
        
        // Get characters in selected location
        ArrayList<Character> characters = location.getCharacters();

        // For each selected character
        for(int k=0; k<characters.size(); k++){
            // Add trespassing features
            if(location.getTrespassing().matches(".*yes$")){
                selectedFeatures.add("trespassing");
            }
            else if(location.getTrespassing().matches(".*no$")){
                selectedFeatures.add("legal");
            }
            // Add character type to features
            selectedFeatures.add(characters.get(k).tipeChar());
            // For each character feature
            for(String feat : characters.get(k).getFeatures()){
                // If the feature is not null
                if(feat != null){ 
                    // Add feature
                    if(feat == "is pet"){
                        selectedFeatures.add("pet");
                    }
                    else{
                        selectedFeatures.add(feat);
                    }
                }
            }
            // If rescued character is human store age
            if(characters.get(k).tipeChar() == "human"){
                rescuedAge.add(characters.get(k).getAge());
            }
        }
    }

    /**
     * Method to get general scenarios features recieving arrays from the main class
     */
    private void getGeneralFeatures(
        RescueBot rescBot, 
        ArrayList<Scenario> processedScenarios, 
        ArrayList<String> generalFeatures){

        ArrayList<Location> locations;
        ArrayList<Character> characters;

        // For each scenario
        for(int i=0; i<processedScenarios.size(); i++){
            // Get locations
            locations = processedScenarios.get(i).getLocations();
            // For each location
            for(int j=0; j<locations.size(); j++){
                // Select characters in location j
                characters = locations.get(j).getCharacters();
                // For each character
                for(int k=0; k<characters.size(); k++){
                    // Add trespassing feature to list
                    if(locations.get(j).getTrespassing().matches(".*yes$")){
                        generalFeatures.add("trespassing");
                    }
                    else if(locations.get(j).getTrespassing().matches(".*no$")){
                        generalFeatures.add("legal");
                    }
                    // Add character type to features
                    generalFeatures.add(characters.get(k).tipeChar());
                    // For each character feature
                    for(String feat : characters.get(k).getFeatures()){
                        // If the feature is not null
                        if(feat != null){ 
                            // Add character feature to list
                            if(feat == "is pet"){
                                generalFeatures.add("pet");
                            }
                            else{
                                generalFeatures.add(feat);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * method to calculate statistics of rescue according recieved features from previous methods
     */
    public void calculateStatistics(
        RescueBot rescBot, 
        ArrayList<Scenario> processedScenarios, 
        ArrayList<String> generalFeatures,
        ArrayList<String> selectedFeatures,
        LinkedHashSet<String> uniqueFeatures,
        ArrayList<String> featPropResc){

        //  Get all the features for the processed scenarios
        getGeneralFeatures(rescBot, processedScenarios, generalFeatures);
        // Get unique features
        uniqueFeatures.addAll(generalFeatures);

        // Counters to measure proportion of features rescued
        double counter_gen;
        double counter_chos;

        for(String uniqueFeat : uniqueFeatures){
            counter_gen = 0.00;
            counter_chos = 0.00;

            // Measure total frequency of unique features
            for(String Feat : generalFeatures){ 
                if(uniqueFeat.equals(Feat)){
                    counter_gen += 1.00;
                }
            }
            // Measure rescued frequency of unique features
            for(String Feat : selectedFeatures){ 
                if(uniqueFeat.equals(Feat)){
                    counter_chos += 1.00;
                }
            }

            // Store proportion in an array
            featPropResc.add(String.format("%.2f", Math.ceil(counter_chos/counter_gen*100)/100));
        }
    }

    /**
     * method to the statistics  of rescue according recieved features from previous method
     * @return ordered      Arraylist containing ordered statistics to print
     */
    public ArrayList<ArrayList<String>> orderStatistics(
        LinkedHashSet<String> uniqueFeatures,
        ArrayList<String> featPropResc,
        ArrayList<Integer> rescuedAge){

        for(String val : featPropResc){
        }
        
        // Array to get unique features among possible scenarios
        ArrayList<String> uniqueFeat = new ArrayList<String>(uniqueFeatures);

        // Array to store ordered statistics to print
        ArrayList<ArrayList<String>> ordered = new ArrayList<ArrayList<String>>();

        int minWord = 0; // Integer to get shortest word (To order alphabetically)

        // For each unique feature among possible scenarios 
        for(int i=0; i<uniqueFeat.size();i++){
            // Fill ordered vector matrix with pairs [Feature, Resc_rate]
            ordered.add(new ArrayList<String>());
            ordered.get(i).add(0, uniqueFeat.get(i));
            ordered.get(i).add(1, featPropResc.get(i));
        }
        
        // Iterate Matrix to order
        int max;
        for(int i=0; i<ordered.size(); i++){
            max = i;
            for(int j=i; j<ordered.size(); j++){
                // If we find a greater value than our current max save it
                if(Double.valueOf(ordered.get(j).get(1)) > Double.valueOf(ordered.get(max).get(1))){
                    max = j;
                }
                // If we find an equal value decide by alphabetical order
                else if(ordered.get(j).get(1).equals(ordered.get(max).get(1))){


                    // Get length of shortest word
                    minWord = Math.min(ordered.get(j).get(0).length(), ordered.get(max).get(0).length());
                    // Iterate and compare each letter position to order
                    outerloop:
                    for(int l=0; l<minWord; l++){
                        char a = ordered.get(j).get(0).charAt(l);
                        char b = ordered.get(max).get(0).charAt(l);
                        if(a != b){
                            if(a<b){
                                max = j;
                                break outerloop;
                            }
                            else{
                                break outerloop;
                            }
                        }
                        else{
                            continue;
                        }
                    }
                }
            }

            // Reorder the matrix
            if(max != i){
                ordered.add(i, new ArrayList<String>());
                ordered.get(i).add(0, ordered.get(max+1).get(0));
                ordered.get(i).add(1, ordered.get(max+1).get(1));
                ordered.remove(max+1);
            }
        }
        return ordered;
    }

}
