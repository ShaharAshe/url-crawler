package ex2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * Downloader is an abstract class for downloading content from URLs.
 */
public abstract class Downloader extends Thread{
    protected final String urlAddress; // The URL to download from
    protected final ArrayList<String> urlsOut; // The output storage for downloaded content
    protected final ArrayList<OutFormat> formats; // The list of output formats to apply
    protected final String contentType; // Expected content type for validation
    private HttpResponse<String> response = null; // Stores the HTTP response

    /**
     * Constructor for Downloader.
     *
     * @param urlAddr   The URL to download from
     * @param urlArr    The storage for output content
     * @param formatsG  The list of output formats to apply
     * @param type      The expected content type for validation
     */
    public Downloader(String urlAddr, ArrayList<String> urlArr, ArrayList<OutFormat> formatsG, String type) {
        this.urlsOut = urlArr;
        this.urlAddress = urlAddr;
        this.formats = formatsG;
        this.contentType = type;
    }

    /**
     * Starts the processing of output formats.
     */
    void startC(){
        for (OutFormat f : formats)
            f.start();
    }

    /**
     * Initiates the download process, sends an HTTP request, and applies formats.
     */
    @Override
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
                throw new Exception("Invalid content type");
        }catch (IllegalArgumentException e){
            this.urlsOut.add("");
            this.urlsOut.add("'"+this.urlAddress+"' malformed");
        }
        catch (RuntimeException e){
            this.urlsOut.add("");
            this.urlsOut.add("'"+this.urlAddress+"' failed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ends the processing of output formats and stores the results.
     */
    void endC(){
        for (OutFormat f : this.formats)
            this.urlsOut.add(f.end(this.response));
    }
}
