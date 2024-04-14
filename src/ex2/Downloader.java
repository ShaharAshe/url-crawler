package ex2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Downloader extends Thread{
    private final String urlAddress;
    private final ArrayList<String> urlsOut;
    private final ArrayList<OutFormat> formats;
    private final String contentType;

    public Downloader(String urlAddr, ArrayList<String> urlArr, ArrayList<OutFormat> formatsG, String type) {
        this.urlsOut = urlArr;
        this.urlAddress = urlAddr;
        this.formats = formatsG;
        this.contentType = type;
    }

    public void run() {
        try {
            // start formats check for output
            for (OutFormat f : formats)
                f.start();
            /////////////////////////////////

            var request = HttpRequest.newBuilder().uri(URI.create(this.urlAddress)).GET().build();

            var client = HttpClient.newHttpClient();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!response.headers().firstValue("Content-Type").orElse("").startsWith(this.contentType))
                return;

            // end formats check for output
            for (OutFormat f : this.formats)
                this.urlsOut.add(f.end(response));
            ///////////////////////////////
        }catch (IllegalArgumentException e){
            System.err.println(this.urlAddress+" malformed");
        }
        catch (RuntimeException e){
            System.err.println(this.urlAddress+" failed");
        }
    }
}
