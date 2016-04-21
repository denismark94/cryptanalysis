package bigrams;

import freqan.Vigenere;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Denis on 21.04.2016.
 */
public class PTAnalyzer {
    public String alph = "";
    public String text;

    public PTAnalyzer(String ptFile) throws FileNotFoundException {
        this.text = Vigenere.readFromFile(ptFile).toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            if (!this.alph.contains(text.charAt(i) + ""))
                if (Character.isLetter(text.charAt(i)))
                    this.alph += text.charAt(i);
        }
    }

    public ArrayList<String> getForbiddenBigrams() {
        String buf;
        ArrayList<String> fb = new ArrayList<>();
        for (int i = 0; i < alph.length(); i++) {
            for (int j = 0; j < alph.length(); j++) {
                buf = "" + alph.charAt(i) + alph.charAt(j);
                if (!text.contains(buf))
                    fb.add(buf);
            }
        }
        return fb;
    }
}
