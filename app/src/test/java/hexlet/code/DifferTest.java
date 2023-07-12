package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

class DifferTest {
    private static final String RESOURCE_DIRECTORY_1 =
            Path.of("src/test/resources/testFile1.json").toAbsolutePath().normalize().toString();
    private static final String RESOURCE_DIRECTORY_2
            = Path.of("src/test/resources/testFile2.json").toAbsolutePath().normalize().toString();
    private static final String RESOURCE_DIRECTORY_3
            = Path.of("src/test/resources/testFile3.yml").toAbsolutePath().normalize().toString();
    private static final String RESOURCE_DIRECTORY_4
            = Path.of("src/test/resources/testFile4.yml").toAbsolutePath().normalize().toString();
    private static final String WRONG_PATH = Path.of("wrongPath").toString();

    @Test
    void testGenerateValidJsonStylish() throws IOException {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_1, RESOURCE_DIRECTORY_2, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYamlStylish() throws IOException {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_3, RESOURCE_DIRECTORY_4, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidJsonPlain() throws IOException {
        String expected = """
                Property 'follow' was removed
                Property 'proxy' was removed
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_1, RESOURCE_DIRECTORY_2, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYamlPlain() throws IOException {
        String expected = """
                Property 'follow' was removed
                Property 'proxy' was removed
                Property 'timeout' was updated. From 50 to 20
                Property 'verbose' was added with value: true""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_3, RESOURCE_DIRECTORY_4, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidJsonJson() throws IOException {
        String expected = """
                [ {
                  "changes" : "deleted",
                  "key" : "follow",
                  "value1" : false
                }, {
                  "changes" : "unchanged",
                  "key" : "host",
                  "value" : "hexlet.io"
                }, {
                  "changes" : "deleted",
                  "key" : "proxy",
                  "value1" : "123.234.53.22"
                }, {
                  "changes" : "changed",
                  "key" : "timeout",
                  "value1" : 50,
                  "value2" : 20
                }, {
                  "changes" : "added",
                  "key" : "verbose",
                  "value2" : true
                } ]""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_1, RESOURCE_DIRECTORY_2, "json");
        System.out.println(actual);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYamlJson() throws IOException {
        String expected = """
                [ {
                  "changes" : "deleted",
                  "key" : "follow",
                  "value1" : false
                }, {
                  "changes" : "unchanged",
                  "key" : "host",
                  "value" : "hexlet.io"
                }, {
                  "changes" : "deleted",
                  "key" : "proxy",
                  "value1" : "123.234.53.22"
                }, {
                  "changes" : "changed",
                  "key" : "timeout",
                  "value1" : 50,
                  "value2" : 20
                }, {
                  "changes" : "added",
                  "key" : "verbose",
                  "value2" : true
                } ]""";

        String actual = Differ.generate(RESOURCE_DIRECTORY_3, RESOURCE_DIRECTORY_4, "json");
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
        String actual = Differ.getStringOutOfPath(Path.of(RESOURCE_DIRECTORY_1));
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
        String actual = Differ.getStringOutOfPath(Path.of(RESOURCE_DIRECTORY_2));
        Assertions.assertNotEquals(expected, actual);
    }
}
