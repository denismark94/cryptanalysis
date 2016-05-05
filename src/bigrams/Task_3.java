package bigrams;

import indexes.Vigenere;
import kasiski.SimplePermutation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Task_3 {
    public static String sample = "iofiles/task_3/sample.txt";
    public static String fbout = "iofiles/task_3/fb.txt";
    public static String alphabet = "iofiles/task_3/alphabet.txt";
    public static String ct = "iofiles/task_3/ct.txt";
    public static String tab = "iofiles/task_3/table.txt";
    public static String forestOut = "iofiles/task_3/tree.txt";
    public static String results = "iofiles/task_3/decrypted.txt";
    public static int keyLength = 6;

    public static void main(String[] args) throws IOException {

        System.out.println("Counting forbidden bits...");
        ArrayList<String> fb  = bigrams(sample, fbout, alphabet);
        System.out.print("Done\nCounting reference table....");
        FBChecker checker = new FBChecker(fb);
        boolean[][] table = checker.getReferenceTable(keyLength, ct, tab);
        System.out.println("Done\nCounting forest...");
        ForestGenerator gen = new ForestGenerator(table);
        ArrayList<String>[] forest = gen.buildForest();
        forestToFile(forest);
        System.out.print("Done\nGenerating keys...");
        ArrayList<Integer[]> keys = getKeys(forest, keyLength);
        System.out.println("Done\nDecrypting...");
        SimplePermutation encrypter = new kasiski.SimplePermutation(ct, results);
        encrypter.rewrite_output = false;
        Integer[] key;
        for (int i = 0; i < keys.size(); i++) {
            key = keys.get(i).clone();
            encrypter.setKey(key);
            encrypter.encrypt();
        }
        System.out.print("Done");
    }

    public static void forestToFile(ArrayList<String>[] forest) throws FileNotFoundException {
        String output = "";
        for (int i = 0; i < forest.length; i++) {
            output += "-------\n";
            for (int j = 0; j < forest[i].size(); j++) {
                output += forest[i].get(j) + "\n";
            }
        }
        Vigenere.writeToFile(forestOut, output);
    }

    public static ArrayList<String> bigrams(String sample, String bigrams, String alphabet) throws FileNotFoundException {
        PTAnalyzer analyzer = new PTAnalyzer(sample);
        ArrayList<String> fb = analyzer.getForbiddenBigrams();
        String out = fb.get(0);
        String alph = analyzer.alph;
        for (int i = 1; i < fb.size(); i++)
            out += " " + fb.get(i);
        Vigenere.writeToFile(bigrams, out);
        Vigenere.writeToFile(alphabet, alph);
        return fb;
    }

    public static ArrayList<Integer[]> getKeys(ArrayList<String>[] forest, int keyLength) {
        String[] tmp;
        Integer[] key = new Integer[keyLength];
        ArrayList<Integer[]> keys = new ArrayList<>();
        for (int i = 0; i < forest.length; i++) {
            for (int j = 0; j < forest[i].size(); j++) {
                String path = forest[i].get(j);
                tmp = path.split(",");
                if (tmp.length == keyLength) {
                    for (int k = 0; k < tmp.length; k++)
                        key[k] = Integer.parseInt(tmp[k]);
                    keys.add(key.clone());
                }
            }
        }
        return keys;
    }

}
