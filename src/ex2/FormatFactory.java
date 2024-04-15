package ex2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FormatFactory {
    final static Map<String, Supplier<OutFormat>> map = new HashMap<>();

    public static void register(FormatType type, Supplier<OutFormat> shapeCreateFunction) {
        map.put(type.name(), shapeCreateFunction);
    }

    public OutFormat createFormat(String f) {
        Supplier<OutFormat> shapeFunc = map.get(f);

        if (shapeFunc != null)
            return shapeFunc.get();
        throw new IllegalArgumentException("format '"+f+"' is not good!");
    }
}
