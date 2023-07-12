package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;
import java.util.Map;

public class Json {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String addFormatToResult(List<Map<String, Object>> list) throws JsonProcessingException {
        StringBuilder result = new StringBuilder();
        ObjectWriter writer = MAPPER.writerWithDefaultPrettyPrinter();
        return result.append(writer.writeValueAsString(list)).append("\n").toString().trim();
    }
}
