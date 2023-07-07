package hexlet.code;

import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String determineFormat(List<Map<String, Object>> list, String format) {
        return switch (format) {
            case "stylish" -> Stylish.addSingToResult(list);
            default -> "Wrong format";
        };
    }
}
