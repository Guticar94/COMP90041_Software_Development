/**
 * COMP90041, Sem1, 2023: Assignment 2
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */
import java.util.ArrayList;
import java.util.Scanner;
public class DrawingTask {

  // Method to compare the predefined canvas with the user generated canvas
  public void compareCanvas(
    DrawingCanvas drawCanvas,   // DrawingCanvas object
    int canvasWidth,            // Canvas width
    int canvasHeight,           // Canvas height
    String canvasChar,          // Canvas Char
    ArrayList<ArrayList<String>> triangleMatrix, // triangleMatrix: Each row has ---> [Tri Number,  Tri posX, Tri posY, Tri shape, Tri char, Tri rotation position]
    char[][] bitmap
    ){
    // Method variables
    String[][] canvas = drawCanvas.getShapes(canvasWidth, canvasHeight, canvasChar, triangleMatrix);
    boolean equal = true;

    // If the matrizes dimensions are the same
    if((bitmap.length == canvasHeight)&&(bitmap[0].length == canvasWidth)){
      outerloop: // Iterate over each position and compare
      for(int i = 0; i < canvasHeight; i++){ // Iterate over the rows
        for(int j = 0; j < canvasWidth; j++){ // Iterate over the columns
          if(canvas[i][j].charAt(0) != bitmap[i][j]){
            equal = false;
            break outerloop;
          }
        } 
      }
    }
    else{equal = false;}
    // Print comparisson result 
    if(equal){ // If the matrixes are identicall
        System.out.println("You successfully complete the drawing task. Congratulations!!");
    }
    else{ // If they are different
        System.out.println("Not quite! Please edit your canvas and check the result again.");
    }
    System.out.println("This is the sample drawing:");
    for(int i = 0; i < bitmap.length; i++){ // Iterate over the rows
        for(int j = 0; j < bitmap[0].length; j++){ // Iterate over the columns
            System.out.print(bitmap[i][j]);
        }
        System.out.println();
    }
    System.out.println("And this is your drawing:");
    for(int i = 0; i < canvasHeight; i++){ // Iterate over the rows
      for(int j = 0; j < canvasWidth; j++){ // Iterate over the columns
        System.out.print(canvas[i][j]);
      }
      System.out.println();
    } 
  }
}
