package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

class DifferTest {
    private static String testFile1 = "testFile1.json";
    private static String testFile2 = "testFile2.json";
    private static String testFile3 = "testFile3.yml";
    private static String testFile4 = "testFile4.yml";
    private static String testFile5 = "testFile5.json";
    private static String testFile6 = "testFile6.json";
    private static final String WRONG_PATH = "wrongPath";
    private static final String ROOT_FOR_FIXTURES = "src/test/resources/fixtures/";
    private static final String ROOT_FOR_TESTFILES = "src/test/resources/";


    private static String jsonWithInnerStructureStylish = "expected_json_with_inner_structure_stylish";
    private static String jsonWithInnerStructurePlain = "expected_json_with_inner_structure_plain";
    private static String jsonYmlStylish = "expected_json_yml_stylish";
    private static String jsonYmlPlain = "expected_json_yml_plain";
    private static String jsonYmlJson = "expected_json_yml_json";
    private static String stringOutOfPath = "expected_string_out_of_path";


    private static String getInfoFromFixture(String path) throws IOException {
        return Files.readString(Paths.get(path)).replaceAll("\\r\\n", "\n");
    }

    private static String createPath(String path, String fileName) {
        return path + fileName;
    }

    @BeforeAll
    public static void beforeTest() {
        testFile1 = createPath(ROOT_FOR_TESTFILES, testFile1);
        testFile2 = createPath(ROOT_FOR_TESTFILES, testFile2);
        testFile3 = createPath(ROOT_FOR_TESTFILES, testFile3);
        testFile4 = createPath(ROOT_FOR_TESTFILES, testFile4);
        testFile5 = createPath(ROOT_FOR_TESTFILES, testFile5);
        testFile6 = createPath(ROOT_FOR_TESTFILES, testFile6);

        jsonWithInnerStructureStylish = createPath(ROOT_FOR_FIXTURES, jsonWithInnerStructureStylish);
        jsonWithInnerStructurePlain = createPath(ROOT_FOR_FIXTURES, jsonWithInnerStructurePlain);
        jsonYmlStylish = createPath(ROOT_FOR_FIXTURES, jsonYmlStylish);
        jsonYmlPlain = createPath(ROOT_FOR_FIXTURES, jsonYmlPlain);
        jsonYmlJson = createPath(ROOT_FOR_FIXTURES, jsonYmlJson);
        stringOutOfPath = createPath(ROOT_FOR_FIXTURES, stringOutOfPath);
    }

    @Test
    void testGenerateValidJson() throws IOException {
        String expected = getInfoFromFixture(jsonYmlStylish);
        String actual = Differ.generate(testFile1, testFile2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYml() throws IOException {
        String expected = getInfoFromFixture(jsonYmlStylish);
        String actual = Differ.generate(testFile3, testFile4);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidJsonStylish() throws IOException {
        String expected = getInfoFromFixture(jsonWithInnerStructureStylish);
        String actual = Differ.generate(testFile5, testFile6, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYmlStylish() throws IOException {
        String expected = getInfoFromFixture(jsonYmlStylish);
        String actual = Differ.generate(testFile3, testFile4, "stylish");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidJsonPlain() throws IOException {
        String expected = getInfoFromFixture(jsonWithInnerStructurePlain);
        String actual = Differ.generate(testFile5, testFile6, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYmlPlain() throws IOException {
        String expected = getInfoFromFixture(jsonYmlPlain);
        String actual = Differ.generate(testFile3, testFile4, "plain");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidJsonJson() throws IOException {
        String expected = getInfoFromFixture(jsonYmlJson);
        String actual = Differ.generate(testFile1, testFile2, "json");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateValidYmlJson() throws IOException {
        String expected = getInfoFromFixture(jsonYmlJson);
        String actual = Differ.generate(testFile3, testFile4, "json");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateInvalidPath() {
        Assertions.assertThrows(NoSuchFileException.class, () ->
                Differ.generate(WRONG_PATH, WRONG_PATH, "stylish"));
    }

    @Test
    void testGetStringOutOfPathValid() throws IOException {
        String expected = getInfoFromFixture(stringOutOfPath);
        String actual = Differ.getStringOutOfPath(Path.of(testFile1));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetStringOutOfPathNotEqualsInfo() throws IOException {
        String expected = getInfoFromFixture(stringOutOfPath);
        String actual = Differ.getStringOutOfPath(Path.of(testFile2));
        Assertions.assertNotEquals(expected, actual);
    }
}
