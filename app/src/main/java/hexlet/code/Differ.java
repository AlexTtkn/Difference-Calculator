package hexlet.code;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;


public class Differ {
    private static final StringBuilder RESULT_STRING = new StringBuilder();

    public static String generate(Path path1, Path path2, String format) throws IOException {
        String format1 = Parser.getFormat(path1.toString());
        String format2 = Parser.getFormat(path2.toString());

        if (!checkIfFormatsEquals(format1, format2)) {
            System.out.println("Your files have a different extension");
        } else {
            String stringOutOfPath1 = getStringOutOfPath(path1);
            String stringOutOfPath2 = getStringOutOfPath(path2);
            Map<String, Object> map1 = new TreeMap<>(Parser.getInfoAsMap(stringOutOfPath1, format1));
            Map<String, Object> map2 = new TreeMap<>(Parser.getInfoAsMap(stringOutOfPath2, format2));
            return getResultString(map1, map2);
        }
        return null;
    }

    static boolean checkIfFormatsEquals(String format1, String format2) {
        return format1.equals(format2);
    }

    static String getStringOutOfPath(Path path) throws IOException {
        RESULT_STRING.delete(0, RESULT_STRING.length());
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            RESULT_STRING.append(line).append("\n");
        }
        return RESULT_STRING.toString();
    }

    static List<String> mergeMapsToList(Map<String, Object> map1, Map<String, Object> map2) {
        return Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .distinct()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> addSingToResult(entry, map1, map2))
                .toList();
    }

    static String addSingToResult(Map.Entry<String, Object> entry, Map<String, Object> map1, Map<String, Object> map2) {
        if (map1.containsKey(entry.getKey()) && map2.containsKey(entry.getKey())) {
            if (map1.get(entry.getKey()).equals(map2.get(entry.getKey()))) {
                return "  " + entry;
            }
        }
        if (map1.entrySet().contains(entry)) {
            return "- " + entry;
        } else if (map2.entrySet().contains(entry)) {
            return "+ " + entry;
        }
        return "  " + entry;
    }

    static String getResultString(Map<String, Object> map1, Map<String, Object> map2) {
        RESULT_STRING.delete(0, RESULT_STRING.length());
        RESULT_STRING.append("{\n");
        for (String entry : mergeMapsToList(map1, map2)) {
            String replacer = entry.replace("=", " : ");
            RESULT_STRING.append(replacer).append("\n");
        }
        RESULT_STRING.append("}\n");
        return RESULT_STRING.toString();
    }
}
