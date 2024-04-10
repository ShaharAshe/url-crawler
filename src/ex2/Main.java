package ex2;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            Controler urlCrawler = new Controler(args);
            urlCrawler.crawl();
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.err.println("Usage: java Main <output format> <pool size> <file name>");
        } catch (NumberFormatException e) {
            System.out.printf("Second argument (Pool size) is not an Integer.\nError: %s", e.getMessage());
        } catch (Exception e){
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}