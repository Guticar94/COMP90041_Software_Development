/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class KinderKit {
  private boolean iter = true;                     // States the main loop activity (True for loop on)
  private boolean mainMenu = true;                 // States if the main menu is active (menu 0)
  private boolean predefinedObjMenu = false;       // States if the predefined object menu is active (menu 1)
  private boolean freestyleMenu = false;           // States if the freestyle Menu is active (menu 2)
  private boolean editCanvasMenu = false;          // States if the edit canvas Menu is active (menu 3)
  private boolean ZMRMenu = false;                 // States if the zoom/move/rotate menu is active (menu 3.1)
  private boolean ZMRInteraction = false;          // States if the zoom/move/rotate interactions menus are active (menu 3.1.n, n={1,2,3})

  // Set canvas params
  private int canvasWidth;                           // Input 0 canvasWidth
  private int canvasHeight;                          // Input 1 canvasHeight
  private String canvasChar;                         // Input 2 canvasChar

  // Array list to set figure params in a matrix
  private ArrayList<ArrayList<String>> triangleMatrix = new ArrayList<>(1);

  private int VEC_POS = 3;
  private String[] canvStup = new String[VEC_POS]; // Vectore to store canvas settings (Width, Height, Char)

  // Strings to set kind of interactions within the menus
  private String ZMRChar = "";              // Kind of zoom/move/rotate character selected
  private String kindZMRInterChar = "";     // Kind of zoom/move/rotate interaction character selected

  private String canvasType = ""; // String to know if we are on freestyle mode or predefined

  private static Scanner input;           // Scanner object instance
  private static int option;              // Desition integer: Menu 0 option chosen (1,2,3)
  private int CONS_OPT = 10;

  // Constructor to inicialize static variables
    public KinderKit(){     
        input = new Scanner(System.in);
        option = CONS_OPT;
    }
  // Setters: Set canvas params
  public void setCanvasWidth(int canvasWidth){
    this.canvasWidth = canvasWidth;
  }
  public void setCanvasHeight(int canvasHeight){
    this.canvasHeight = canvasHeight;
  }
  public void setCanvasChar(String canvasChar){
    this.canvasChar = canvasChar;
  }

  // Helper method to add new row to triangle matrix
  private void newTriangle(String figureShape, String figureChar, String PosX, String PosY, String rotState){
    triangleMatrix.add(new ArrayList());
    triangleMatrix.get(triangleMatrix.size()-1).add(0, figureShape);   // figureShape: 0
    triangleMatrix.get(triangleMatrix.size()-1).add(1, figureChar);    // figureChar: 1
    triangleMatrix.get(triangleMatrix.size()-1).add(2, PosX);          // PosX: 2
    triangleMatrix.get(triangleMatrix.size()-1).add(3, PosY);          // PosY: 3
    triangleMatrix.get(triangleMatrix.size()-1).add(4, rotState);      // rotState: 4
  }

  public static void main(String[] args) {
    //DON'T TOUCH LINES 8 to 59
    //Check program arguments
    if (args.length != 1) {
      System.out.println("This program takes exactly one argument!");
      return;
    }

    //Read sample drawing from file
    Scanner inputStream = null;
    char[][] bitmap = null;
    int rows = 0, cols = 0;
    char bgChar;

    try {
      inputStream = new Scanner(new FileInputStream(args[0]));
      String line;
 
      //Read the first line
      if (inputStream.hasNextLine()) {
        line = inputStream.nextLine();
        String[] tmps = line.split(",");
        if (tmps.length != 3) {
          System.out.println("The given file is in wrong format!");
          return;
        } else {
          rows = Integer.parseInt(tmps[0]);
          cols = Integer.parseInt(tmps[1]);
          bgChar = tmps[2].charAt(0);
          bitmap = new char[rows][cols];
        }
      } else {
        System.out.println("The given file seems empty!");
        return;
      }

      //Read the remaining lines
      int rowIndex = 0;
      while (inputStream.hasNextLine()) {
        line = inputStream.nextLine();
        String[] tmps = line.split(",");
        for(int i = 0; i < tmps.length; i++) {
          bitmap[rowIndex][i] = tmps[i].charAt(0);
        }
        rowIndex++;
      }
      //Close the file input stream
      inputStream.close();
    } catch (FileNotFoundException e) {
      System.out.println("The given file is not found!");
      return;
    }

    //TODO: Write your code from here

    // Classes instanciation
    KinderKit kinder = new KinderKit();           // KinderKit object 
    DrawingCanvas canvas = new DrawingCanvas();   // Canvas object 
    DrawingTask drawingTask = new DrawingTask();  // Common drawings object
    Triangle triangle = new Triangle();           // Triangle object 

    boolean iter = true; // Bolean for the main loop

    kinder.setCanvasWidth(cols);                           // Input 0 canvasWidth
    kinder.setCanvasHeight(rows);                          // Input 1 canvasHeight
    kinder.setCanvasChar(String.valueOf(bgChar));          // Input 2 canvasChar

    // Drawing first header
    System.out.println("----DIGITAL KINDER KIT: LET'S PLAY & LEARN----");
    do{ // Loop to interact with the figures
      // Method for the main menu (Menu 0)
      kinder.mainMenuOptions(kinder);  

      // Method for the object predefined menu (Menu 1)
      kinder.menuObjPredOptions(kinder, canvas, drawingTask, bitmap);

      // Method for the freestyle menu (Menu 2)
      kinder.menuFreestyleOptions(kinder, canvas);

      // Method for the edit canvas menu (Menu 3)
      kinder.menuEditCanvas(kinder, triangle, canvas);

      // Method for the Z/M/R menu (Menu 4)
      kinder.menuZMR(canvas);

      // Method for the Interaction menu (Menu 4.1)
      kinder.menuInteractZMR(canvas, triangle);
    }while(iter);
  }

  // Void Method to print main menu and read input (menu 0)
  private void menu(){
    option = CONS_OPT;
    while(option > 3){
      System.out.println("Please select an option. Type 3 to exit.");
      System.out.println("1. Draw a predefined object");
      System.out.println("2. Freestyle Drawing");
      System.out.println("3. Exit");
      option = input.nextInt(); 
      if(option > 3){
          System.out.println("Unsupported option. Please try again!");
      }
    }
  }

  // Method to iterate over menu 0
  private void mainMenuOptions(KinderKit kinder){
    if(mainMenu){ // If we are in menu 0
      // Remove triangles from matrix
      for(int tri =0; tri < triangleMatrix.size(); tri++){triangleMatrix.remove(tri);} 
        kinder.menu();  // print main menu and get input
      // Multiway branching over menu input
      switch(option){
        case(1): // 1. Draw a predefined object (Go to menu 1)
          canvasType = "predefined";
          mainMenu = false;
          predefinedObjMenu = true;
          break;
        case(2): // 2. Freestyle Drawing (Go to menu 2)
          // Instanciate canvas 
          canvStup = kinder.instanciateCanvas(); // Instanciate canvas
          canvasWidth = Integer.parseInt(canvStup[0]);
          canvasHeight = Integer.parseInt(canvStup[1]);
          canvasChar = canvStup[2];
          canvasType = "freestyle";
          mainMenu = false;
          freestyleMenu = true;
          break;
        case(3): // 3. Exit
          System.out.println("Goodbye! We hope you had fun :)");
          System.exit(0);
          break;
        // Print the menu 0 again
        default:
          System.out.println("Unsupported option. Please try again.");
          kinder.menu();    // print main menu and get input
      }
    }
  }

  // Method to return canvas setup (menu 0)
  private String[] instanciateCanvas(){
    String[] canvStup = new String[3];
    System.out.print("Canvas width: ");
    canvStup[0] = String.valueOf(input.nextInt());
    System.out.print("Canvas height: ");
    canvStup[1] = String.valueOf(input.nextInt());
    System.out.print("Background character: ");
    canvStup[2] = input.next();
    // System.out.println("Drawing canvas has been updated!\n");
    return canvStup;
  }

  // Void Method to print predefined object menu and read input (menu 1)
  private void menuPredefined(){
    option = CONS_OPT;
    while(option > 4){
      System.out.println("Please select an option. Type 4 to go back to the main menu.");
      System.out.println("1. Preview the sample drawing");
      System.out.println("2. Start/edit the current canvas");
      System.out.println("3. Check result");
      System.out.println("4. Go back to the main menu");
      option = input.nextInt(); 
      if(option > 4){
          System.out.println("Unsupported option. Please try again.");
      }
    }
  }

  // Method to iterate over menu 1 predefined Object
  private void menuObjPredOptions(KinderKit kinder, DrawingCanvas canvas, DrawingTask drawingTask, char[][] bitmap){
    if(predefinedObjMenu){ // If we are in menu 1
      kinder.menuPredefined();
      // Multiway branching over menu input
      switch(option){
        case(1): // 1. Preview the sample drawing
          System.out.println("This is your task. Just try to draw the same object. Enjoy!");
          canvas.printCanvas(bitmap);
          break;
        case(2): // 2. Start/edit the current canvas
          canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
          predefinedObjMenu = false;
          editCanvasMenu = true;
          break;
        case(3): // 3. Check result
          drawingTask.compareCanvas(canvas, canvasWidth, canvasHeight, canvasChar, triangleMatrix, bitmap);
          break;
        case(4): // 4. Go back to the main menu
          canvasType = "";
          mainMenu = true;
          predefinedObjMenu = false;
          break;
      }
    }
  }

  // Void Method to print freestyle object menu and read input (menu 2)
  private void menuFreestyle(){
    option = CONS_OPT;
    while(option > 3){
      System.out.println("Please select an option. Type 3 to go back to the main menu.");
      System.out.println("1. Start/edit your current canvas");
      System.out.println("2. Share your current drawing");
      System.out.println("3. Go back to the main menu");
      option = input.nextInt(); 
      if(option > 3){
          System.out.println("Unsupported option. Please try again.");
      }
    }
  }

  // Method to iterate over menu 2 fresstyle Object
  private void menuFreestyleOptions(KinderKit kinder, DrawingCanvas canvas){
    if(freestyleMenu){ // If we are in menu 2
      kinder.menuFreestyle();
      // Multiway branching over menu input
      switch(option){
        case(1): // 1. Start/edit your current canvas
          canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
          freestyleMenu = false;
          editCanvasMenu = true;
          break;
        case(2): // 2. Share your current drawing
          canvas.currentDrawing(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
          break;
        case(3): // 3. Go back to the main menu
          // Reset canvas settings 
          canvasWidth = 0;
          canvasHeight = 0;
          canvasChar = "";
          canvasType = "";
          // Set main menu loop
          freestyleMenu = false;
          mainMenu = true;
          break;
        // Print the menu 0 again
        default:
          break;
      }
    }
  }

  // Void Method to print current canvas menu and read input (menu 3)
  private void menuEditCurrCanvas(){
    option = CONS_OPT;
    while(option > 4){
      System.out.println("Please select an option. Type 4 to go back to the previous menu.");
      System.out.println("1. Add a new Triangle");
      System.out.println("2. Edit a triangle");
      System.out.println("3. Remove a triangle");
      System.out.println("4. Go back");
      option = input.nextInt(); 
      if(option > 4){
          System.out.println("Unsupported option. Please try again.");
      }
    }
  }

  // Method to iterate over menu 3 edit canvas 
  private void menuEditCanvas(KinderKit kinder, Triangle triangle, DrawingCanvas canvas){
    int triOption = 0;
    boolean clean = false;
    if(editCanvasMenu){ // If we are in menu 3
      kinder.menuEditCurrCanvas();
      // Multiway branching over menu input
      switch(option){
        case(1): // 1. Add a new Triangle
          kinder.addNewTriangle(kinder, canvas, triangle);
          // Go to the next menu
          editCanvasMenu= false;
          ZMRMenu = true;
          break;
        case(2): // 2. Edit a triangle
          if(triangleMatrix.size()<1){ //Evaluate if we have any triangles
            System.out.println("The current canvas is clean; there are no shapes to edit!");
            canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
            break;
          }
          else{
            int count = 0;
            for (ArrayList<String> tri : triangleMatrix) { // Iterate over the Triangles
              count +=1;
              System.out.printf("Shape #%d - Triangle: xPos = %s, yPos = %s, tChar = %s\n", count, tri.get(2), tri.get(3), tri.get(1));
            }
            System.out.println("Index of the shape:");
            triOption = input.nextInt();
          }
          if(triOption > triangleMatrix.size()){
            System.out.println("The shape index cannot be larger than the number of shapes!");
            canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
            break;
            }
          else{ // If correct selection
            kinder.editTriangle(kinder, triangle, triOption);       // Select triangle if option is 2
            triangleMatrix.remove(triOption - 1);                   // Remove triangle from old position to update printing priority
            canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
            // Go to the next menu
            editCanvasMenu= false;
            ZMRMenu = true;
          }
          break;
        case(3): // 3. Remove a triangle
          if(triangleMatrix.size()<1){ //Evaluate if we have any triangles
            System.out.println("The current canvas is clean; there are no shapes to remove!");
            canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
            break;
          }
          else{
            int count = 0;
            for (ArrayList<String> tri : triangleMatrix) { // Iterate over the Triangles
              count +=1;
              System.out.printf("Shape #%d - Triangle: xPos = %s, yPos = %s, tChar = %s\n", count, tri.get(2), tri.get(3), tri.get(1));
            }
            System.out.println("Index of the shape to remove:");
            triOption = input.nextInt();
            }
            if(triOption > triangleMatrix.size()){
              System.out.println("The shape index cannot be larger than the number of shapes!");
              canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
              }
            else{ // If correct selection
              triangleMatrix.remove(triOption - 1);  // Remove triangle from old position to update printing priority
              canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
            }
          break;
        case(4): // 4. Go back
          editCanvasMenu = false;
          if (canvasType == "freestyle"){
            freestyleMenu = true;
          }
          else if (canvasType == "predefined"){
            predefinedObjMenu = true;
          }
          else{
            System.out.printf("Houston we have a problem canvasType is: %s\n", canvasType);
            System.exit(0);
          }
          break;
        // Print the menu 0 again
        default:
          break;
      }
    }
  }

  // Method to add new triangle (Menu 3)
  private void addNewTriangle(KinderKit kinder, DrawingCanvas canvas, Triangle triangle){
    triangle.setFigure(canvasWidth, canvasHeight, input);
    // Instancialte triangle parameters in a matrix to iterate
    kinder.newTriangle(
      String.valueOf(triangle.getFigShape()), // figureShape
      triangle.getFigChar(),                  // figureChar
      String.valueOf(triangle.getPosX()),     // PosX
      String.valueOf(triangle.getPosY()),     // PosY
      String.valueOf(triangle.getRotState()));// rotState
    // Print canvas
    canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix); 
  }

  // Method to set triangle to edit (Menu 3)
  private void editTriangle(KinderKit kinder, Triangle triangle, int triOption){
    // Instancialte triangle parameters in a matrix to iterate
    kinder.newTriangle(
      triangleMatrix.get(triOption-1).get(0),  // figureShape
      triangleMatrix.get(triOption-1).get(1),  // figureChar
      triangleMatrix.get(triOption-1).get(2),  // PosX
      triangleMatrix.get(triOption-1).get(3),  // PosY
      triangleMatrix.get(triOption-1).get(4));  // rotState

    // Instanciate variables of current triangle for redability
    triangle.setFigShape(Integer.parseInt(triangleMatrix.get(triangleMatrix.size()-1).get(0)));
    triangle.setFigChar(triangleMatrix.get(triangleMatrix.size()-1).get(1));
    triangle.setPosX(Integer.parseInt(triangleMatrix.get(triangleMatrix.size()-1).get(2)));
    triangle.setPosX(Integer.parseInt(triangleMatrix.get(triangleMatrix.size()-1).get(3)));
    triangle.setRotState(Integer.parseInt(triangleMatrix.get(triangleMatrix.size()-1).get(4)));
  }
  
  // Void Method to print Z/M/R menu and read input (menu 4)
  private void menuZMR(DrawingCanvas canvas){
    if(ZMRMenu){ // If we are in menu 4
      System.out.println("Type Z/M/R for zooming/moving/rotating. Use other keys to quit the Zooming/Moving/Rotating mode.");
      ZMRChar = input.next().toLowerCase();
      switch(ZMRChar){
        // If the input contains z, m or r go to next menu 4.1
        case("z"):
        case("m"):
        case("r"):
          ZMRMenu = false;
          ZMRInteraction = true;
          break;
        default: // Back to the menu 3
          editCanvasMenu= true;
          ZMRMenu = false;
      }
      canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
    }
  }

  // Void method to print Zoom/move/rotate interactions menu options (Menu 4.1)
  private void menuInteractZMR(DrawingCanvas canvas, Triangle triangle){
    while(ZMRInteraction){
      switch(ZMRChar){
        // Zoom interactions menu options (Menu 4.1.1)
        case("z"):
          zoomInteract(canvas, triangle); // Kind of zooming answer
        break;
        // Move interactions menu options (Menu 4.1.2)
        case("m"):
          moveInteract(canvas, triangle); // Kind of movement answer
        break;
        // Rotate interactions menu options (Menu 4.1.3)
        case("r"):
          rotateInteract(canvas, triangle); // Kind of rotation answer
        break;
        }
    }
  }

  private void zoomInteract(DrawingCanvas canvas, Triangle triangle){
    // Kind of zooming answer
    System.out.println(
        "Type I/O to zoom in/out. Use other keys to go back to the Zooming/Moving/Rotating menu.");
    kindZMRInterChar = input.next().toLowerCase();
    switch(kindZMRInterChar){
      case("i"):
      case("o"):
        // Conditions according user input
        triangle.zoomResult(kindZMRInterChar, canvasWidth, canvasHeight);
        triangleMatrix.get(triangleMatrix.size()-1).set(0, String.valueOf(triangle.getFigShape()));
      break;
      default:
        ZMRMenu = true;
        ZMRInteraction = false;
    }
    canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
  }

  private void moveInteract(DrawingCanvas canvas, Triangle triangle){
    // Kind of movement answer
    System.out.println(
        "Type A/S/W/Z to move left/right/up/down."
            +" Use other keys to go back to the Zooming/Moving/Rotating menu.");
    kindZMRInterChar = input.next().toLowerCase();
    switch(kindZMRInterChar){
        case("a"):  // Left movement
        case("s"): // Right movement
        case("w"):  // Up movement
        case("z"):  // Down movement
          triangle.moveResult(kindZMRInterChar, canvasWidth, canvasHeight);
          triangleMatrix.get(triangleMatrix.size()-1).set(2, String.valueOf(triangle.getPosX()));
          triangleMatrix.get(triangleMatrix.size()-1).set(3, String.valueOf(triangle.getPosY()));
        break;
        default:
          ZMRMenu = true;
          ZMRInteraction = false;
    }
    canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
  }

  private void rotateInteract(DrawingCanvas canvas, Triangle triangle){
    // Kind of rotation answer
    System.out.println(
        "Type R/L to rotate clockwise/anti-clockwise."
        + " Use other keys to go back to the Zooming/Moving/Rotating menu.");
    kindZMRInterChar = input.next().toLowerCase();
    switch(kindZMRInterChar){
      case("r"):
      case("l"):
        triangle.rotateResult(kindZMRInterChar, canvasWidth, canvasHeight);
        triangleMatrix.get(triangleMatrix.size()-1).set(4, String.valueOf(triangle.getRotState()));
      break;
    default: 
      ZMRMenu = true;
      ZMRInteraction = false;
    }
    canvas.printTriangle(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
  }
}
