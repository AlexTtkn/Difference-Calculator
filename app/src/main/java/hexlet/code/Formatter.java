package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Formatter {
    public static String determineFormat(List<Map<String, Object>> list, String format) throws IOException {
        return switch (format) {
            case "stylish" -> Stylish.addFormatToResult(list);
            case "plain" -> Plain.addFormatToResult(list);
            case "json" -> Json.addFormatToResult(list);
            default -> "Wrong format";
        };
    }
}
