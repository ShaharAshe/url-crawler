package ex2;

import java.net.http.HttpResponse;

/**
 * OutFormat defines the contract for output formatting classes.
 */
public interface OutFormat {
    /**
     * Start method, usually for initialization.
     */
    void start();

    /**
     * End method, called at the end of processing.
     *
     * @param response The HTTP response
     * @return A string representing the processed output
     */
    String end(HttpResponse<String> response);
}
