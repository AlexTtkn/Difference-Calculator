package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;


import static hexlet.code.Differ.*;
import static org.junit.jupiter.api.Assertions.*;

class DifferTest {
    private final static Path resourceDirectory1 = Path.of("src/test/resources/testFile1.json").toAbsolutePath().normalize();
    private final static Path resourceDirectory2 = Path.of("src/test/resources/testFile2.json").toAbsolutePath().normalize();
    private final static Path wrongResourceDirectory = Path.of("wrongPath");
    private static final Map<String, Object> MAP_1 = Map.of("key1", "value1", "key2", "value2");
    private static final Map<String, Object> MAP_2 = Map.of("key2", "value2", "key3", "value3");

    @Test
    public void testGenerate_Valid() throws IOException {
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

        String actual = generate(resourceDirectory1, resourceDirectory2, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerate_InvalidPath() {
        assertThrows(NoSuchFileException.class, () ->
                generate(resourceDirectory2, wrongResourceDirectory, null));
    }

    @Test
    public void testGetStringOutOfPath_Valid() throws IOException {
        String expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        String actual = getStringOutOfPath(resourceDirectory1);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetStringOutOfPath_NotEqualsInfo() throws IOException {
        String expected = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        String actual = getStringOutOfPath(resourceDirectory2);
        assertNotEquals(expected, actual);
    }

    @Test
    public void testGetInfoFromStringToMap_Valid() throws IOException {
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
    public void testGetInfoFromStringToMap_NotEqualsInfo() throws IOException {
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
    public void testMergeMapsToList_Valid() {
        List<String> expectedList = Arrays.asList("- key1=value1", "  key2=value2", "+ key3=value3");
        List<String> actualList = mergeMapsToList(MAP_1, MAP_2);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testAddDifferenceSignToResult_sameValueInBothMaps() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key2", "value2");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("  key2=value2", result);
    }

    @Test
    public void testAddDifferenceSignToResult_keyOnlyInMap1() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key1", "value1");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("- key1=value1", result);
    }

    @Test
    public void testAddDifferenceSignToResult_keyOnlyInMap2() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key3", "value3");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("+ key3=value3", result);
    }

    @Test
    public void testAddDifferenceSignToResult_keyNotPresentInBothMaps() {
        Map.Entry<String, Object> entry = new AbstractMap.SimpleEntry<>("key4", "value4");
        String result = Differ.addSingToResult(entry, MAP_1, MAP_2);
        assertEquals("  key4=value4", result);
    }

}
