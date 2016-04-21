package bigrams;

import freqan.Vigenere;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Denis on 21.04.2016.
 */
public class MainClass {
    public static void main(String[] args) throws IOException {
        PTAnalyzer analyzer = new PTAnalyzer("iofiles/task_3/pt.txt");
        ArrayList<String> fb = analyzer.getForbiddenBigrams();
        String out = fb.get(0);
        String alph = analyzer.alph;
        for (int i = 1; i < fb.size(); i++)
            out+=" "+fb.get(i);
        Vigenere.writeToFile("iofiles/task_3/fb.txt",out);
        Vigenere.writeToFile("iofiles/task_3/alphabet.txt",alph);
        FBChecker checker = new FBChecker(fb);
        checker.getReferenceTable(5,"iofiles/task_3/ct.txt","iofiles/task_3/table.txt");
        System.out.println("Done!");

    }
}
