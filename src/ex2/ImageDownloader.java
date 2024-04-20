package ex2;

import java.util.ArrayList;

/**
 * ImageDownloader is a subclass of Downloader for downloading image content.
 */
public class ImageDownloader extends Downloader{
    /**
     * Constructor for ImageDownloader.
     *
     * @param urlAddr   The URL to download from
     * @param urlArr    The storage for output content
     * @param formatsG  The list of output formats to apply
     */
    public ImageDownloader(String urlAddr, ArrayList<String> urlArr, ArrayList<OutFormat> formatsG) {
        super(urlAddr, urlArr, formatsG, "image");
    }

    /**
     * Overrides the `startC` method to start the output processing.
     */
    @Override
    protected void startC(){
        super.startC();
    }

    /**
     * Overrides the `run` method to manage the download process.
     */
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

    /**
     * Overrides the `endC` method to end the output processing.
     */
    @Override
    protected void endC(){
        super.endC();
    }

}
