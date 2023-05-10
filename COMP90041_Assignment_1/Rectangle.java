import java.util.Scanner;
public class Rectangle {

    // Instanciating variables
    private static int figureWidth;         // Figure width 
    private static int figureHeight;        // Figure width 
    private static String[] array_Strings;  // Array to save values to return

    // Constructor to inicialize variables
    public Rectangle(){
        figureWidth = 0;
        figureHeight = 0;
        array_Strings = new String[3];       
    }

    // Method to set figure dimensions and characters
    public String[] setFigure(
        int width,      // Canvas width
        int height,     // Canvas height
        Scanner input   // Scanner variable to read inputs
        ){                     
        // Loop to asure the figure width size dimensions
        Boolean wrongFigure = true; // Bolean to acces loop of figure size dimension
        while(wrongFigure){
            // Get the figure side width
            System.out.println("width:");
            figureWidth = input.nextInt();
            // If the input width shape is so big, it must be input again
            if (figureWidth > width){
                System.out.printf(
                    "Error! The width is too large (Current canvas size is %dx%d). Please try again.\n",
                    width ,height);
            }
            // If the input size is accepted finish the loop 
            else {wrongFigure = false;}   // Set the loop boolean to false
        }
        // Loop to asure the figure height size dimensions
        wrongFigure = true; // Set the loop boolean to true
        while(wrongFigure){
           // Get the height side lenght
            System.out.println("height:");
            figureHeight = input.nextInt();

            // If the input figure shape is so big, it must be input again
            if (figureHeight > height){
                System.out.printf(
                    "Error! The height is too large (Current canvas size is %dx%d). Please try again.\n",
                    width ,height);
            }
            // If the input size is accepted finish the loop 
            else {wrongFigure = false;}   // Set the loop boolean to false}
        }
        // Get the character to print the rectangle
        System.out.println("Printing character:");
        String figureChar = input.next();   // Figure char
        // Fill array with rectangle variables
        array_Strings[0] = String.valueOf(figureWidth);
        array_Strings[1] = String.valueOf(figureHeight);
        array_Strings[2] = figureChar;
        
        return array_Strings;
    }

    // Ask to draw another rectangle
    public String drawAnother(Scanner input){
        System.out.println("Draw another rectangle (Y/N)?");
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
        array_Strings[2] = "false";
        switch(kindZMChar){
            case("i"): //Zoom in
                if ((posY + figureHeight >= canvasHeight)||(posX + figureWidth >= canvasWidth)){
                    System.out.println("This rectangle reaches its limit. You cannot make it bigger!");
                }
                else{
                    figureHeight += 1;
                    figureWidth += 1;
                }
                break;
            case("o"):  //Zoom out
                if ((figureHeight <= 1)||(figureWidth <= 1)){
                    System.out.println("This rectangle reaches its limit. You cannot make it smaller!");
                }
                else{
                    figureHeight -= 1;
                    figureWidth -= 1;
                }
                break;
            default:   //Go back to menu 1
                array_Strings[2] = "true";
        }
        // Return new rectangle settings
        array_Strings[0] = String.valueOf(figureHeight);
        array_Strings[1] = String.valueOf(figureWidth);
        return array_Strings;
    }
}
