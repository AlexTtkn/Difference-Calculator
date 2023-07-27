package hexlet.code;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Object> {
    @CommandLine.Option(names = {"-f", "--format"},
            required = true,
            defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]")
    private String format;

    @CommandLine.Parameters(paramLabel = "filepath1",
            description = "path to first file")
    private String path1;

    @CommandLine.Parameters(paramLabel = "filepath2",
            description = "path to second file")
    private String path2;

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }

    @Override
    public Object call() {
        String result;
        try {
            result = format.isEmpty() ? Differ.generate(path1, path2) : Differ.generate(path1, path2, format);
            System.out.println(result);
            return result;
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            return new RuntimeException(e);
        }
    }
}
