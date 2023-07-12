package hexlet.code.formatters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class StylishTest {
    @Test
    void testAddSingToResult() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> m1 =
                Map.of("changes", "unchanged", "key", "key1", "value", "value1");
        Map<String, Object> m2 =
                Map.of("changes", "changed", "key", "key2", "value1", "oldValue", "value2", "newValue");
        Map<String, Object> m3 =
                Map.of("changes", "deleted", "key", "key3", "value1", "value3");
        Map<String, Object> m4 =
                Map.of("changes", "added", "key", "key4", "value2", "value4");

        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);

        String expected = """
                {
                   key1 : value1
                 - key2 : oldValue
                 + key2 : newValue
                 - key3 : value3
                 + key4 : value4
                }""";

        String actual = Stylish.addFormatToResult(list);
        Assertions.assertEquals(expected, actual);
    }
}

