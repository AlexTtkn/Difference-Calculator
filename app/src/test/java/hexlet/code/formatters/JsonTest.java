package hexlet.code.formatters;

import org.junit.jupiter.api.Assertions;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class JsonTest {
    @Test
    void testAddFormatToResult() throws JsonProcessingException {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> m1 = Map.of("key1", "value1");
        Map<String, Object> m2 = Map.of("key3", true);

        list.add(m1);
        list.add(m2);

        String expected = """
                [ {
                  "key1" : "value1"
                }, {
                  "key3" : true
                } ]""";

        String actual = Json.addFormatToResult(list);
        Assertions.assertEquals(expected, actual);
    }

}
