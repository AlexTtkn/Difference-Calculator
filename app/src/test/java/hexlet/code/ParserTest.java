package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {
    private static final String WRONG_FORMAT = "invalid";

    @Test
    void testGetInfoAsMapWithInvalidFormat() {
        String info = "key: value";
        Assertions.assertThrows(RuntimeException.class, () -> {
            Parser.getInfoAsMap(info, WRONG_FORMAT);
        });
    }
}
