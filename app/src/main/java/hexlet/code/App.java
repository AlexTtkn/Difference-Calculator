package hexlet.code;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff",
        mixinStandardHelpOptions = true,
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Object> {
    @CommandLine.Option(names = {"-f", "--format"},
            required = true,
            defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]")
    private String format;

    @CommandLine.Parameters(paramLabel = "filepath1",
            description = "path to first file")
    private Path path1;

    @CommandLine.Parameters(paramLabel = "filepath2",
            description = "path to second file")
    private Path path2;

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }

    @Override
    public Object call() {
        String result;
        try {
            Path p1 = path1.toAbsolutePath().normalize();
            Path p2 = path2.toAbsolutePath().normalize();
            result = Differ.generate(p1, p2, format);
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
