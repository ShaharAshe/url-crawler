package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReaderComp implements Read {
    private final String fileName;

    public FileReaderComp(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public ArrayList<String> readContent() throws IOException {
        ArrayList<String> fileCont;
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader((this.fileName)))) {
            fileCont = new ArrayList<>();
            String lineCont;

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
