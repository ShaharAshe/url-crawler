package ex2;

import java.net.http.HttpResponse;

public class TimeFormat implements OutFormat{
    private long startTime;
    private long endTime;
    private long totalTime;

    public TimeFormat(){
        this.startTime = 0;
        this.endTime = 0;
        this.totalTime = 0;
    }

    @Override
    public void start(){this.startTime = System.currentTimeMillis();}

    @Override
    public String end(HttpResponse<String> response){
        this.endTime = System.currentTimeMillis();
        this.totalTime = this.endTime - this.startTime;
        return Long.toString(this.totalTime);
    }
}
