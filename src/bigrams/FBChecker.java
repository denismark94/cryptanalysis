package bigrams;

import indexes.Vigenere;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Denis on 21.04.2016.
 */
public class FBChecker {
    String ct = "";
    ArrayList<String> fbigrams;

    public FBChecker(ArrayList<String> fbigrams) {
        this.fbigrams = fbigrams;
    }

    public boolean[][] getReferenceTable(int bufLength, String ctFile, String outFile) throws IOException {
        String ct = Vigenere.readFromFile(ctFile).toLowerCase();
        boolean[][] res = new boolean[bufLength][bufLength];
        for (int i = 0; i < bufLength; i++)
            res[i][i] = true;
        String[] splct = new String[ct.length() / bufLength];
        for (int i = 0; i < ct.length(); i += bufLength)
            splct[i / bufLength] = ct.substring(i, i + bufLength);
        for (int i = 0; i < bufLength; i++)
            for (int j = 0; j < bufLength; j++) {
                if (i == j)
                    continue;
                for (int k = 0; k < splct.length; k++)
                    if (fbigrams.contains("" + splct[k].charAt(i) + splct[k].charAt(j))) {
                        res[i][j] = true;
                        break;
                    }
            }

        FileWriter fw = new FileWriter(outFile);
        String mask = "\\";
        for (int i = 0; i < bufLength; i++)
            mask+="\t"+i;
        fw.write(mask+"\r\n");
        for (int i = 0; i < bufLength; i++) {
            mask = ""+i;
            for (int j = 0; j < bufLength; j++) {
                mask+="\t";
                if(res[i][j])
                    mask+="x";
            }
            fw.write(mask+"\r\n");
        }
        fw.flush();
        fw.close();
        return res;
    }
}
