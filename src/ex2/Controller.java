package ex2;

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
    private final HashMap<String, ArrayList<String>> urlsIO;
    private final ExecutorService pool;
    private final ArrayList<OutFormat> format;
    private final String contentType;
    private final Read reader;

    public Controller(String[] args, String contType, Read reader) throws IOException {
        // take the data from argument vector
        this.outputFormat = args[FORMAT].split("");
        this.poolSize = Integer.parseInt(args[POOL]);
        this.format = new ArrayList<>();
        this.contentType = contType;
        this.reader = reader;

        // creating the thread pool
        this.pool = Executors.newFixedThreadPool(this.poolSize);

        // read the urls from the file and save the addresses in hash map by index
        this.urlsIO = new LinkedHashMap<>();

        addFormatOut();
        readFile();
    }

    private void addFormatOut() {
        for(String f : this.outputFormat)
            this.format.add(new FormatFactory().createFormat(f));
    }

    private void readFile() throws IOException {
        try{
           for (String lineCont : this.reader.operation())
               if (!this.urlsIO.containsKey(lineCont))
                   this.urlsIO.put(lineCont, new ArrayList<>());
        } catch (IOException ioe) {
            throw new IOException(ioe);
        }
    }

    // todo: this function need to call the threads
    public void crawl() throws IOException, InterruptedException {
        ArrayList<Downloader> down = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : this.urlsIO.entrySet())
            down.add(new Downloader(entry.getKey(), entry.getValue(), this.format, this.contentType));

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
