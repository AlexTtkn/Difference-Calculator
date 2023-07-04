package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.AbstractMap;


import static hexlet.code.Differ.generate;
import static hexlet.code.Differ.getInfoFromStringToMap;
import static hexlet.code.Differ.getStringOutOfPath;
import static hexlet.code.Differ.mergeMapsToList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    private static final Path RESOURCE_DIRECTORY_1 =
            Path.of("src/test/resources/testFile1.json").toAbsolutePath().normalize();
    private static final Path RESOURCE_DIRECTORY_2
            = Path.of("src/test/resources/testFile2.json").toAbsolutePath().normalize();
    private static final Path WRONG_PATH = Path.of("wrongPath");
    private static final Map<String, Object> MAP_1 = Map.of("key1", "value1", "key2", "value2");
    private static final Map<String, Object> MAP_2 = Map.of("key2", "value2", "key3", "value3");

    @Test
    public void testGenerateValid() throws IOException {
        String expected = """
                {
                - follow : false
                  host : hexlet.io
                - proxy : 123.234.53.22
                - timeout : 50
                + timeout : 20
                + verbose : true
                }
                """;

        String actual = generate(RESOURCE_DIRECTORY_1, RESOURCE_DIRECTORY_2, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateInvalidPath() {
        assertThrows(NoSuchFileException.class, () ->
                generate(RESOURCE_DIRECTORY_2, WRONG_PATH, null));
    }

    @Test
    public void testGetStringOutOfPathValid() throws IOException {
        String expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        String actual = getStringOutOfPath(RESOURCE_DIRECTORY_1);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStringOutOfPathNotEqualsInfo() throws IOException {
        String expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        String actual = getStringOutOfPath(RESOURCE_DIRECTORY_2);
        assertNotEquals(expected, actual);
    }

    @Test
    public void testGetInfoFromStringToMapValid() throws IOException {
        String info = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        Map<String, Object> expected = new HashMap<>();
        expected.put("host", "hexlet.io");
        expected.put("timeout", 50);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);
        Map<String, Object> actual = getInfoFromStringToMap(info);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetInfoFromStringToMapNotEqualsInfo() throws IOException {
        String info = """
                {
                  "Alex": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        Map<String, Object> expected = new HashMap<>();
        expected.put("host", "hexlet.io");
        expected.put("timeout", 50);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);
        Map<String, Object> actual = getInfoFromStringToMap(info);
        assertNotEquals(expected, actual);
    }

    @Test
    public void testMergeMapsToListValid() {
        List<String> expectedList = Arrays.asList("- key1=value1", "  key2=value2", "+ key3=value3");
        List<String> actualList = mergeMapsToList(MAP_1, MAP_2);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testAddDifferenceSignToResultSameValueInBothMaps() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key2", "value2");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("  key2=value2", result);
    }

    @Test
    public void testAddDifferenceSignToResultKeyOnlyInMap1() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key1", "value1");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("- key1=value1", result);
    }

    @Test
    public void testAddDifferenceSignToResultKeyOnlyInMap2() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key3", "value3");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("+ key3=value3", result);
    }

    @Test
    public void testAddDifferenceSignToResultKeyNotPresentInBothMaps() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key4", "value4");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("  key4=value4", result);
    }

}
