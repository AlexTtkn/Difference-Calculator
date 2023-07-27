package hexlet.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

class DifferTest {
    private static final String WRONG_PATH = "wrongPath";
    private static final String ROOT_FOR_TESTFILES = "src/test/resources/";
    private static final String ROOT_FOR_FIXTURES = "src/test/resources/fixtures/";

    private static String getInfoFromFixture(String path) throws IOException {
        return Files.readString(Paths.get(path)).replaceAll("\\r\\n", "\n");
    }

    private static String createPath(String path, String fileName) {
        return path + fileName;
    }

    @ParameterizedTest
    @CsvSource({
        "expected_json_yml_with_inner_structure_stylish, testFile1.json, testFile2.json, stylish",
        "expected_json_yml_with_inner_structure_stylish, testFile3.yml, testFile4.yml, stylish",
        "expected_json_yml_with_inner_structure_plain, testFile1.json, testFile2.json, plain",
        "expected_json_yml_with_inner_structure_plain, testFile3.yml, testFile4.yml, plain",
        "expected_json_yml_with_inner_structure_json, testFile1.json, testFile2.json, json",
        "expected_json_yml_with_inner_structure_json, testFile3.yml, testFile4.yml, json"
    })
    void globalTest(String fixture, String testFile1, String testFile2, String format) throws IOException {
        String path = createPath(ROOT_FOR_FIXTURES, fixture);
        String firstTestFile = createPath(ROOT_FOR_TESTFILES, testFile1);
        String secondTestFile = createPath(ROOT_FOR_TESTFILES, testFile2);

        String expected = getInfoFromFixture(path);
        String actual = Differ.generate(firstTestFile, secondTestFile, format);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
        "expected_json_yml_with_inner_structure_stylish, testFile1.json, testFile2.json",
        "expected_json_yml_with_inner_structure_stylish, testFile3.yml, testFile4.yml"
    })
    void globalTest2(String fixture, String testFile1, String testFile2) throws IOException {
        String path = createPath(ROOT_FOR_FIXTURES, fixture);
        String firstTestFile = createPath(ROOT_FOR_TESTFILES, testFile1);
        String secondTestFile = createPath(ROOT_FOR_TESTFILES, testFile2);

        String expected = getInfoFromFixture(path);
        String actual = Differ.generate(firstTestFile, secondTestFile);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGenerateInvalidPath() {
        Assertions.assertThrows(NoSuchFileException.class, () ->
                Differ.generate(WRONG_PATH, WRONG_PATH, "stylish"));
    }
}
