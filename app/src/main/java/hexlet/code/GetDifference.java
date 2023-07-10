package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class GetDifference {
    private static final String CHANGED = "changed";
    private static final String UNCHANGED = "unchanged";
    private static final String ADDED = "added";
    private static final String DELETED = "deleted";
    private static Map<String, Object> map1 = new TreeMap<>();
    private static Map<String, Object> map2 = new TreeMap<>();

    public static List<Map<String, Object>> mergeMapsToList(Map<String, Object> m1, Map<String, Object> m2) {
        map1 = m1;
        map2 = m2;

        return Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .sorted(Map.Entry.comparingByKey())
                .map(GetDifference::findDifference)
                .distinct()
                .toList();
    }

    static Map<String, Object> findDifference(Map.Entry<String, Object> entry) {
        String key = entry.getKey();
        Object value = entry.getValue();

        Map<String, Object> map = new TreeMap<>();

        if (checkIfKeyHaveInBothMaps(key) && checkIfKeysAreEqual(key)) {
            map.put("key", key);
            map.put("value", value);
            map.put("changes", UNCHANGED);
            return map;
        } else if (checkIfKeyHaveInBothMaps(key) && !checkIfKeysAreEqual(key)) {
            map.put("key", key);
            map.put("value1", map1.get(key));
            map.put("value2", map2.get(key));
            map.put("changes", CHANGED);
            return map;
        }
        if (map1.containsKey(key)) {
            map.put("key", key);
            map.put("value1", map1.get(key));
            map.put("changes", DELETED);
            return map;
        }
        map.put("key", key);
        map.put("value2", map2.get(key));
        map.put("changes", ADDED);
        return map;
    }

    public static boolean checkIfKeyHaveInBothMaps(String key) {
        return map1.containsKey(key) && map2.containsKey(key);
    }

    public static boolean checkIfKeysAreEqual(String key) {
        return Objects.equals(map1.get(key), map2.get(key));
    }
}
