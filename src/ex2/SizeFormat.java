package ex2;

import java.net.http.HttpResponse;

public class SizeFormat implements OutFormat{
    private String imageSize;

    public SizeFormat(){
        this.imageSize = "";
    }

    @Override
    public void start(){}

    @Override
    public String end(HttpResponse<String> response){
        this.imageSize = response.headers().firstValue("content-length").orElse("-1");
        return this.imageSize;
    }
}
