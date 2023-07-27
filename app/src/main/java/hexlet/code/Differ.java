package hexlet.code;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Differ {
    public static String generate(String path1, String path2, String format) throws IOException {
        String format1 = Parser.getFormat(path1); // заданынй путь
        String format2 = Parser.getFormat(path2);

        if (!checkIfFormatsEquals(format1, format2)) {
            System.out.println("Your files have a different extension");
        } else {
            String stringOutOfPath1 = getStringOutOfPath(path1);
            String stringOutOfPath2 = getStringOutOfPath(path2);
            Map<String, Object> map1 = new TreeMap<>(Parser.getInfoAsMap(stringOutOfPath1, format1));
            Map<String, Object> map2 = new TreeMap<>(Parser.getInfoAsMap(stringOutOfPath2, format2));
            List<Map<String, Object>> listOfDifference = GetDifference.mergeMapsToList(map1, map2);
            return Formatter.determineFormat(listOfDifference, format);
        }
        return null;
    }

    public static String generate(String path1, String path2) throws IOException {
        return generate(path1, path2, "stylish");
    }

    private static boolean checkIfFormatsEquals(String format1, String format2) {
        return format1.equals(format2);
    }

    private static String getStringOutOfPath(String path) throws IOException {
        Path absoulutePath = createAbsoulutePath(path);
        StringBuilder resultString = new StringBuilder();
        resultString.delete(0, resultString.length());
        List<String> lines = Files.readAllLines(absoulutePath);
        for (String line : lines) {
            resultString.append(line).append("\n");
        }
        return resultString.toString().trim();
    }

    private static Path createAbsoulutePath(String path) {
        return Paths.get(path).toAbsolutePath().normalize();
    }
}
