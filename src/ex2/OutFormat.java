package ex2;

import java.net.http.HttpResponse;

public interface OutFormat {
    void start();
    String end(HttpResponse<String> response);
}
