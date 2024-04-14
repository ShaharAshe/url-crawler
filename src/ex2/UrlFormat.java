package ex2;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UrlFormat implements OutFormat{
    private String currentUrl;

    public UrlFormat(){
        this.currentUrl = "";
    }

    @Override
    public void start(){}

    @Override
    public String end(HttpResponse<String> response) {
        // Get the HttpHeaders from the response
        HttpRequest request = response.request();
        URI uri = request.uri();
        this.currentUrl = uri.toString();
        return this.currentUrl;
    }
}
