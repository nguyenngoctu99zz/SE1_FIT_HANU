package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Engine {
    private final List<Doc> docs;

    public Engine() {
        this.docs = new ArrayList<>();
    }


    public int loadDocs(String dirname) {
        if (!dirname.isEmpty()) {
            try (Stream<Path> pathsFiles = Files.list(Paths.get(dirname))) {
                pathsFiles.filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".txt"))
                        .sorted()
                        .forEach(path ->
                        {
                            try {
                                String content = Files.readString(path);
                                this.docs.add(new Doc(content));
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                        });
                return this.docs.size();
            } catch (IOException e) {
                System.out.println("Error during loading: " + e.getMessage());
            }
        }
        return 0;
    }

    public Doc[] getDocs() {
        return this.docs.toArray(new Doc[0]);
    }


    public List<Result> search(Query q) {
        return this.docs.stream()
                .map(doc -> new Result(doc, q.matchAgainst(doc)))
                .filter(result -> !result.getMatches().isEmpty())
                .sorted().collect(Collectors.toList());
    }


    public String htmlResult(List<Result> results) {
        StringBuilder strBuilder;

        strBuilder = new StringBuilder();
        for (Result resultR : results) {
            strBuilder.append(resultR.htmlHighlight());
        } return strBuilder.toString();
    }
}
