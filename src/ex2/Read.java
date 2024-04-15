package ex2;

import java.io.IOException;
import java.util.ArrayList;

public interface Read {
    ArrayList<String> readContent() throws IOException;
}