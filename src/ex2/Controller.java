package ex2;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Controller class manages the process of reading URLs from a file,
 * initiating download tasks with a thread pool, and handling output formats.
 */
public class Controller {
    private static final int FORMAT = 0,
                             POOL = 1;
    private final String[] outputFormat; // Desired output formats specified in arguments
    private final int poolSize; // Number of threads in the pool
    private final HashMap<String, ArrayList<String>> urlsIO; // Maps URLs to their outputs
    private final ExecutorService pool; // Thread pool for executing tasks
    private final ArrayList<OutFormat> format; // Stores format classes for processing output
    private final Read reader; // Reads data from file/terminal/etc.
    private final FormatFactory formatFactory; // Factory for creating format objects

    /**
     * Constructor for the Controller class.
     *
     * @param args   The arguments containing output format and pool size
     * @param reader An object that reads content from a source
     * @throws IOException If an error occurs while reading content
     */
    public Controller(String[] args, Read reader) throws IOException {
        // Parse output format and pool size from arguments
        this.outputFormat = args[FORMAT].split("");
        this.poolSize = Integer.parseInt(args[POOL]);
        this.format = new ArrayList<>();
        this.reader = reader;

        // Initialize the thread pool with specified size
        this.pool = Executors.newFixedThreadPool(this.poolSize);

        // Initialize the map for URLs and their outputs
        this.urlsIO = new LinkedHashMap<>();

        // Create a new FormatFactory for creating output formats
        this.formatFactory = new FormatFactory();

        // Register the different format types
        registerFactory();
        addFormatOut();

        // Read the content from the provided source
        readFile();
    }

    /**
     * Registers various output formats in the factory.
     */
    private void registerFactory(){
        this.formatFactory.register(FormatType.s, SizeFormat::new);
        this.formatFactory.register(FormatType.u, UrlFormat::new);
        this.formatFactory.register(FormatType.t, TimeFormat::new);
        this.formatFactory.register(FormatType.m, ImagTypeFormat::new);
    }

    /**
     * Adds output formats based on the provided output format array.
     */
    private void addFormatOut() {
        for(String f : this.outputFormat)
            this.format.add(this.formatFactory.createFormat(f));
    }

    /**
     * Reads the content from the specified source and populates the `urlsIO` map.
     *
     * @throws IOException If an error occurs while reading content
     */
    private void readFile() throws IOException {
        try{
           for (String lineCont : this.reader.readContent())
               if (!this.urlsIO.containsKey(lineCont))
                   this.urlsIO.put(lineCont, new ArrayList<>());
        } catch (IOException ioe) {
            throw new IOException(ioe);
        }
    }

    /**
     * Starts the crawling process, executing download tasks in the thread pool.
     *
     * @throws InterruptedException If the thread pool is interrupted
     */
    public void crawl() throws InterruptedException {
        ArrayList<Downloader> down = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : this.urlsIO.entrySet())
            down.add(new ImageDownloader(entry.getKey(), entry.getValue(), this.format));

        // Execute all the downloads in the thread pool
        for (Downloader d: down)
            this.pool.execute(d);

        // Shutdown the thread pool and wait for all tasks to complete
        pool.shutdown();
        boolean re =  this.pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    /**
     * Prints the outputs of the crawling process.
     * If an error occurs, it is printed to stderr.
     */
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
