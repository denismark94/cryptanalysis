package freqan;

import indexes.Vigenere;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Task4 {

    public static String pt_file = "iofiles/task_4/pt.txt";
    public static String ct_file = "iofiles/task_4/ct.txt";
    public static String key_file = "iofiles/task_4/key.txt";
    public static String alphabet_file = "iofiles/task_4/alphabet.txt";
    public static String sample_file = "iofiles/task_4/sample.txt";
    public static String l_freqs_file = "iofiles/task_4/l_freqs.txt";
    public static String w_freqs_file = "iofiles/task_4/w_freqs.txt";


    public static void main(String[] args) throws FileNotFoundException {
      /*  Vigenere crypter = new Vigenere(alphabet_file,pt_file,ct_file);
        String key = Vigenere.readFromFile(key_file);
        crypter.decrypt(ct_file,key);*/
        Analyzer fa = new Analyzer(sample_file);
//        HashMap<Character,Double> letfreq = fa.getLettersFreq(freqs_file);
//        System.out.println(letfreq.toString());
        HashMap<String,Double> wordfreq = fa.getWordsFreq(w_freqs_file);
        System.out.println(wordfreq.toString());
    }
}
