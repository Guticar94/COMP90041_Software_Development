import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class Human extends Character{
	private String profession;
	private String pregnant;

	/**
     * Child class to specify an Human
	 * Extended from parent class Character
	 * @param profession 	Specifies human profession
	 * @param pregnant		Specify whether the human is a pregnanto or not (This could have been implemented with an instance for the class human)
     */
	
	/**
     * Constructor to create human character
     */
	public Human(String bodyType, int age, String profession, String gender, String pregnant) {
		super(bodyType, age, gender);
		this.profession = profession;
		this.pregnant = pregnant;
	}

	/**
     * Getter function to obtain Human Profession
	 * @return string specifying profession
     */
	public String getProfession() {
		return profession;
	}

	/**
     * Getter function to obtain Human Pregnant status
	 * @return string specifying pregnant status
     */
	public String getPregnant() {
		return pregnant;
	}

	/**
     * Getter function to obtain character type (Human)
	 * Overrided function from parent class
	 * @return String specifying human character
     */
	@Override
	public String tipeChar() {
		return "human";
	}

	/**
     * Function to describe humans
	 * Overrided function from parent class
	 * @return String to print human description
     */
	@Override
	public String describe() {
		String printAgeRange = getAgeRange(getAge());
		String printProfession = getProfessionStatus(printAgeRange, getProfession());
		String printPregnant = getPregnantStatus(getGender(), getPregnant());
		String toPrint = "";

	/**
     * @param printAgeRange 	Get human ageCategory
	 * @param printProfession	Get human profession
	 * @param printPregnant		Get pregnant status
	 * @param toPrint			Vatiable to return string to print
     */

		// If profession or pregnant aren't null, print them
		if(printProfession!=null && printPregnant!=null){
			toPrint = getBodyType() + " " + printAgeRange + " " + printProfession + " " + getGender() + " " + printPregnant;
		}
		// If profession is null dont print it
		else if (printProfession==null && printPregnant!=null){
			toPrint =  getBodyType() + " " + printAgeRange + " " + getGender() + " " + printPregnant;
		}
		// If pregnant is null dont print it
		else if (printProfession!=null && printPregnant==null){
			toPrint =  getBodyType() + " " + printAgeRange + " " + printProfession + " " + getGender();
		}
		// If profession and pregnant are null, dont print them
		else if (printProfession==null && printPregnant==null){
			toPrint =  getBodyType() + " " + printAgeRange + " " + getGender();
		}
		return toPrint;
	}
	
	/**
     * Age conditional for Humans
	 * @return string specifying age rage classification
     */
	private String getAgeRange(int age){
		String ageRange = "";
		// Conditional to classify age
		if(age>=0 && age<=4){
			ageRange = "baby";
		}
		else if(age>4 && age<=16){
			ageRange = "child";
		}
		else if(age>16 && age<=68){
			ageRange = "adult";
		}
		else if(age>68){
			ageRange = "senior";
		}
		return ageRange;
	}

	/**
     * Profession conditional for Humans
	 * @return string specifying profession if applies
     */
	private String getProfessionStatus(String ageRange, String profession){
		String professionStatus;
		// Conditional to classify profession
		if(ageRange == "adult"){
		    professionStatus = profession;
		}
		else{
		    professionStatus = null;
		}
		return professionStatus;
	}

	/**
     * Pregnant conditional for Humans
	 * @return string specifying pregnant condition if applies
     */
    private String getPregnantStatus(String gender, String pregnant){  
		String pregnantStatus;
		// Conditional to classify pregnant
		if((gender.equals("female")) && (pregnant.equals("true"))){
			pregnantStatus = "pregnant";
		}
		else{
			pregnantStatus = null;
		}
		return pregnantStatus;
	}    
	
    /**
     * Function to get human features
	 * Overrided function from parent class
	 * @return arraylist containing character features
     */
	@Override
	public ArrayList<String> getFeatures(){

		String AgeRange = getAgeRange(getAge());
		String Profession = getProfessionStatus(AgeRange, getProfession());
		String Pregnant = getPregnantStatus(getGender(), getPregnant());

		ArrayList<String> feat = new ArrayList<String>();

		// Add Body Type
		feat.add(getBodyType());
		// Add Age status
		feat.add(AgeRange);
		// Add Profession
		feat.add(Profession);
		// Add Gender
		feat.add(getGender());
		// Add Pregnant condition
		feat.add(Pregnant);
		return feat;
	}
	
}

