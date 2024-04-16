package ex2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public abstract class Downloader extends Thread{
    protected final String urlAddress;
    protected final ArrayList<String> urlsOut;
    protected final ArrayList<OutFormat> formats;
    protected final String contentType;
    private HttpResponse<String> response = null;

    public Downloader(String urlAddr, ArrayList<String> urlArr, ArrayList<OutFormat> formatsG, String type) {
        this.urlsOut = urlArr;
        this.urlAddress = urlAddr;
        this.formats = formatsG;
        this.contentType = type;
    }

    void startC(){
        // start formats check for output
        for (OutFormat f : formats)
            f.start();
        /////////////////////////////////
    }

    public void run() {
        try {
            var request = HttpRequest.newBuilder().uri(URI.create(this.urlAddress)).GET().build();

            var client = HttpClient.newHttpClient();

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!response.headers().firstValue("Content-Type").orElse("").startsWith(this.contentType))
                throw new Exception("");

        }catch (IllegalArgumentException e){
            System.err.println("'"+this.urlAddress+"' malformed");
        }
        catch (RuntimeException e){
            System.err.println("'"+this.urlAddress+"' failed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void endC(){
        // end formats check for output
        for (OutFormat f : this.formats)
            this.urlsOut.add(f.end(response));
        ///////////////////////////////
    }
}
