package ex2;

import java.awt.*;

public class FormatFactory {
    public OutFormat createFormat(String f){
        switch (f) {
            case "s" -> {
                return new SizeFormat();
            }
            case "u" -> {
                return new UrlFormat();
            }
            case "t" -> {
                return new TimeFormat();
            }
            case "m" -> {
                return new ImagTypeFormat();
            }
            default -> throw new IllegalArgumentException("format is not good!");
        }
    }
}
