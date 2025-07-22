package chapter03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ExecuteAround {
    private static final String FILE = "resources/chapter03/data.txt";

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FILE);

        // test
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(System.out::println);
        }

        String result = processFileLimited();
        System.out.println("---");

        String oneLine = processFile(b -> b.readLine());
        System.out.println(oneLine);

        String twoLines = processFile(b -> b.readLine() + b.readLine());
        System.out.println(twoLines);
    }

    private static String processFileLimited() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return p.process(br);
        }
    }

    public interface BufferedReaderProcessor {
        String process(BufferedReader br) throws IOException;
    }
}
