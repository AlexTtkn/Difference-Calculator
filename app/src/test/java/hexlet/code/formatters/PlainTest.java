package hexlet.code.formatters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class PlainTest {
    private static final String COMPLEX_VALUE = "[complex value]";

    @Test
    void testAddFormatToResult() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> unchangedEntry =
                Map.of("changes", "unchanged");
        Map<String, Object> changedEntry =
                Map.of("changes", "changed", "key", "property1", "value1", "oldValue", "value2", "newValue");
        Map<String, Object> deletedEntry =
                Map.of("changes", "deleted", "key", "property2");
        Map<String, Object> addedEntry =
                Map.of("changes", "added", "key", "property3", "value2", "newValue");

        list.add(unchangedEntry);
        list.add(changedEntry);
        list.add(deletedEntry);
        list.add(addedEntry);

        String result = Plain.addFormatToResult(list);
        String expected = """
                Property 'property1' was updated. From 'oldValue' to 'newValue'
                Property 'property2' was removed
                Property 'property3' was added with value: 'newValue'""";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testCheckTypeOfValue() {
        Object arrayListValue = new ArrayList<>();
        Object linkedHashMapValue = new LinkedHashMap<>();
        Object stringValue = "test";
        Object intValue = 10;

        String arrayListResult = Plain.checkValueType(arrayListValue);
        String linkedHashMapResult = Plain.checkValueType(linkedHashMapValue);
        String stringResult = Plain.checkValueType(stringValue);
        String intResult = Plain.checkValueType(intValue);

        Assertions.assertEquals(COMPLEX_VALUE, arrayListResult);
        Assertions.assertEquals(COMPLEX_VALUE, linkedHashMapResult);
        Assertions.assertEquals("'test'", stringResult);
        Assertions.assertEquals("10", intResult);
    }
}
