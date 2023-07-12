package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

class DifferTest {
    private static final Path RESOURCE_DIRECTORY_1 =
            Path.of("src/test/resources/testFile1.json").toAbsolutePath().normalize();
    private static final Path RESOURCE_DIRECTORY_2
            = Path.of("src/test/resources/testFile2.json").toAbsolutePath().normalize();
    private static final Path RESOURCE_DIRECTORY_3
            = Path.of("src/test/resources/testFile3.yml").toAbsolutePath().normalize();
    private static final Path RESOURCE_DIRECTORY_4
            = Path.of("src/test/resources/testFile4.yml").toAbsolutePath().normalize();
    private static final Path WRONG_PATH = Path.of("wrongPath");

    @Test
    void testGenerateValidJson() throws IOException {
        String expected = """
                {
                 - follow : false
                   host : hexlet.io
                 - proxy : 123.234.53.22
                 - timeout : 50
                 + timeout : 20
                 + verbose : true
                }""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_1, RESOURCE_DIRECTORY_2, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYaml() throws IOException {
        String expected = """
                {
                 - follow : false
                   host : hexlet.io
                 - proxy : 123.234.53.22
                 - timeout : 50
                 + timeout : 20
                 + verbose : true
                }""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_3, RESOURCE_DIRECTORY_4, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateInvalidPath() {
        Assertions.assertThrows(NoSuchFileException.class, () ->
                Differ.generate(WRONG_PATH, WRONG_PATH, "stylish"));
    }

    @Test
    void testGetStringOutOfPathValid() throws IOException {
        String expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        String actual = Differ.getStringOutOfPath(RESOURCE_DIRECTORY_1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetStringOutOfPathNotEqualsInfo() throws IOException {
        String expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        String actual = Differ.getStringOutOfPath(RESOURCE_DIRECTORY_2);
        Assertions.assertNotEquals(expected, actual);
    }
}
