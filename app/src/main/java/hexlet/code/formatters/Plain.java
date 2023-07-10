package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Plain {
    private static final String COMPLEX_VALUE = "[complex value]";
    private static final List<String> LIST_OF_DIFFERENCE = new ArrayList<>();

    public static String addFormatToResult(List<Map<String, Object>> list) {
        LIST_OF_DIFFERENCE.clear();
        for (Map<String, Object> entry : list) {
            switch (entry.get("changes").toString()) {
                case "unchanged" -> LIST_OF_DIFFERENCE.add("\n");
                case "changed" -> LIST_OF_DIFFERENCE.add(String.format("Property '%s' was updated. From %s to %s\n",
                        entry.get("key"), checkValueType(entry.get("value1")), checkValueType(entry.get("value2"))));
                case "deleted" ->
                        LIST_OF_DIFFERENCE.add(String.format("Property '%s' was removed\n", entry.get("key")));
                case "added" -> LIST_OF_DIFFERENCE.add(String.format("Property '%s' was added with value: %s\n",
                        entry.get("key"), checkValueType(entry.get("value2"))));
                default -> throw new RuntimeException("Unknown type!" + entry.get("changes"));
            }
        }
        return LIST_OF_DIFFERENCE.stream()
                .sorted()
                .collect(Collectors.joining(""))
                .trim();
    }

    static String checkValueType(Object value) {
        if (value == null) {
            return null;
        }
        return switch (value.getClass().getSimpleName()) {
            case "ArrayList", "LinkedHashMap" -> COMPLEX_VALUE;
            case "String" -> "'" + value + "'";
            default -> value.toString();
        };
    }
}
