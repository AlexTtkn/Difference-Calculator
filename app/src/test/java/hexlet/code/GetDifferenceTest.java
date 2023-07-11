package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


class GetDifferenceTest {
    @Test
    public void testMergeMapsToList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("key1", "value1");
        map1.put("key2", "value2");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("key1", "value3");
        map2.put("key3", "value4");

        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> m1 = new TreeMap<>();
        m1.put("key", "key1");
        m1.put("value1", "value1");
        m1.put("value2", "value3");
        m1.put("changes", "changed");

        Map<String, Object> m2 = new TreeMap<>();
        m2.put("key", "key2");
        m2.put("value1", "value2");
        m2.put("changes", "deleted");

        Map<String, Object> m3 = new TreeMap<>();
        m3.put("key", "key3");
        m3.put("value2", "value4");
        m3.put("changes", "added");

        expected.add(m1);
        expected.add(m2);
        expected.add(m3);

        List<Map<String, Object>> result = GetDifference.mergeMapsToList(map1, map2);
        Assertions.assertEquals(expected, result);
    }
}