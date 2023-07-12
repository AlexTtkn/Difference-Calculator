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
        Map<String, Object> unchanged =
                Map.of("changes", "unchanged");
        Map<String, Object> changed =
                Map.of("changes", "changed", "key", "property1", "value1", "oldValue", "value2", "newValue");
        Map<String, Object> deleted =
                Map.of("changes", "deleted", "key", "property2");
        Map<String, Object> added =
                Map.of("changes", "added", "key", "property3", "value2", "newValue");

        list.add(unchanged);
        list.add(changed);
        list.add(deleted);
        list.add(added);

        String result = Plain.addFormatToResult(list);
        String expected = """
                Property 'property1' was updated. From 'oldValue' to 'newValue'
                Property 'property2' was removed
                Property 'property3' was added with value: 'newValue'""";
        Assertions.assertEquals(expected, result);
    }

    @Test
    void testCheckTypeOfValue() {
        Object arrayList = new ArrayList<>();
        Object linkedHashMap = new LinkedHashMap<>();
        Object string = "test";
        Object intValue = 10; // Magic number

        String arrayListResult = Plain.checkValueType(arrayList);
        String linkedHashMapResult = Plain.checkValueType(linkedHashMap);
        String stringResult = Plain.checkValueType(string);
        String intResult = Plain.checkValueType(intValue);

        Assertions.assertEquals(COMPLEX_VALUE, arrayListResult);
        Assertions.assertEquals(COMPLEX_VALUE, linkedHashMapResult);
        Assertions.assertEquals("'test'", stringResult);
        Assertions.assertEquals("10", intResult);
    }
}
