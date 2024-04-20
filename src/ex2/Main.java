package ex2;

/**
 * Main class for executing the program.
 */
public class Main {
    private static final int FILENAME = 2; // Index for filename argument

    /**
     * Main method to start the program.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        try{
            // Create a controller and execute the crawl process
            Controller urlCrawler = new Controller(args, new FileReaderComp(args[FILENAME]));
            urlCrawler.crawl(); // Crawl the URLs from the argument
            urlCrawler.print(); // Print the output in the desired format
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: java Main <output format> <pool size> <file name>");
        } catch (NumberFormatException e) {
            System.out.printf("Second argument (Pool size) is not an Integer.\nError: %s", e.getMessage());
        } catch (Exception e){
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}