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
    private static StringBuilder resultString = new StringBuilder();
    private static Map<String, Object> map1 = new TreeMap<>();
    private static Map<String, Object> map2 = new TreeMap<>();

    public static String generate(Path file1, Path file2, String format) throws IOException {
        String stringOutOfPath1 = getStringOutOfPath(file1);
        String stringOutOfPath2 = getStringOutOfPath(file2);
//        System.out.println("fileAsString1 = " + fileAsString1+ "END1");
//        System.out.println("fileAsString2 = " + fileAsString2 + "END2");
        map1.putAll(getInfoFromStringToMap(stringOutOfPath1));
        map2.putAll(getInfoFromStringToMap(stringOutOfPath2));
//        System.out.println("Map1 = " + map1 + "END");
//        System.out.println("Map2 = " + map2 + "END");
        resultString.delete(0, resultString.length());
//        System.out.println("resultString = " + resultString + "END");
        resultString.append("{\n");
        for (String entry : mergeMapsToList()) {
            String replacer = entry.replace("=", " : ");
            resultString.append(replacer).append("\n");
        }
        resultString.append("}");
        return resultString.toString();
    }

    private static String getStringOutOfPath(Path path) throws IOException {
        resultString.delete(0, resultString.length());
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            resultString.append(line).append("\n");
        }
        return resultString.toString();
    }

    private static Map<String, Object> getInfoFromStringToMap(String info) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(info, new TypeReference<TreeMap<String, Object>>() {
        });
    }

    private static List<String> mergeMapsToList() {
        return Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .distinct()
                .sorted(Map.Entry.comparingByKey())
                .map(Differ::addDifferenceSingToResult)
                .toList();
    }

    private static String addDifferenceSingToResult(Map.Entry<String, Object> entry) {
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
