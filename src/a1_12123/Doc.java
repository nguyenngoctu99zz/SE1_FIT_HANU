package engine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Doc {

    private final List<Word> title;
    private final List<Word> body;


    public Doc(String content) {


        String[] lineSplit = content.split("\\R");


        this.title = Arrays.stream(lineSplit.length > 0 ? lineSplit[0].split(" ") : new String[0])
                .map(Word::createWord)
                .collect(Collectors.toList());

        this.body = Arrays.stream(lineSplit.length > 1 ? lineSplit[1].split(" ") : new String[0])
                .map(Word::createWord)
                .collect(Collectors.toList());
    }



    public List<Word> getTitle() {
        return this.title;
    }

    public List<Word> getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }

        Doc doc = (Doc) o;
        return Objects.equals(this.title, doc.title) && Objects.equals(this.body, doc.body);
    }
}
