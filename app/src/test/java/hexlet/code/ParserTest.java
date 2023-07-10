package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.TreeMap;


class ParserTest {
    private static final String WRONG_FORMAT = "invalid";

    @ParameterizedTest
    @CsvSource({
        "key, value, yml, key: value",
        "key, value, json, {\"key\": \"value\"}",
    })
    void testGetInfoAsMapValid(String key, String value, String format, String info) throws IOException {
        TreeMap<String, Object> expected = new TreeMap<>();
        expected.put(key, value);
        TreeMap<String, Object> actual = Parser.getInfoAsMap(info, format);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetInfoAsMapWithInvalidFormat() {
        String info = "key: value";
        Assertions.assertThrows(RuntimeException.class, () -> {
            Parser.getInfoAsMap(info, WRONG_FORMAT);
        });
    }
}
