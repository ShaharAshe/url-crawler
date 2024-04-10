package ex2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Controler {
    private static final int FORMAT = 0,
                             POOL = 1,
                             FILENAME = 2;
    private String[] outputFormat;
    private int poolSize;
    private String fileName;
    private int numberOfLines;
    private final HashMap<Integer, ArrayList<String>> urlsIO;
    public Controler(String[] args) throws IOException {
        this.outputFormat = args[FORMAT].split("");
        this.poolSize = Integer.parseInt(args[POOL]);
        this.fileName = args[FILENAME];

        /////////////////////////////
        /////////// debug ///////////
        System.out.println("out:");
        for (String s:this.outputFormat){
            System.out.printf("%s ", s);
        }
        System.out.println(); // blank
        System.out.println(poolSize);
        System.out.println(fileName);
        /////////////////////////////
        this.numberOfLines = 0;
        this.urlsIO = new HashMap<>();

        /////////////////////////////

        readFile();

        /////////// debug ///////////
        System.out.println("out:");
        for (int i = 1; i <= this.numberOfLines; ++i) {
            System.out.println(this.urlsIO.get(i));
        }
        /////////////////////////////
    }

    private void readFile() throws IOException {
        String lineCont;
        try (BufferedReader reader = new BufferedReader(new FileReader((this.fileName)))) {
            while ((lineCont = reader.readLine())!=null){
                ++this.numberOfLines;
                this.urlsIO.put(this.numberOfLines, new ArrayList<>());
                this.urlsIO.get(this.numberOfLines).add(lineCont);
            }
        } catch (IOException ioe) {
            System.err.println("Reading from file " + fileName + " failed.");
            throw new IOException(ioe);
        }
        if (this.numberOfLines == 0)
            throw new IOException("File "+fileName+" is empty.");
    }

    public void crawl(){
        System.out.println("crawl");
    }
}
