package ex2;

import java.util.ArrayList;

public class TextDownloader extends Downloader{
    public TextDownloader(String urlAddr, ArrayList<String> urlArr, ArrayList<OutFormat> formatsG) {
        super(urlAddr, urlArr, formatsG, "text");
    }

    @Override
    protected void startC(){
        super.startC();
    }

    @Override
    public void run(){
        try {
            this.startC();
            super.run();
            this.endC();
        }catch (Exception e){
            return;
        }

    }

    @Override
    protected void endC(){
        super.endC();
    }
}

