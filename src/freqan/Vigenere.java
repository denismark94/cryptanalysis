package freqan;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Denis on 10.04.2016.
 */
public class Vigenere {
    public  String alph_file;
    public String key_file;
    public String ct_file;

    public Vigenere(String alph_file, String ct_file) {
        this.alph_file = alph_file;
        this.ct_file = ct_file;
    }

    public String crypt(String pt_file, String key) throws FileNotFoundException {
        String alph = readFromFile(alph_file);
        String pt = readFromFile(pt_file);
        char[] raw = pt.toLowerCase().toCharArray();
        String ct = "";
        for (int i = 0; i < raw.length / key.length(); i++)
            for (int j = 0; j < key.length(); j++)
                ct += alph.charAt((alph.indexOf(raw[key.length()*i + j]) + alph.indexOf(key.charAt(j))) % alph.length());
        for (int i = ct.length(); i < raw.length; i++)
            ct += alph.charAt((alph.indexOf(raw[i]) + alph.indexOf(key.charAt(i%key.length()))) % alph.length());
        writeToFile(ct_file,ct);
        return ct;
    }

    public static String readFromFile(String path) throws FileNotFoundException {
        String res = "";
        Scanner in = new Scanner(new File(path));
        while(in.hasNext())
            res+=in.nextLine().toLowerCase();
        in.close();
        return res;
    }

    public void writeToFile(String path, String ct) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path)));
        writer.write(ct);
        writer.flush();
        writer.close();
    }
}
