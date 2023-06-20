import java.io.PrintWriter;
import java.io.FileOutputStream;    
import java.io.FileNotFoundException;

/**
 * COMP90041, Sem1, 2023: Final Project
 * @author: Andres Gutierrez
 * University email: agutierrezca@student.unimelb.edu.au
 * Student Id: 1405461
 */

public class FileWriting{
    private PrintWriter outputStream = null;

    /**
     * Default class to open and write files
     * @param outputStream     declaration of writer variable
     */

    /**
     * Default method to open files in a write mode
     */
    public void setWriteFile(String filePath){
        if((filePath != "") && (filePath.matches("^.*.(txt|csv|log)$"))){
            try {
            // First, try to append to the file
            outputStream = new PrintWriter(new FileOutputStream (filePath, true));
            } catch (FileNotFoundException e) {
                // If the file wasn't found, create it
                outputStream = newFileOrExit(filePath);
            }
        }
        else{
                outputStream = newFileOrExit("rescuebot.log");
        }
        
    }

    /**
     * Default method to open files in a write mode
     * @return      outStream  PrintWriter object with file ready to write in
     * @throws      WARNING message stating file couldnt be opened
     */
    private static PrintWriter newFileOrExit(String filePath) {
        PrintWriter outStream = null;
        try {
            outStream = new PrintWriter (new FileOutputStream (filePath));
        } catch (Exception e) {
            System.err.println("WARNING: Error opening " + filePath + " for writing.");
            System.exit(1);
        }
        return outStream;
    }

    /**
     * Method to write file
     */
    public void writeLines(String inputToWrite){
        outputStream.println(inputToWrite);
        outputStream.flush();
    }
    
}
