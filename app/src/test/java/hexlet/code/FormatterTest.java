package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

class FormatterTest {
    private static final int INT_FOR_TEST = 10;
    @Test
    public void testDetermineFormatWrongFormat() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = Map.of("key1", "value1", "key2", INT_FOR_TEST);
        list.add(map1);
        String expected = "Wrong format";
        String actual = Formatter.determineFormat(list, "invalid");
        Assertions.assertEquals(expected, actual);
    }
}
