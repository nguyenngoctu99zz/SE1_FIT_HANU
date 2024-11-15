package engine;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Result implements Comparable<Result> {

    private final List<Match> matches;
    private final int matchCount;
    private final int totalFrequency;
    private final double averageFirstIndex;
    private final Doc doc;


    int totalFreque = 0;
    double sumFirstIdx = 0;


    public Result(Doc d, List<Match> matches) {
        this.doc = d;
        this.matches = matches;
        this.matchCount = matches.size();


        for (Match ma : matches) {
            totalFreque += ma.getFreq();
            sumFirstIdx += ma.getFirstIndex();
        }

        this.totalFrequency = totalFreque;
        this.averageFirstIndex = matches.isEmpty() ? 0.0 : sumFirstIdx / matches.size();

    }


    public int getMatchCount() {
        return this.matchCount;
    }


    public Doc getDoc() {
        return this.doc;
    }


    public List<Match> getMatches() {
        return this.matches;
    }


    public int getTotalFrequency() {
        return this.totalFrequency;
    }


    public double getAverageFirstIndex() {
        return this.averageFirstIndex;
    }


    public String htmlHighlight() {

        Set<Word> wordMatch = new HashSet<>();
        this.matches.forEach(match -> wordMatch.add(match.getWord()));


        String highlightedTittleAndBody = "<h3>" +
                this.doc.getTitle().stream().map(word -> {
                    if (!wordMatch.contains(word)) {
                        return word.toString();
                    }
                    return word.getPrefix() + "<u>" + word.getText() + "</u>" + word.getSuffix();

                }).collect(Collectors.joining(" ")) +
                "</h3><p>" +
                this.doc.getBody().stream().map(word -> {
                    if (!wordMatch.contains(word)) {
                        return word.toString();
                    }
                    return word.getPrefix() + "<b>" + word.getText() + "</b>" + word.getSuffix();
                }).collect(Collectors.joining(" ")) + "</p>";


        return highlightedTittleAndBody;

    }


    @Override
    public int compareTo(Result o) {
        int matchCountComparison = Integer.compare(o.matchCount, this.matchCount);
        if (matchCountComparison != 0) return matchCountComparison;

        int totalFrequencyComparison = Integer.compare(o.totalFrequency, this.totalFrequency);
        return totalFrequencyComparison != 0 ? totalFrequencyComparison : Double.compare(this.averageFirstIndex, o.averageFirstIndex);
    }

}
