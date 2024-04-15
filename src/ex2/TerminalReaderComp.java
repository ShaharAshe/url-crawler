package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TerminalReaderComp implements Read {

    public TerminalReaderComp() {
    }

    @Override

    public ArrayList<String> readContent() throws IOException {
        ArrayList<String> fileCont;
        try (Scanner reader = new Scanner(System.in)) {
            fileCont = new ArrayList<>();
            String lineCont;
            final String endOut = "exit",
                    outMsg = "write urls (or type '"+endOut+"' for stop)";
            System.out.println(outMsg);
            while (!(lineCont = reader.nextLine()).equals(endOut)) {
                fileCont.add(lineCont);
                System.out.println(outMsg);
            }
        }catch (Exception e){
            System.err.println("Reading from terminal is failed.");
            throw new IOException(e);
        }
        if (fileCont.isEmpty())
            throw new IOException("No input urls were entered.");
        return fileCont;
    }
}
