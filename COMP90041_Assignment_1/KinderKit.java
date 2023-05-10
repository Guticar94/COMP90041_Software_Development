import java.util.Scanner;
/**
 * COMP90041, Sem1, 2023: Assignment 1
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */


public class KinderKit {
    // Instanciating variables
    private static Scanner input;           // Scanner object instance
    private static int option;              // Desition integer: Menu 0 option chosen (1,2,3,4)
    private static String selectedFigure;   // String to set the current figure

    // Constructor to inicialize variables
    public KinderKit(){     
        input = new Scanner(System.in);
        option = 0;
        selectedFigure = "";
    }

   // Loop to print main menu and read input
    public void menu(){
        System.out.println("Please select an option. Type 4 to exit.");
        System.out.println("1. Draw triangles");
        System.out.println("2. Draw rectangles");
        System.out.println("3. Update drawing canvas settings");
        System.out.println("4. Exit");
        option = input.nextInt(); 
        switch(option){
            case(1):
                selectedFigure = "Triangle";
            break;
            case(2):
                selectedFigure = "Rectangle";
            break;
            default:
                selectedFigure = "";
        }
    }
    
    // Main method
    public static void main(String[] args) {
        //________________________________________________________________________________________________________________
        //___________________________________VARIABLES INSTANCIATION AND INICIALIZATION___________________________________
        //________________________________________________________________________________________________________________
        // Classes instanciation
        KinderKit Kinder = new KinderKit();         // KinderKit object 
        Triangle triangle = new Triangle();         // Triangle object 
        Rectangle rectangle = new Rectangle();      // Rectangle object 
        DrawingCanvas canvas = new DrawingCanvas(); // Canvas object 

        // Set canvas params
        int canvasWidth = Integer.parseInt(args[0]);    // Input 0 canvasWidth
        int canvasHeight = Integer.parseInt(args[1]);   // Input 1 canvasHeight
        String canvasChar = args[2];                    // Input 2 canvasChar

        // State booleans to iterate
        boolean iter = true;                     // States the main loop activity (True for loop on)
        boolean mainMenu = true;                 // States if the main menu menu is active (menu 0)
        boolean zoomMoveMenu = false;            // States if the zoom/move menu is active (menu 1)
        boolean zoomOrMoveInteraction = false;   // States if the zooming/moving interactions are active (menu 2)
        boolean anotherfig = false;              // States if the another selection figure menu is active

        String zoomMoveChar= "";    // Desition character: Canvas action (z/m)
        String kindZMChar= "";      // Desition character: Kind of zoom/move (i/o)
        String DrawAnother= "";     // Desition character: Draw another figure (y/n)

        int figureShape = 0;    // Shape (size) of the inputed figure (triangle)
        int figureWidth = 0;    // Width (size) of the inputed figure (rectangle)
        int figureHeight = 0;   // Height (size) of the inputed figure (rectangle)
        String figureChar = ""; // Characters to print the figure

        int posX = 0;   // X position of the figure
        int posY = 0;   // Y position of the figure

        String[] arr_Strings = new String[3];   // Array of strings for figures and zoom in/out returns

        //________________________________________________________________________________________________________________
        //___________________________________________________MAIN CLASS___________________________________________________
        //________________________________________________________________________________________________________________
        // Drawing menu options (Menu 0)
        System.out.println("----DIGITAL KINDER KIT: LET'S PLAY & LEARN----");
        canvas.canvasSettings(canvasWidth, canvasHeight, canvasChar);   // print canvas settings
        Kinder.menu();  // print main menu and get input

        // Loop to interact with the figures
        do{
            if(mainMenu){ // If we are in menu 0
                // Multiway branching over menu input
                switch(option){
                    case(1): // 1. Draw triangles
                        arr_Strings = triangle.setFigure(canvasWidth, canvasHeight, input);
                        figureShape = Integer.parseInt(arr_Strings[0]);
                        figureChar = arr_Strings[1];
                        canvas.printCanvas(
                            canvasWidth, canvasHeight, canvasChar, posX, posY,
                            figureShape, figureWidth, figureHeight, figureChar, selectedFigure);
                        figureWidth = 0;
                        figureHeight = 0;
                        break;
                    case(2): // 2. Draw rectangles
                        arr_Strings = rectangle.setFigure(canvasWidth, canvasHeight, input);
                        figureWidth = Integer.parseInt(arr_Strings[0]);
                        figureHeight = Integer.parseInt(arr_Strings[1]);
                        figureChar = arr_Strings[2];
                        canvas.printCanvas(
                            canvasWidth, canvasHeight, canvasChar, posX, posY,
                            figureShape, figureWidth, figureHeight, figureChar, selectedFigure);
                        figureShape = 0;
                        break;
                    case(3): // 3. Update drawing canvas settings
                        System.out.print("Canvas width: ");
                        canvasWidth = input.nextInt();
                        System.out.print("Canvas height: ");
                        canvasHeight = input.nextInt();
                        System.out.print("Background character: ");
                        canvasChar = input.next();
                        System.out.println("Drawing canvas has been updated!\n");
                        canvas.canvasSettings(canvasWidth, canvasHeight, canvasChar);    // print canvas settings
                        Kinder.menu();              // print main menu and get input
                        continue;         // reset loop
                    case(4): // 4. Exit
                        System.out.println("Goodbye! We hope you had fun :)");
                        System.exit(0);
                        break;
                    // Print the menu 0 again
                    default:
                        System.out.println("Unsupported option. Please try again!");
                        Kinder.menu();    // print main menu and get input
                        continue;         // reset loop
                    }
                    // Go to zoom/move menu (menu 1)
                    mainMenu = false;
                    zoomMoveMenu = true;
                }

            // Zoom/moving menu options (Menu 1)
            if(zoomMoveMenu){
                // Zooming menu answer
                System.out.println("Type Z/M for zooming/moving. Use other keys to quit the Zooming/Moving mode.");
                zoomMoveChar = input.next().toLowerCase();
                switch(zoomMoveChar){
                    // If the input contains z or m go to next menu (menu 2)
                    case("z"):
                    case("m"):
                        canvas.printCanvas(
                            canvasWidth, canvasHeight, canvasChar, posX, posY,
                            figureShape, figureWidth, figureHeight, figureChar, selectedFigure);
                        zoomMoveMenu = false;
                        zoomOrMoveInteraction = true;
                        break;
                    // If not ask to draw another figure
                    default:
                        posX = 0; // Reset position X
                        posY = 0; // Reset positions Y
                        do{
                            anotherfig = false;
                            switch(selectedFigure){
                                case("Triangle"):  // Ask to draw another triangle
                                    DrawAnother = triangle.drawAnother(input);
                                    break;
                                case("Rectangle"): // Ask to draw another rectangle
                                    DrawAnother = rectangle.drawAnother(input);
                                    break;
                                default:           // Return error
                                    System.out.println("Fatal Error!. Actual figure set to " + selectedFigure);
                                    System.exit(0);
                            }
                            switch(DrawAnother){   // If yes, reset figure dims
                                case("y"):
                                    switch(selectedFigure){
                                        case("Triangle"):  // Set triangle
                                            arr_Strings = triangle.setFigure(canvasWidth, canvasHeight, input);  
                                            figureShape = Integer.parseInt(arr_Strings[0]);
                                            figureChar = arr_Strings[1];
                                            break;
                                        case("Rectangle"):  // Set rectangle
                                            arr_Strings = rectangle.setFigure(canvasWidth, canvasHeight, input);
                                            figureWidth = Integer.parseInt(arr_Strings[0]);
                                            figureHeight = Integer.parseInt(arr_Strings[1]);
                                            figureChar = arr_Strings[2];
                                            break;
                                    }
                                    canvas.printCanvas(
                                        canvasWidth, canvasHeight, canvasChar, posX, posY,
                                        figureShape, figureWidth, figureHeight, figureChar, selectedFigure);
                                    break;
                                case("n"): // If not, go to main menu (menu 0)
                                    Kinder.menu();  // print main menu and get input
                                    mainMenu = true;
                                    zoomMoveMenu = false;
                                    break;
                                default:      // Otherwise ask to input a correct value
                                    System.out.println("Unsupported option. Please try again!");
                                    anotherfig = true;
                            }
                        }while(anotherfig);
                }
            }

            // Zoom/move interactions menu options (Menu 2)
            while(zoomOrMoveInteraction){
                switch(zoomMoveChar){

                    // Zoom interactions menu options (Menu 2.1)
                    case("z"):
                        // Kind of zooming answer
                        System.out.println(
                            "Type I/O to zoom in/out. Use other keys to go back to the Zooming/Moving menu.");
                        kindZMChar = input.next().toLowerCase();
                        
                        // Conditions according user input
                        if(selectedFigure == "Triangle"){
                            arr_Strings = triangle.zoomResult(kindZMChar, canvasWidth, canvasHeight, posX, posY);
                            figureShape = Integer.parseInt(arr_Strings[0]);
                            if (arr_Strings[1].equals("true")){
                                zoomMoveMenu = true;
                                zoomOrMoveInteraction = false;
                            }
                        }
                        if(selectedFigure == "Rectangle"){
                            arr_Strings = rectangle.zoomResult(kindZMChar, canvasWidth, canvasHeight, posX, posY);
                            figureHeight = Integer.parseInt(arr_Strings[0]);
                            figureWidth = Integer.parseInt(arr_Strings[1]);
                            if (arr_Strings[2].equals("true")){
                                zoomMoveMenu = true;
                                zoomOrMoveInteraction = false;
                            }
                        }
                    break;

                    // Move interactions menu options (Menu 2.2)
                    case("m"):
                        // Kind of movement answer
                        System.out.println(
                            "Type A/S/W/Z to move left/right/up/down."
                                +" Use other keys to go back to the Zooming/Moving menu.");
                        kindZMChar = input.next().toLowerCase();
                        switch(kindZMChar){
                            case("a"):  // Left movement
                                if (posX == 0){
                                    System.out.printf("You cannot move this %s outside of the drawing canvas!\n", 
                                        selectedFigure.toLowerCase());
                                }
                                else{
                                    posX -= 1;
                                }
                                break;
                            case("s"): // Right movement
                                    if ((posX + figureShape >= canvasWidth)||(posX + figureWidth >= canvasWidth)){
                                        System.out.printf("You cannot move this %s outside of the drawing canvas!\n", 
                                            selectedFigure.toLowerCase());
                                    }
                                    else{
                                        posX += 1;
                                    }
                                break;
                            case("w"):  // Up movement
                                if (posY == 0){
                                    System.out.printf("You cannot move this %s outside of the drawing canvas!\n", 
                                        selectedFigure.toLowerCase());
                                }
                                else{
                                    posY -= 1;
                                }
                                break;
                            case("z"):  // Down movement
                                if ((posY + figureShape >= canvasHeight)||(posY + figureHeight >= canvasHeight)){
                                    System.out.printf("You cannot move this %s outside of the drawing canvas!\n",
                                        selectedFigure.toLowerCase());
                                }
                                else{
                                    posY += 1;
                                }
                                break;
                            default:
                                zoomMoveMenu = true;
                                zoomOrMoveInteraction = false;
                        }
                    break;
                    default:
                        System.exit(0);
                }
                canvas.printCanvas(
                            canvasWidth, canvasHeight, canvasChar, posX, posY,
                            figureShape, figureWidth, figureHeight, figureChar, selectedFigure); // Print canvas zooming results
            }  

        }while(iter);
    }
}
