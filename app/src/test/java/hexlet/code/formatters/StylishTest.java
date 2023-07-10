package hexlet.code.formatters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class StylishTest {
    @Test
    void testAddSingToResult() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> entry1 = new HashMap<>();
        entry1.put("changes", "unchanged");
        entry1.put("key", "key1");
        entry1.put("value", "value1");

        Map<String, Object> entry2 = new HashMap<>();
        entry2.put("changes", "changed");
        entry2.put("key", "key2");
        entry2.put("value1", "oldValue");
        entry2.put("value2", "newValue");

        Map<String, Object> entry3 = new HashMap<>();
        entry3.put("changes", "deleted");
        entry3.put("key", "key3");
        entry3.put("value1", "value3");

        Map<String, Object> entry4 = new HashMap<>();
        entry4.put("changes", "added");
        entry4.put("key", "key4");
        entry4.put("value2", "value4");

        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        list.add(entry4);

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

