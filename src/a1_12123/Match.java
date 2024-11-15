package engine;

public class Match implements Comparable<Match> {
    private final int freq;
    private final int firstIndex;
    private final Doc doc;
    private final Word word;


    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.doc = d;
        this.word = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }


    public int getFirstIndex() {
        return this.firstIndex;
    }



    public Doc getDoc() {
        return this.doc;
    }

    public int getFreq() {
        return this.freq;
    }


    @Override
    public int compareTo(Match o) {
        if (this.firstIndex == o.firstIndex) {
            return 0;
        }


        return this.firstIndex < o.firstIndex ? -1 : 1;
    }


    public Word getWord() {
        return this.word;
    }


}

