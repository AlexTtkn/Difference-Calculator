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
    private static final Map<String, Object> MAP_1 = new TreeMap<>();
    private static final Map<String, Object> MAP_2 = new TreeMap<>();

    public static String generate(Path path1, Path path2, String format) throws IOException {
        String stringOutOfPath1 = getStringOutOfPath(path1);
        String stringOutOfPath2 = getStringOutOfPath(path2);
//        System.out.println("stringOutOfPath1 = " + fileAsString1+ "END1");
//        System.out.println("stringOutOfPath2 = " + fileAsString2 + "END2");
        MAP_1.putAll(getInfoFromStringToMap(stringOutOfPath1));
        MAP_2.putAll(getInfoFromStringToMap(stringOutOfPath2));
//        System.out.println("Map1 = " + map1 + "END");
//        System.out.println("Map2 = " + map2 + "END");
        RESULT_STRING.delete(0, RESULT_STRING.length());
//        System.out.println("resultString = " + resultString + "END");
        RESULT_STRING.append("{\n");
        for (String entry : mergeMapsToList()) {
            String replacer = entry.replace("=", " : ");
            RESULT_STRING.append(replacer).append("\n");
        }
        RESULT_STRING.append("}");
        return RESULT_STRING.toString();
    }

    private static String getStringOutOfPath(Path path) throws IOException {
        RESULT_STRING.delete(0, RESULT_STRING.length());
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            RESULT_STRING.append(line).append("\n");
        }
        return RESULT_STRING.toString();
    }

    private static Map<String, Object> getInfoFromStringToMap(String info) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(info, new TypeReference<TreeMap<String, Object>>() {
        });
    }

    private static List<String> mergeMapsToList() {
        return Stream.concat(MAP_1.entrySet().stream(), MAP_2.entrySet().stream())
                .distinct()
                .sorted(Map.Entry.comparingByKey())
                .map(Differ::addDifferenceSingToResult)
                .toList();
    }

    private static String addDifferenceSingToResult(Map.Entry<String, Object> entry) {
        if (MAP_1.containsKey(entry.getKey()) && MAP_2.containsKey(entry.getKey())) {
            if (MAP_1.get(entry.getKey()).equals(MAP_2.get(entry.getKey()))) {
                return "  " + entry;
            }
        }
        if (MAP_1.entrySet().contains(entry)) {
            return "- " + entry;
        } else if (MAP_2.entrySet().contains(entry)) {
            return "+ " + entry;
        }
        return "  " + entry;
    }
}
