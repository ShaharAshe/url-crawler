package ex2;

public class Main {
    public static void main(String[] args) {
        try{
            Controller urlCrawler = new Controller(args);
            urlCrawler.crawl();
            System.out.println("=======================\n");
            urlCrawler.print();
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("Usage: java Main <output format> <pool size> <file name>");
        } catch (NumberFormatException e) {
            System.out.printf("Second argument (Pool size) is not an Integer.\nError: %s", e.getMessage());
        } catch (Exception e){
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}