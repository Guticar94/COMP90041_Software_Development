public class DrawingCanvas {

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

    // Method to Print triangle
    public static void printTriangle(
        int width,                  // Canvas width
        int height,                 // Canvas height
        String backChar,            // Canvas backChar
        int posX,                   // Canvas posX
        int posY,                   // Canvas posY
        int figureShape,            // Triangle shape
        String figureChar           // Triangle char
        ){
        int figureShapeCopy = figureShape; // Local variable to print the triangle
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if((i<posY) | (j<posX)){System.out.print(backChar);}
                else{
                    if(j < posX+figureShapeCopy){System.out.print(figureChar);}
                    else{System.out.print(backChar);}
                }
            }
            System.out.println();
            if(i >= posY){figureShapeCopy -= 1;}
        }
    }

    // Method to Print rectangle
    public static void printRectangle(
        int canvasWidth,            // Canvas width
        int canvasHeight,           // Canvas height
        String backChar,            // Canvas backChar
        int posX,                   // Canvas posX
        int posY,                   // Canvas posY
        int figureWidth,            // Rectangle width
        int figureHeight,           // Rectangle height
        String figureChar           // Rectangle char
        ){ 
        for(int i = 0; i < canvasHeight; i++){
            for(int j = 0; j < canvasWidth; j++){
                if((i<posY) | (j<posX)){System.out.print(backChar);}
                else{
                    if((j < posX+figureWidth) & (i < posY+figureHeight)){System.out.print(figureChar);}
                    else{System.out.print(backChar);}
                }
            }
            System.out.println();
        }
    }

    // Print canvas
    public void printCanvas(
        int canvasWidth,            // Canvas width
        int canvasHeight,           // Canvas height
        String canvasChar,          // Canvas backChar
        int posX,                   // Canvas posX
        int posY,                   // Canvas posY
        int figureShape,            // Triangle shape
        int figureWidth,            // Rectangle width
        int figureHeight,           // Rectangle height
        String figureChar,          // Triangle char
        String selectedFigure       // Figure to print
        ){ 
        switch(selectedFigure){
            case("Triangle"):
                DrawingCanvas.printTriangle(canvasWidth, canvasHeight, canvasChar, posX, posY, figureShape, figureChar);
            break;
            case("Rectangle"):
                DrawingCanvas.printRectangle(canvasWidth, canvasHeight, canvasChar, posX, posY, figureWidth, figureHeight, figureChar);
            break;
        }
    } 
}

