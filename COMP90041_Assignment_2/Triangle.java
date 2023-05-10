/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

import java.util.Scanner;
public class Triangle {
  // Instanciate class variables
  private int figureShape;         // Figure shape 
  private String figureChar;       // Figure character
  private int posX;                // Figure position X
  private int posY;                // Figure position Y
  private int rotState;            // Figure rotation state

  // Setters
  public void setFigShape(int figureShape){
    this.figureShape = figureShape;
  }
  public void setFigChar(String figureChar){
    this.figureChar = figureChar;
  }
  public void setPosX(int posX){
    this.posX = posX;
  }
  public void setPosY(int posY){
    this.posY = posY;
  }
  public void setRotState(int rotState){
    this.rotState = rotState;
  }
  // Getters
  public int getFigShape(){
    return figureShape;
  }
  public String getFigChar(){
    return figureChar;
  }
  public int getPosX(){
    return posX;
  }
  public int getPosY(){
    return posY;
  }
  public int getRotState(){
    return rotState;
  }

  // Method to set figure and inicialize object variables
  public void setFigure(
  int width,      // Canvas width
  int height,     // Canvas height
  Scanner input   // Scanner variable to read inputs
  ){         
    // Loop to asure the figure size dimensions
    Boolean wrongFigure = true; // Bolean to access loop of figure size dimension
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
    figureChar = input.next(); // Asign figure char

    // Inicialize empty variables 
    posX = 0;
    posY = 0;
    rotState = 0;
  }

  // Zoom in/out according input
  public void zoomResult(
  String kindZChar,
  int canvasWidth,
  int canvasHeight
  ){
    switch(kindZChar){
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
    }
  }

  // Move a/s/w/z according input
  public void moveResult(
  String kindMChar,
  int canvasWidth,
  int canvasHeight
  ){
    switch(kindMChar){
      case("a"):  // Left movement
        if (posX == 0){
          System.out.println("You cannot move this triangle outside of the drawing canvas!");
        }
      else{
        posX -= 1;
      }
      break;
      case("s"): // Right movement
        if (posX + figureShape >= canvasWidth){
          System.out.println("You cannot move this triangle outside of the drawing canvas!");
        }
      else{
        posX += 1;
      }
      break;
      case("w"):  // Up movement
        if (posY == 0){
          System.out.println("You cannot move this triangle outside of the drawing canvas!");
        }
      else{
        posY -= 1;
      }
      break;
      case("z"):  // Down movement
        if (posY + figureShape >= canvasHeight){
          System.out.println("You cannot move this triangle outside of the drawing canvas!");
        }
      else{
        posY += 1;
      }
      break;
    }
  }

  // Rotation right/left according input
  public void rotateResult(
  String kindRChar,
  int canvasWidth,
  int canvasHeight
  ){
    switch(kindRChar){
      case("r"): // Rotate clockwise
        if (rotState < 3){
          rotState += 1;
        }
      else{
        rotState = 0;
      }
      break;
      case("l"): // Rotate counter-clockwise
        if (rotState > 0){
          rotState -= 1;
        }
      else{
        rotState = 3;
      }
      break;
    }
  }

}




