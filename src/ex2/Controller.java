package ex2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller {
    private static final int FORMAT = 0,
                             POOL = 1,
                             FILENAME = 2;
    private final String[] outputFormat;
    private final int poolSize;
    private final String fileName;
    private final HashMap<String, ArrayList<String>> urlsIO;
    private final ExecutorService pool;
    private final ArrayList<OutFormat> format;

    public Controller(String[] args) throws IOException {
        // take the data from argument vector
        this.outputFormat = args[FORMAT].split("");
        this.poolSize = Integer.parseInt(args[POOL]);
        if(args.length != FILENAME+1)
            this.fileName = "";
        else
            this.fileName = args[FILENAME];
        this.format = new ArrayList<>();

        addFormatOut();

        // creating the thread pool
        this.pool = Executors.newFixedThreadPool(this.poolSize);

        // read the urls from the file and save the addresses in hash map by index
        this.urlsIO = new LinkedHashMap<>();
        readFile();
    }

    private void addFormatOut() {
        for(String f : this.outputFormat)
            this.format.add(new FormatFactory().createFormat(f));
    }

    private void readFile() throws IOException {
        if (!this.fileName.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader((this.fileName)))) {
                String lineCont;
                while ((lineCont = reader.readLine()) != null)
                    if (!this.urlsIO.containsKey(lineCont))
                        this.urlsIO.put(lineCont, new ArrayList<>());
            } catch (IOException ioe) {
                System.err.println("Reading from file " + fileName + " failed.");
                throw new IOException(ioe);
            }
        }
        else{
            try (Scanner reader = new Scanner(System.in)) {
                String lineCont;
                System.out.println("write urls (or exit for stop)");
                while (!(lineCont = reader.nextLine()).equals("exit")) {
                    if (!this.urlsIO.containsKey(lineCont))
                        this.urlsIO.put(lineCont, new ArrayList<>());
                    System.out.println("write urls (or exit for stop)");
                }
            }catch (Exception e){
                System.err.println("Reading from terminal is failed.");
                throw new IOException(e);
            }
        }

        // check if the urls file is empty
        if (this.urlsIO.isEmpty())
            throw new IOException("File " + fileName + " is empty.");
    }

    // todo: this function need to call the threads
    public void crawl() throws IOException, InterruptedException {
        ArrayList<Downloader> down = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : this.urlsIO.entrySet())
            down.add(new Downloader(entry.getKey(), entry.getValue(), this.format, "image/"));

        for (Downloader d: down)
            this.pool.execute(d);

        pool.shutdown();
        boolean re =  this.pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public void print() {
        for (ArrayList<String> value : this.urlsIO.values()) {
            for (String vf: value)
                System.out.print(vf+" ");

            if (!value.isEmpty())
                System.out.println();
        }
    }
}
