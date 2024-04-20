package ex2;

public class Main {
    private static final int FILENAME = 2;
    public static void main(String[] args) {
        try{
            Controller urlCrawler = new Controller(args, new FileReaderComp(args[FILENAME]));
            urlCrawler.crawl(); // crawl the url that given in the argument vector
            urlCrawler.print(); // Prints the output in the desired format
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: java Main <output format> <pool size> <file name>");
        } catch (NumberFormatException e) {
            System.out.printf("Second argument (Pool size) is not an Integer.\nError: %s", e.getMessage());
        } catch (Exception e){
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}