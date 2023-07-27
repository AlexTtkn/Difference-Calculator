package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class GetDifferenceTest {
    @Test
    public void testMergeMapsToList() {
        Map<String, Object> map1 = Map.of("key1", "value1", "key2", "value2");
        Map<String, Object> map2 = Map.of("key1", "value3", "key3", "value4");

        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> m1 =
                Map.of("key", "key1", "value1", "value1", "value2", "value3", "type", "changed");
        Map<String, Object> m2 =
                Map.of("key", "key2", "value1", "value2", "type", "deleted");
        Map<String, Object> m3 =
                Map.of("key", "key3", "value2", "value4", "type", "added");

        expected.add(m1);
        expected.add(m2);
        expected.add(m3);

        List<Map<String, Object>> result = GetDifference.mergeMapsToList(map1, map2);
        Assertions.assertEquals(expected, result);
    }
}
