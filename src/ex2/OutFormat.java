package ex2;

import java.net.http.HttpResponse;

public interface OutFormat {
    public void start();
    public String end(HttpResponse<String> response);
}
