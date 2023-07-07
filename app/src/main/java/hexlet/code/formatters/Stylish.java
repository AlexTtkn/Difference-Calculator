package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {
    public static String addSingToResult(List<Map<String, Object>> list) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("{").append("\n");

        for (Map<String, Object> entry : list) {
            switch (entry.get("type").toString()) {
                case "unchanged" ->
                        resultString.append(String.format("   %s : %s\n", entry.get("key"), entry.get("value")));
                case "changed" -> {
                    resultString.append(String.format(" - %s : %s\n", entry.get("key"), entry.get("value1")));
                    resultString.append(String.format(" + %s : %s\n", entry.get("key"), entry.get("value2")));
                }
                case "deleted" ->
                        resultString.append(String.format(" - %s : %s\n", entry.get("key"), entry.get("value1")));
                case "added" ->
                        resultString.append(String.format(" + %s : %s\n", entry.get("key"), entry.get("value2")));
                default -> System.out.println("Something wrong");
            }
        }
        return resultString.append("}\n").toString().trim();
    }
}
