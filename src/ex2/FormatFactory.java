package ex2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * FormatFactory is a factory class for creating output formats.
 * It uses a map to store the registered format types and their respective suppliers.
 */
public class FormatFactory {
    final static Map<String, Supplier<OutFormat>> map = new HashMap<>();

    /**
     * Registers a new format type with its corresponding supplier.
     *
     * @param type             The format type to register
     * @param shapeCreateFunction A supplier that creates the format
     */
    public static void register(FormatType type, Supplier<OutFormat> shapeCreateFunction) {
        map.put(type.name(), shapeCreateFunction);
    }

    /**
     * Creates a format based on the specified key.
     *
     * @param f The key for the format type
     * @return An OutFormat instance
     * @throws IllegalArgumentException If the format type is not registered
     */
    public OutFormat createFormat(String f) {
        Supplier<OutFormat> shapeFunc = map.get(f);

        if (shapeFunc != null)
            return shapeFunc.get();
        throw new IllegalArgumentException("format '"+f+"' is not good!");
    }
}
