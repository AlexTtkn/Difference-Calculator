package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FormatterTest {
    private static final String WRONG_FORMAT = "invalid";
    private static final int INT_FOR_TEST = 10;

    @Test
    public void testDetermineFormatWrongFormat() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = Map.of("key1", "value1", "key2", INT_FOR_TEST);
        list.add(map1);

        Assertions.assertThrows(RuntimeException.class, () ->
                Formatter.determineFormat(list, WRONG_FORMAT));
    }
}
