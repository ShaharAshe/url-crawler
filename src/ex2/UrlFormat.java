package ex2;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * UrlFormat is a class that extracts the URL from an HTTP response.
 */
public class UrlFormat implements OutFormat{
    private String currentUrl; // The extracted URL

    /**
     * Constructor for UrlFormat.
     * Initializes currentUrl to an empty string.
     */
    public UrlFormat(){
        this.currentUrl = "";
    }

    /**
     * Does nothing, required by OutFormat interface.
     */
    @Override
    public void start(){}

    /**
     * Extracts the URL from the HTTP response's request.
     *
     * @param response The HTTP response
     * @return The extracted URL as a string
     */
    @Override
    public String end(HttpResponse<String> response) {
        // Get the HttpHeaders from the response
        HttpRequest request = response.request();
        URI uri = request.uri();
        this.currentUrl = uri.toString();
        return this.currentUrl;
    }
}
