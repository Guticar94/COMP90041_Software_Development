import java.util.Scanner;
public class Triangle {

    // Instanciating variables
    private static int figureShape;         // Figure shape 
    private static String[] arrayStrings;   // Array to save values to return

    // Constructor to inicialize variables
    public Triangle(){
        figureShape = 0;
        arrayStrings = new String[2];   
    }

    // Method to set figure dimensions and characters
    public String[] setFigure(
        int width,      // Canvas width
        int height,     // Canvas height
        Scanner input   // Scanner variable to read inputs
        ){         
        // Loop to asure the figure size dimensions
        Boolean wrongFigure = true; // Bolean to acces loop of figure size dimension
        while(wrongFigure){
            // Get the figure side lenght
            System.out.println("Side length:");
            figureShape = input.nextInt();
            // The input figure shape is so big, it must be input again
            if (figureShape > width){
                System.out.printf(
                    "Error! The side length is too long (Current canvas size is %dx%d). Please try again.\n",
                    width ,height);
            }
            // If the input size is accepted finish the loop
            else {wrongFigure = false;}   // Set the loop boolean to false
        }
        // Get the character to print the triangle
        System.out.println("Printing character:");
        String figureChar = input.next(); // Asign figure char
        arrayStrings[0] = String.valueOf(figureShape);
        arrayStrings[1] = figureChar;
        return arrayStrings;
    }

    // Ask to draw another triangle
    public String drawAnother(Scanner input){
        System.out.println("Draw another triangle (Y/N)?");
        return input.next().toLowerCase();
    }

    // Zoom in/out according input
    public String[] zoomResult(
        String kindZMChar,
        int canvasWidth,
        int canvasHeight,
        int posX,
        int posY
    ){
        arrayStrings[1] = "false";
        switch(kindZMChar){
            case("i"): //Zoom in
                if ((posX + figureShape >= canvasWidth)||(posY + figureShape >= canvasHeight)){
                    System.out.println("This triangle reaches its limit. You cannot make it bigger!");
                }
                else{
                    figureShape += 1;
                }
                break;
            case("o"):  //Zoom out
                if (figureShape <= 1){
                    System.out.println("This triangle reaches its limit. You cannot make it smaller!");
                }
                else{
                    figureShape -= 1;
                }
                break;
            default:   //Go back to menu 1
                arrayStrings[1] = "true";
        }
        arrayStrings[0] = String.valueOf(figureShape);
        return arrayStrings;
    }
}





