package hexlet.code;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Object> {
    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }

    @Override
    public Object call() {
        return null;
    }
}