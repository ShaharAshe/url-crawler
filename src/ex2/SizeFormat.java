package ex2;

import java.net.http.HttpResponse;

/**
 * SizeFormat is a class that retrieves the content length from an HTTP response.
 */
public class SizeFormat implements OutFormat{
    private String imageSize; // Stores the content length

    /**
     * Constructor for SizeFormat.
     * Initializes imageSize to an empty string.
     */
    public SizeFormat(){
        this.imageSize = "";
    }

    /**
     * Does nothing, required by OutFormat interface.
     */
    @Override
    public void start(){}

    /**
     * Retrieves the content length from the HTTP response's headers.
     *
     * @param response The HTTP response
     * @return A string representing the content length
     */
    @Override
    public String end(HttpResponse<String> response){
        this.imageSize = response.headers().firstValue("content-length").orElse("-1");
        return this.imageSize;
    }
}
