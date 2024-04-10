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

    public Downloader(String urlAddr, ArrayList<String> urlArr){
        System.out.println("downloader con");
        this.urlsOut = urlArr;
        this.urlAddress = urlAddr;
    }
    public void run() {
        System.out.println("download");
        var request = HttpRequest.newBuilder().uri(URI.create(this.urlAddress)).GET().build();

        var client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("header:\n"+response.headers());

        if (response.headers().firstValue("Content-Type").orElse("").startsWith("image/"))
            System.out.println(response.body());
        else
            try {
                throw new IOException(this.urlAddress+" Content-Type is not text");
            } catch (IOException e) {
                System.out.println("Error: "+e.getMessage());
            }
    }
}
