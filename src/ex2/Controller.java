package ex2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private static final int FORMAT = 0,
                             POOL = 1,
                             FILENAME = 2;
    private String[] outputFormat;
    private int poolSize;
    private final String fileName;
    private int numberOfLines;
    private final HashMap<String, ArrayList<String>> urlsIO;
    private final ExecutorService pool;

    public Controller(String[] args) throws IOException {
        // take the data from argument vector
        this.outputFormat = args[FORMAT].split("");
        this.poolSize = Integer.parseInt(args[POOL]);
        this.fileName = args[FILENAME];
        this.numberOfLines = 0;

        // creating the thread pool
        this.pool = Executors.newFixedThreadPool(this.poolSize);

        // read the urls from the file and save the addresses in hash map by index
        this.urlsIO = new LinkedHashMap<>();
        readFile();

//        for (Map.Entry<String, ArrayList<String>> entry : this.urlsIO.entrySet()) {
//            System.out.println(entry+" |KEY:"+entry.getKey()+" |value:"+entry.getValue());
//        }
    }

    private void readFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader((this.fileName)))) {
            String lineCont;
            while ((lineCont = reader.readLine())!=null)
                if (!this.urlsIO.containsKey(lineCont))
                    this.urlsIO.put(lineCont, new ArrayList<>());
        } catch (IOException ioe) {
            System.err.println("Reading from file " + fileName + " failed.");
            throw new IOException(ioe);
        }

        // check if the urls file is empty
        if (this.urlsIO.isEmpty())
            throw new IOException("File "+fileName+" is empty.");
    }

    // todo: this function need to call the threads
    public void crawl() throws IOException, InterruptedException {
        System.out.println("crawl");
        ArrayList<Downloader> down = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : this.urlsIO.entrySet()) {
            down.add(new Downloader(entry.getKey(), entry.getValue()));
        }
        for (Downloader d: down){
            this.pool.execute(d);
            d.start();
        }
        for (Downloader d: down){
            d.join();
        }
        pool.shutdown();
    }
}
