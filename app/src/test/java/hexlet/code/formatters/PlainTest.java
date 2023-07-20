package hexlet.code.formatters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class PlainTest {
    private static final String COMPLEX_VALUE = "[complex value]";
    private static final int INT_FOR_TEST = 10;

    @Test
    void testAddFormatToResult() throws IOException {
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


        String path = "src/test/resources/fixtures/formatters.fixtures/expected_plain";
        String expected = Files.readString(Paths.get(path)).replaceAll("\\r\\n", "\n");
        String actual = Plain.addFormatToResult(list);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCheckTypeOfValue() {
        Object arrayList = new ArrayList<>();
        Object linkedHashMap = new LinkedHashMap<>();
        Object string = "test";

        String arrayListResult = Plain.checkValueType(arrayList);
        String linkedHashMapResult = Plain.checkValueType(linkedHashMap);
        String stringResult = Plain.checkValueType(string);
        String intResult = Plain.checkValueType(INT_FOR_TEST);

        Assertions.assertEquals(COMPLEX_VALUE, arrayListResult);
        Assertions.assertEquals(COMPLEX_VALUE, linkedHashMapResult);
        Assertions.assertEquals("'test'", stringResult);
        Assertions.assertEquals("10", intResult);
    }
}
