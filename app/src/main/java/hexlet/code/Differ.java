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
        String format1 = getFormat(path1);
        String format2 = getFormat(path2);

        if (!checkIfFormatsEquals(format1, format2)) {
            System.out.println("Your files have a different extension");
        } else {
            String stringOutOfPath1 = getContent(path1);
            String stringOutOfPath2 = getContent(path2);
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
    public static String getFormat(String filePath) {
        int dotInPath = filePath.lastIndexOf('.');
        return dotInPath > 0 ? filePath.substring(dotInPath + 1) : "";
    }

    private static boolean checkIfFormatsEquals(String format1, String format2) {
        return format1.equals(format2);
    }

    private static String getContent(String path) throws IOException {
        Path absoulutePath = createAbsoulutePath(path);
        return Files.readString(absoulutePath).trim();
    }

    private static Path createAbsoulutePath(String path) {
        return Paths.get(path).toAbsolutePath().normalize();
    }
}
