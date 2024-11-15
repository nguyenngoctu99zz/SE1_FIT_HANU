package engine;

import java.util.*;

public class Query {

    private final List<Word> keywords;


    public Query(String searchPhrase) {
        this.keywords = new ArrayList<>();
        for (String words : searchPhrase.split(" ")) {
            Word wrd = Word.createWord(words);
            if (wrd.isKeyword())
            {
                this.keywords.add(wrd);
            }
        }
    }


    public List<Word> getKeywords() {
        return this.keywords;
    }


    public List<Match> matchAgainst(Doc d) {

        List<Word> wrdDocs;
        List<Match> theMatching = new ArrayList<>();


        wrdDocs = new ArrayList<>(d.getTitle()); wrdDocs.addAll(d.getBody());

        for (Word keyword : keywords) {
            int theFrequency = 0;

            for (Word docWord : wrdDocs) {
                if (docWord.equals(keyword)) {
                    theFrequency++;
                }
            }

            if (theFrequency > 0) {
                theMatching.add(
                        new Match(d, keyword, theFrequency, wrdDocs.indexOf(keyword)));
            }
        }
        theMatching.sort(null);
        return theMatching;
    }


}
