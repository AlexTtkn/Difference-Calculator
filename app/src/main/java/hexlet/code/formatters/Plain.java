package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Plain {
    public static String addFormatToResult(List<Map<String, Object>> list) {
        List<String> listOfDifference = new ArrayList<>();
        for (Map<String, Object> entry : list) {
            switch (entry.get("type").toString()) {
                case "unchanged" -> listOfDifference.add("\n");
                case "changed" -> listOfDifference.add(String.format("Property '%s' was updated. From %s to %s\n",
                        entry.get("key"), checkValueType(entry.get("value1")), checkValueType(entry.get("value2"))));
                case "deleted" -> listOfDifference.add(String.format("Property '%s' was removed\n", entry.get("key")));
                case "added" -> listOfDifference.add(String.format("Property '%s' was added with value: %s\n",
                        entry.get("key"), checkValueType(entry.get("value2"))));
                default -> throw new RuntimeException("Something wrong");
            }
        }
        return listOfDifference.stream()
                .sorted()
                .collect(Collectors.joining(""))
                .trim();
    }

    static String checkValueType(Object value) {
        String complexValue = "[complex value]";
        if (value == null) {
            return null;
        }
        return switch (value.getClass().getSimpleName()) {
            case "ArrayList", "LinkedHashMap" -> complexValue;
            case "String" -> "'" + value + "'";
            default -> value.toString();
        };
    }
}
