package hexlet.code;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Objects;

public class GetDifference {
    public static List<Map<String, Object>> mergeMapsToList(Map<String, Object> map1, Map<String, Object> map2) {
        List<String> keys = mergeKeys(map1, map2);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String key : keys) {
            result.add(findDifference(key, map1, map2));
        }
        return result;
    }

    private static Map<String, Object> findDifference(String key, Map<String, Object> map1, Map<String, Object> map2) {
        String changed = "changed";
        String unchanged = "unchanged";
        String added = "added";
        String deleted = "deleted";

        Map<String, Object> map = new TreeMap<>();

        if (checkIfKeyHaveInBothMaps(key, map1, map2) && checkIfKeysAreEqual(key, map1, map2)) {
            map.put("key", key);
            map.put("value", map1.get(key));
            map.put("type", unchanged);
            return map;
        } else if (checkIfKeyHaveInBothMaps(key, map1, map2) && !checkIfKeysAreEqual(key, map1, map2)) {
            map.put("key", key);
            map.put("value1", map1.get(key));
            map.put("value2", map2.get(key));
            map.put("type", changed);
            return map;
        }
        if (map1.containsKey(key)) {
            map.put("key", key);
            map.put("value1", map1.get(key));
            map.put("type", deleted);
            return map;
        }
        map.put("key", key);
        map.put("value2", map2.get(key));
        map.put("type", added);
        return map;
    }

    private static boolean checkIfKeyHaveInBothMaps(String key, Map<String, Object> map1, Map<String, Object> map2) {
        return map1.containsKey(key) && map2.containsKey(key);
    }

    private static boolean checkIfKeysAreEqual(String key, Map<String, Object> map1, Map<String, Object> map2) {
        return Objects.equals(map1.get(key), map2.get(key));
    }

    private static List<String> mergeKeys(Map<String, Object> m1, Map<String, Object> m2) {
        Map<String, Object> keys = new HashMap<>();
        keys.putAll(m1);
        keys.putAll(m2);

        return keys.keySet()
                .stream()
                .sorted()
                .toList();
    }
}
