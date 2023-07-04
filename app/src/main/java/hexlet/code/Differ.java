package hexlet.code;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {
    private static final StringBuilder RESULT_STRING = new StringBuilder();

    public static String generate(Path path1, Path path2, String format) throws IOException {
        String stringOutOfPath1 = getStringOutOfPath(path1);
        String stringOutOfPath2 = getStringOutOfPath(path2);
//        System.out.println("stringOutOfPath1 = " + stringOutOfPath1 + "END1");
//        System.out.println("stringOutOfPath2 = " + stringOutOfPath1 + "END2");
        Map<String, Object> map1 = new TreeMap<>(getInfoFromStringToMap(stringOutOfPath1));
        Map<String, Object> map2 = new TreeMap<>(getInfoFromStringToMap(stringOutOfPath2));
//        System.out.println("Map1 = " + map1 + "END");
//        System.out.println("Map2 = " + map2 + "END");
        RESULT_STRING.delete(0, RESULT_STRING.length());
//        System.out.println("resultString = " + resultString + "END");
        RESULT_STRING.append("{\n");
        for (String entry : mergeMapsToList(map1, map2)) {
            String replacer = entry.replace("=", " : ");
            RESULT_STRING.append(replacer).append("\n");
        }
        RESULT_STRING.append("}\n");
        return RESULT_STRING.toString();
    }

    static String getStringOutOfPath(Path path) throws IOException {
        RESULT_STRING.delete(0, RESULT_STRING.length());
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            RESULT_STRING.append(line).append("\n");
        }
        return RESULT_STRING.toString();
    }

    static Map<String, Object> getInfoFromStringToMap(String info) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(info, new TypeReference<TreeMap<String, Object>>() {
        });
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
}
