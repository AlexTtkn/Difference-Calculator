package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    private static final String YAML = "yml";
    private static final String JSON = "json";
    private static final String WRONG_FORMAT = "invalid";

    @Test
    void testGetInfoAsMapWithYamlFormat() throws IOException {
        String info = "key: value";
        TreeMap<String, Object> expected = new TreeMap<>();
        expected.put("key", "value");
        TreeMap<String, Object> actual = Parser.getInfoAsMap(info, YAML);
        assertEquals(expected, actual);
    }

    @Test
    void testGetInfoAsMapWithJsonFormat() throws IOException {
        String info = "{\"key\": \"value\"}";
        TreeMap<String, Object> actual = new TreeMap<>();
        actual.put("key", "value");
        TreeMap<String, Object> result = Parser.getInfoAsMap(info, JSON);
        assertEquals(actual, result);
    }

    @Test
    void testGetInfoAsMapWithInvalidFormat() {
        String info = "key: value";
        assertThrows(RuntimeException.class, () -> {
            Parser.getInfoAsMap(info, WRONG_FORMAT);
        });
    }
}
