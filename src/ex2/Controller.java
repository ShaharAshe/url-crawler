package ex2;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller {
    private static final int FORMAT = 0,
                             POOL = 1;
    private final String[] outputFormat; // save the desire format that given
    private final int poolSize;
    private final HashMap<String, ArrayList<String>> urlsIO; // save the url and the output. the keys are the urls and the values are the output.
    private final ExecutorService pool; // thread pool
    private final ArrayList<OutFormat> format; // save the format class shows.
    private final Read reader; // the class that read from file/ terminal/ etc ...
    private final FormatFactory formatFactory; // the factory for the format class shows

    public Controller(String[] args, Read reader) throws IOException {
        // take the data from argument vector
        this.outputFormat = args[FORMAT].split("");
        this.poolSize = Integer.parseInt(args[POOL]);
        this.format = new ArrayList<>();
        this.reader = reader;

        // creating the thread pool
        this.pool = Executors.newFixedThreadPool(this.poolSize);

        // read the urls from the file and save the addresses in hash map by index
        this.urlsIO = new LinkedHashMap<>();

        this.formatFactory = new FormatFactory();

        registerFactory();
        addFormatOut();
        readFile();
    }

    private void registerFactory(){
        this.formatFactory.register(FormatType.s, SizeFormat::new);
        this.formatFactory.register(FormatType.u, UrlFormat::new);
        this.formatFactory.register(FormatType.t, TimeFormat::new);
        this.formatFactory.register(FormatType.m, ImagTypeFormat::new);
    }

    private void addFormatOut() {
        for(String f : this.outputFormat)
            this.format.add(this.formatFactory.createFormat(f));
    }

    private void readFile() throws IOException {
        try{
           for (String lineCont : this.reader.readContent())
               if (!this.urlsIO.containsKey(lineCont))
                   this.urlsIO.put(lineCont, new ArrayList<>());
        } catch (IOException ioe) {
            throw new IOException(ioe);
        }
    }

    public void crawl() throws InterruptedException {
        ArrayList<Downloader> down = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : this.urlsIO.entrySet())
            down.add(new ImageDownloader(entry.getKey(), entry.getValue(), this.format));

        for (Downloader d: down)
            this.pool.execute(d);

        pool.shutdown();
        boolean re =  this.pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public void print(){
        for (ArrayList<String> value : this.urlsIO.values()) {
            try {
                if (!value.isEmpty() && value.get(0).isEmpty()) {
                    throw new RuntimeException(value.get(1));
                } else {
                    for (String vf : value)
                        System.out.print(vf + " ");
                    if (!value.isEmpty())
                        System.out.println();
                }
            }
            catch (RuntimeException e){
                System.err.println(e.getMessage());
                System.err.flush();
            }
        }
    }
}
