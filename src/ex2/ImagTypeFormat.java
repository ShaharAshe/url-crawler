package ex2;

import java.net.http.HttpResponse;

public class ImagTypeFormat implements OutFormat{
    private String imageType;

    public ImagTypeFormat(){
        this.imageType = "";
    }

    @Override
    public void start(){}

    @Override
    public String end(HttpResponse<String> response){
        this.imageType = response.headers().firstValue("content-type").orElse("");
        return this.imageType;
    }
}
