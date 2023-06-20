import java.util.ArrayList;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class Animal extends Character{
	private String specie;
	private String pet;

	/**
     * Child class to specify an Animal
	 * Extended from parent class Character
	 * @param specie 	Specifies animal specie
	 * @param pet		Specify whether the animal is a pet or not
     */
	
	/**
     * Constructor to create animal character
     */
	public Animal(String bodyType, int age, String specie, String gender, String pet) {
		super(bodyType, age, gender);
		this.specie = specie;
		this.pet = pet;
	}

	/**
     * Getter function to obtain animal specie
	 * @return string specifying animal specie inputed
     */
	public String getSpecie() {
		return specie;
	}

	/**
     * Getter function to obtain animal pet condition
	 * @return string specifying pet status inputed
     */
	public String getPet() {
		return pet;
	}

	/**
     * Getter function to obtain character type (Animal)
	 * Overrided function from parent class
	 * @return string specifying animal character
     */
	@Override
	public String tipeChar() {
		return "animal";
	}
	
	/**
     * Function to describe animals
	 * Overrided function from parent class
	 * @return string specifying animal description
     */
	@Override
	public String describe() {
        String printPetStatus = getPetStatus(getSpecie(), getPet());
        String toPrint = "";

		/**
		* @param printPetStatus 	print if animal is pet or not
		* @param toPrint			Vatiable to return string to print
		*/

		// If animal is pet print it 
        if(printPetStatus != null){
            toPrint = getSpecie() + " " + printPetStatus;
        }
        else{
            toPrint = getSpecie();
        }
		return toPrint;
	}

	/**
     * Pet conditional for animals: Only 3 animals can be pets
	 * @return string specifying pet status
     */
    private String getPetStatus(String specie, String pet){
        String petStatus;
        if(specie.matches("dog|cat|ferret") && pet.equals("true")){
            petStatus = "is pet";
        }
        else{
            petStatus = null;
        }
        return petStatus;
    }

	/**
     * Function to get animals features
	 * Overrided function from parent class
	 * @return arraylist containing character features
     */
	@Override
	public ArrayList<String> getFeatures(){
		ArrayList<String> feat = new ArrayList<String>();

		// Add profession
		feat.add(getSpecie());
		// Add Age condition
		feat.add(getPetStatus(getSpecie(), getPet()));
		return feat;
	}

}
