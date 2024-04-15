package ex2;

public class Main {
    private static final int FILENAME = 2;
    public static void main(String[] args) {
        try{
            /* You can also read urls from the terminal */
            // Controller urlCrawler = new Controller(args, "image/", new TerminalReaderComp());

            Controller urlCrawler = new Controller(args, "image/", new FileReaderComp(args[FILENAME]));
            urlCrawler.crawl();
            urlCrawler.print();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Usage: java Main <output format> <pool size> <file name>");
        } catch (NumberFormatException e) {
            System.out.printf("Second argument (Pool size) is not an Integer.\nError: %s", e.getMessage());
        } catch (Exception e){
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}