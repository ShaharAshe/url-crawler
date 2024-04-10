package ex2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Downloader extends Thread{
    private String urlAddress;
    private ArrayList<String> urlsOut;
    private ArrayList<OutFormat> formats;

    public Downloader(String urlAddr, ArrayList<String> urlArr, ArrayList<OutFormat> formatsG){
        // System.out.println("downloader con");
        this.urlsOut = urlArr;
        this.urlAddress = urlAddr;
        // System.out.println("this.urlsOut: "+this.urlsOut);
        this.formats = formatsG;
    }
    public void run() {
        // System.out.println("download");

        // start formats check for output
        for (OutFormat f : formats) {
            f.start();
        }
        //

        var request = HttpRequest.newBuilder().uri(URI.create(this.urlAddress)).GET().build();

        var client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response.headers().firstValue("Content-Type").orElse("").startsWith("image/"))
            System.out.println("Content-Type is an image");
        else
            return;

        for (OutFormat f : this.formats) {
            this.urlsOut.add(f.end(response));
        }
    }
}
