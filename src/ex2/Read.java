package ex2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The `Read` interface defines a contract for reading content from a source.
 * It specifies a single method, `readContent`, which returns a list of strings.
 */
public interface Read {
    /**
     * Reads content from a specific source, returning it as a list of strings.
     *
     * @return A list of strings containing the content read from the source.
     * @throws IOException If an I/O error occurs during reading.
     */
    ArrayList<String> readContent() throws IOException;
}