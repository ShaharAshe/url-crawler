package ex2;

import java.net.http.HttpResponse;

/**
 * TimeFormat measures the time taken for an HTTP response.
 */
public class TimeFormat implements OutFormat{
    private long startTime; // Start time for measuring duration
    private long endTime; // End time for measuring duration
    private long totalTime; // Total time calculated

    /**
     * Constructor for TimeFormat.
     * Initializes all timing variables to 0.
     */
    public TimeFormat(){
        this.startTime = 0;
        this.endTime = 0;
        this.totalTime = 0;
    }

    /**
     * Starts timing by recording the current system time.
     */
    @Override
    public void start(){this.startTime = System.currentTimeMillis();}

    /**
     * Ends timing and calculates the total time taken.
     *
     * @param response The HTTP response
     * @return A string representing the total time in milliseconds
     */
    @Override
    public String end(HttpResponse<String> response){
        this.endTime = System.currentTimeMillis();
        this.totalTime = this.endTime - this.startTime;
        return Long.toString(this.totalTime);
    }
}
