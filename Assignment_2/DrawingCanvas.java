/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */
import java.util.ArrayList;
import java.util.Scanner;
public class DrawingCanvas {
  private String[][] canvas; // Canvas matrix to save the figure

  // Initial drawing canvas settings (Menu 0)
  public void canvasSettings(
    int canvasWidth,
    int canvasHeight,
    String canvasChar
    ){
    System.out.println("Current drawing canvas settings:");
    System.out.printf("- Width: %d\n", canvasWidth);
    System.out.printf("- Height: %d\n", canvasHeight);
    System.out.printf("- Background character: %s\n\n", canvasChar);
  }
  
  // Method to get the canvas with the triangles in an array matrix
  public String[][] getShapes(
    int canvasWidth,            // Canvas width
    int canvasHeight,           // Canvas height
    String canvasChar,          // Canvas Char
    ArrayList<ArrayList<String>> triangleMatrix // triangleMatrix: Each row has ---> [Tri Number,  Tri posX, Tri posY, Tri shape, Tri char, Tri rotation position]
    ){
    // Local variables
    int figureShape;
    String figureChar;
    int posX;
    int posY;
    int rotState;
    int figureShapeCopy;   // Local variable to print the triangle
    int posXCopy;          // Local variable to print the triangle

    // Declare and save empty canvas in an array matrix
    canvas = new String[canvasHeight][canvasWidth];
    for(int i = 0; i < canvasHeight; i++){ // Iterate over the rows
      for(int j = 0; j < canvasWidth; j++){ // Iterate over the columns
        canvas[i][j] = canvasChar;
      }
    }
    // Iterate over triangles and write them over the canvas array matrix
    for (ArrayList<String> triangle : triangleMatrix) { // Iterate over each of the triangles

      // Get current triangle variables
      figureShape = Integer.parseInt(triangle.get(0));
      figureChar = triangle.get(1);
      posX = Integer.parseInt(triangle.get(2));
      posY = Integer.parseInt(triangle.get(3));
      rotState = Integer.parseInt(triangle.get(4));
      figureShapeCopy = figureShape;   // Local variable to print the triangle
      posXCopy = posX;                 // Local variable to print the triangle

      for(int i = 0; i < canvasHeight; i++){ // Iterate over the rows
        for(int j = 0; j < canvasWidth; j++){ // Iterate over the columns
          if(((i >= posY) & (j >= posX))&&((i < posY + figureShape) & (j < posX + figureShape))){ // If we are in the area of the triangle
            switch(rotState){   // Print the triangle depending rotation state
              case(0): // State 0. Normal triangle
                if(j < posX + figureShapeCopy){canvas[i][j] = figureChar;}
              break;
              case(1): // State 1. 45 degrees
                if(j > posXCopy - 1){canvas[i][j] = figureChar;}
              break;
              case(2): // State 2. 90 degrees
                if(j > posX + figureShapeCopy - 2){canvas[i][j] = figureChar;}
              break;
              case(3): // State 3. 135 degrees
                if(j < posXCopy + 1){canvas[i][j] = figureChar;}
              break;
            }
          }
        }
        if(i >= posY){
          figureShapeCopy -= 1;
          posXCopy += 1;
        }
      }
    }
  return canvas; // Return canvas matrix
  }

  // Method to print the canvas
  public void printTriangle(
      int canvasWidth,            // Canvas width
      int canvasHeight,           // Canvas height
      String canvasChar,          // Canvas Char
      ArrayList<ArrayList<String>> triangleMatrix // triangleMatrix: Each row has ---> [Tri Number,  Tri posX, Tri posY, Tri shape, Tri char, Tri rotation position]
      ){
      // Get actual canvas
      canvas = getShapes(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
      // Print the final canvas
      for(int i = 0; i < canvasHeight; i++){ // Iterate over the rows
          for(int j = 0; j < canvasWidth; j++){ // Iterate over the columns
              System.out.print(canvas[i][j]);}
          System.out.println();}
  }

  // Method to print current drawing 
  public void currentDrawing(
      int canvasWidth,            // Canvas width
      int canvasHeight,           // Canvas height
      String canvasChar,          // Canvas Char
      ArrayList<ArrayList<String>> triangleMatrix // triangleMatrix: Each row has ---> [Tri Number,  Tri posX, Tri posY, Tri shape, Tri char, Tri rotation position]
  ){
      // Method variables
      canvas = getShapes(canvasWidth, canvasHeight, canvasChar, triangleMatrix);

      System.out.println("This is the detail of your current drawing");
      System.out.printf("%d,%d,%s\n", canvasHeight, canvasWidth, canvasChar);
      for(int i = 0; i < canvasHeight; i++){ // Iterate over the rows
          for(int j = 0; j < canvasWidth; j++){ // Iterate over the columns
              System.out.print(canvas[i][j]);
              if(j<canvasWidth-1){
                  System.out.print(",");
                  }
              }
          System.out.println();
          }

  }

  // Method to print predefined canvas inputed by file
  public static void printCanvas(char[][] bitmap){
      for(int i = 0; i<bitmap.length; i++){
          System.out.println(bitmap[i]);
      }
  }
    
}


