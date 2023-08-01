package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.TreeMap;

public class Parser {
    public static TreeMap<String, Object> getInfoAsMap(String info, String format) throws IOException {
        ObjectMapper jsonMapper = new JsonMapper(new JsonFactory());
        ObjectMapper yamlMapper = new YAMLMapper(new YAMLFactory());
        return switch (format) {
            case "yml" -> yamlMapper.readValue(info, new TypeReference<>() {
            });
            case "json" -> jsonMapper.readValue(info, new TypeReference<>() {
            });
            default -> throw new RuntimeException("Wrong format");
        };
    }
}
