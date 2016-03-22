package kasiski;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Denis on 13.03.2016.
 */
public class KeyBruteforce {
    int keyLength = 0;
    String ctfile = "iofiles/ct.txt";
    String outfile = "iofiles/hacked.txt";
    String gentdkeys = "iofiles/gkeys.txt";
    static int[] key;

    public KeyBruteforce(int keyLength, String ctfile, String outfile, String gkeysfile) {
        this.keyLength = keyLength;
        this.ctfile = ctfile;
        this.outfile = outfile;
        this.gentdkeys = gkeysfile;
        key = new int[keyLength];
        for (int i = 0; i < keyLength; i++)
            key[i] = i;
    }

    public void brute() throws FileNotFoundException, UnsupportedEncodingException {
        SimplePermutation decrypter = new SimplePermutation(ctfile,outfile);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(gentdkeys, false), "UTF-8"));
        int[]seq = new int[keyLength];
        Arrays.fill(seq,0);
        int[]res = sub(seq,key);
        int iterator = 1;
        writer.write("Key[" + iterator++ + "]: " + Arrays.toString(res)+"\n");
        writer.flush();
        decrypter.setKey(res);
        decrypter.decrypt();
        decrypter.rewrite_output = false;
        int ptr = keyLength - 1;
        while(ptr > 0) {
            ptr = keyLength - 1;
            while (seq[ptr] == ptr) {
                seq[ptr] = 0;
                ptr--;
                if (ptr < 0)
                    break;
            }
            if (ptr < 0)
                break;
            seq[ptr]++;
            res = sub(seq,key);
            writer.write("Key[" + iterator++ + "]: " + Arrays.toString(res)+"\n");
            writer.flush();
            decrypter.setKey(res);
            decrypter.decrypt();
        }
        writer.close();
    }

    public int[] sub(int[]seq, int[]key) {
        int[]copy = Arrays.copyOf(key,keyLength);
        int tmp;
        for (int i = copy.length - 1; i >= 0; i--) {
            tmp = copy[i];
            copy[i] = copy[seq[i]];
            copy[seq[i]] = tmp;
        }
        return copy;
    }

}
