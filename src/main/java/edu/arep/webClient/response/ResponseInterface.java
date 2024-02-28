package edu.arep.webClient.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public interface ResponseInterface {

    void sendResponse() throws IOException;

    /**
     * Method that reads a text file and returns it in a string
     * @param path Path to the file
     * @return String with the contents of the file
     */
    static String readFile(String path){
        StringBuilder textFile = new StringBuilder();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                textFile.append(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return textFile.toString();
    }
}
