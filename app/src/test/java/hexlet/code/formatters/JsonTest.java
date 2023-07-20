package hexlet.code.formatters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class JsonTest {
    @Test
    void testAddFormatToResult() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> m1 = Map.of("key1", "value1");
        Map<String, Object> m2 = Map.of("key3", true);

        list.add(m1);
        list.add(m2);

        String path = "src/test/resources/fixtures/formatters.fixtures/expected_json";
        String expected = Files.readString(Paths.get(path)).replaceAll("\\r\\n", "\n");
        String actual = Json.addFormatToResult(list);
        Assertions.assertEquals(expected, actual);
    }

}
