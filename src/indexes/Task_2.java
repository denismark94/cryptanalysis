package indexes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Task_2 {
    public static String en_alph_file = "iofiles/task_2/en_alph.txt";
    public static String mixed_alph_file = "iofiles/task_2/mixed_alph.txt";
    public static String pt_file = "iofiles/task_2/pt.txt";
    public static String ct_file = "iofiles/task_2/ct.txt";
    public static String result_table_1 = "iofiles/task_2/res_2.1.txt";
    public static String result_table_2 = "iofiles/task_2/res_2.2.txt";
    public static String result_table_3 = "iofiles/task_2/res_2.3.txt";
    public static String ru_sample = "iofiles/task_2/ru_sample.txt";
    public static String en_sample = "iofiles/task_2/en_sample.txt";
    public static String key5_file = "iofiles/task_2/key5.txt";
    public static String key7_file = "iofiles/task_2/key7.txt";

    public static int stringLength = 250;

    public static void main(String[] args) throws IOException {

        FileWriter res1 = new FileWriter(result_table_1);
        FileWriter res2 = new FileWriter(result_table_2);
        FileWriter res3 = new FileWriter(result_table_3);
        res1.write(String.format("%20s %20s %20s\r\n", "english", "russian", "random"));
        res2.write(String.format("%20s %20s %20s\r\n", "english", "russian", "random"));
        res3.write(String.format("%20s %20s %20s\r\n", "plain", "key = 5", "key = 7"));
        StringGenerator generator = new StringGenerator(mixed_alph_file);
        String ru1, ru2, en1, en2, rand1, rand2;
        Random random = new Random();
        double[][] di = new double[3][4];
        double[][] ai = new double[3][4];
        double[][] si = new double[3][15];
        System.out.println("Reading russian samples...");
        ArrayList<String> ru_samples = readSamples(ru_sample);
        System.out.println("Done\nReading english samples...");
        ArrayList<String> en_samples = readSamples(en_sample);
        System.out.println("Done\nCounting indexes...");
        for (int j = 0; j < 4; j++) {
            ru1 = ru_samples.get(Math.floorMod(random.nextInt(),4));
            en1 = en_samples.get(Math.floorMod(random.nextInt(),4));
            rand1 = generator.generate(stringLength);

            ru2 = ru_samples.get(Math.floorMod(random.nextInt(),4));
            en2 = en_samples.get(Math.floorMod(random.nextInt(),4));
            rand2 = generator.generate(stringLength);

            di[0][j] = IndexCounter.getMatchIndex(ru1, ru2);
            di[1][j] = IndexCounter.getMatchIndex(en1, en2);
            di[2][j] = IndexCounter.getMatchIndex(rand1, rand2);

            ai[0][j] = IndexCounter.getAvgMatchIndex(ru1, ru2);
            ai[1][j] = IndexCounter.getAvgMatchIndex(en1, en2);
            ai[2][j] = IndexCounter.getAvgMatchIndex(rand1, rand2);

            res1.write(String.format("%20.2f %20.2f %20.2f\r\n", di[0][j], di[1][j], di[2][j]));
            res2.write(String.format("%20.2f %20.2f %20.2f\r\n", ai[0][j], ai[1][j], ai[2][j]));
        }
        System.out.println("Done\nEncrypting plaintext...");
        Vigenere cipher = new Vigenere(en_alph_file, ct_file);
        String ct5 = cipher.crypt(pt_file, Vigenere.readFromFile(key5_file));
        String ct7 = cipher.crypt(pt_file, Vigenere.readFromFile(key7_file));
        String pt = Vigenere.readFromFile(pt_file);
        System.out.println("Done\nCounting indexes for ciphertext");
        for (int i = 0; i < 15; i++) {
            si[0][i] = IndexCounter.getMatchIndex(pt, shift(pt, i + 1));
            si[1][i] = IndexCounter.getMatchIndex(ct5, shift(ct5, i + 1));
            si[2][i] = IndexCounter.getMatchIndex(ct7, shift(ct7, i + 1));
            res3.write(String.format("%20.2f %20.2f %20.2f\r\n", si[0][i], si[1][i], si[2][i]));
        }
        res1.flush();
        res1.close();
        res2.flush();
        res2.close();
        res3.flush();
        res3.close();
        System.out.println("Done");
    }

    public static String shift(String input, int l) throws FileNotFoundException {
        String output = "";
        for (int i = 0; i < input.length(); i++)
            output += input.charAt((i + l) % input.length());
        return output;
    }

    public static ArrayList<String> readSamples(String sample_file) throws FileNotFoundException {
        ArrayList<String> samples = new ArrayList<>();
        Scanner scanner = new Scanner(new File(sample_file));
        while (scanner.hasNext())
            samples.add(scanner.nextLine());
        return samples;
    }
}
