package engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Word {
    public static Set<String> stopWords;

    private final String prefixWrd, textWrd, suffixWrd;
    private final boolean valid;


    public Word(String prefix, String text, String suffix, boolean valid) {
        this.prefixWrd = prefix;
        this.textWrd = text;
        this.suffixWrd = suffix;
        this.valid = valid;
    }




    
    public static Word createWord(String rawText) {
        String txtWords = rawText.replaceAll("[^\\p{IsAlphabetic}-']", "");

        String[] partsOfText = rawText.split(txtWords, 2);

        String partOfPre, partOfSuf; boolean validCheck;

        if (partsOfText.length <= 1) {
            partOfSuf = "";
        } else {
            partOfSuf = partsOfText[1];
        }

        if (partsOfText.length != 0) {
            partOfPre = partsOfText[0];
        } else {
            partOfPre = rawText;
        }


        boolean noLetterDigitSuf = partOfSuf.chars().noneMatch(Character::isLetterOrDigit);
        boolean noLetterDigitPre = partOfPre.chars().noneMatch(Character::isLetterOrDigit);
        boolean hasTheLetter = txtWords.chars().anyMatch(Character::isLetter);
        boolean emptyCheck = !txtWords.isEmpty();


        validCheck = hasTheLetter && noLetterDigitPre && noLetterDigitSuf && emptyCheck;


        switch (txtWords.length() >= 2 ? txtWords.substring(txtWords.length() - 2) : "") {
            case "'s":
                partOfSuf = "'s" + partOfSuf;
                txtWords = txtWords.substring(0, txtWords.length() - 2);
                break;
            case "'d":
                partOfSuf = "'d" + partOfSuf;
                txtWords = txtWords.substring(0, txtWords.length() - 2);
                break;
            default:
                break;
        }



        if (!validCheck) {
            partOfPre = ""; txtWords = rawText; partOfSuf = "";
        }

        return new Word(partOfPre, txtWords, partOfSuf, validCheck);
    }


    public static boolean loadStopWords(String fileName) {
        try {
            Set<String> stopWordsSet = new HashSet<>(Files.readAllLines(Paths.get(fileName)));
            Word.stopWords = stopWordsSet;
            return true;
        } catch (IOException e) {
            return false;
        }
    }



    public boolean isKeyword() {
        return this.valid &&
                (Word.stopWords == null || !Word.stopWords.contains(this.textWrd.toLowerCase()));
    }

    public String getPrefix() {
        return this.prefixWrd;
    }


    public String getSuffix() {
        return this.suffixWrd;
    }


    public String getText() {
        return this.textWrd;
    }


    public boolean isValid() {
        return this.valid;
    }


    @Override
    public int hashCode() {
        return this.textWrd.toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word word)) return false;
        return textWrd.equalsIgnoreCase(word.textWrd);
    }


    @Override
    public String toString() {
        return this.prefixWrd + this.textWrd + this.suffixWrd;
    }
}
