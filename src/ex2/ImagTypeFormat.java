package ex2;

import java.net.http.HttpResponse;

/**
 * ImagTypeFormat is a class that extracts the image type from the HTTP response.
 */
public class ImagTypeFormat implements OutFormat {
    private String imageType; // Stores the extracted image type

    /**
     * Constructor for ImagTypeFormat.
     * Initializes imageType to an empty string.
     */
    public ImagTypeFormat(){
        this.imageType = "";
    }

    /**
     * Does nothing, required by OutFormat interface.
     */
    @Override
    public void start(){}

    /**
     * Extracts the image type from the HTTP response's headers.
     *
     * @param response The HTTP response
     * @return A string representing the image type
     */
    @Override
    public String end(HttpResponse<String> response){
        this.imageType = response.headers().firstValue("content-type").orElse("");
        return this.imageType;
    }
}
