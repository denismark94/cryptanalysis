package freqan;

import indexes.Vigenere;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by admin on 05.05.2016.
 */
public class Analyzer {
    public String sample;

    public Analyzer(String sample_file) throws FileNotFoundException {
        this.sample = Vigenere.readFromFile(sample_file).toLowerCase().replaceAll("[^a-z]"," ").replaceAll(" {2,}"," ");
    }

    public HashMap<Character, Double> getLettersFreq(String out_file) {
        HashMap<Character, Double> freqs = new HashMap<Character, Double>();
        String in = sample.replaceAll(" ", "");
        double cnt;
        for (int i = 0; i < in.length(); i++)
            if (freqs.containsKey(in.charAt(i))) {
                cnt = freqs.get(in.charAt(i));
                freqs.replace(in.charAt(i), cnt, cnt + 1);
            } else freqs.put(in.charAt(i), 1.0);
        for (Character i : freqs.keySet()) {
            cnt = freqs.get(i);
            freqs.replace(i, cnt, cnt / in.length());
        }
        return freqs;
    }

    public HashMap<String, Double> getWordsFreq(String out_file) {
        HashMap<String, Double> freqs = new HashMap<>();
        String[] words = sample.split(" ");
        Double cnt;
        for (int i = 0; i < words.length; i++) {
            if (freqs.containsKey(words[i])) {
                cnt = freqs.get(words[i]);
                freqs.replace(words[i], cnt, cnt + 1);
            } else freqs.put(words[i], 1.0);
        }
        for (String i : freqs.keySet()) {
            cnt = freqs.get(i);
            freqs.replace(i, cnt, cnt / words.length);
        }
        return freqs;
    }
}
