package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * FileReaderComp is a class that reads content from a specified file.
 */
public class FileReaderComp implements Read {
    private final String fileName; // Name of the file to read from

    /**
     * Constructor for FileReaderComp.
     *
     * @param fileName The name of the file to read from
     */
    public FileReaderComp(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads the content from the specified file.
     *
     * @return A list of strings representing the file content
     * @throws IOException If an error occurs while reading the file
     */
    @Override
    public ArrayList<String> readContent() throws IOException {
        ArrayList<String> fileCont;
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader((this.fileName)))) {
            fileCont = new ArrayList<>();
            String lineCont;

            // Read each line from the file and add to the list
            while ((lineCont = reader.readLine()) != null)
                fileCont.add(lineCont);
        } catch (IOException ioe) {
            System.err.println("Reading from file " + fileName + " failed.");
            throw new IOException(ioe);
        }
        if (fileCont.isEmpty())
            throw new IOException("File " + fileName + " is empty.");
        return fileCont;
    }
}
