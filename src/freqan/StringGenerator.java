package freqan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by markindi on 31.03.2016.
 */
public class StringGenerator {

    public static char[] abc = {};
    public StringGenerator(char[] abc) {
        this.abc = abc;
    }

    public StringGenerator(String alphabetFile) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(alphabetFile));
        String raw = "";
        while(scan.hasNext())
            raw+=scan.nextLine();
        abc = raw.replaceAll(" ","").toCharArray();
    }

    public String generate(int length) {
        String out = "";
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < length; i++) {
            out+=abc[random.nextInt(abc.length)];
        }
        return out;
    }

}
