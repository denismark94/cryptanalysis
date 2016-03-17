package kasiski;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Denis on 13.03.2016.
 */
public class KeyBruteforce {
    int keyLength = 0;
    String ctfile = "iofiles/ct.txt";
    String outfile = "iofiles/hacked.txt";
    static int[] key;

    public KeyBruteforce(int keyLength, String ctfile, String outfile) {
        this.keyLength = keyLength;
        this.ctfile = ctfile;
        this.outfile = outfile;
        key = new int[keyLength];
        for (int i = 0; i < keyLength; i++)
            key[i] = i;
    }

    public void brute() throws FileNotFoundException, UnsupportedEncodingException {
        SimplePermutation decrypter = new SimplePermutation(ctfile,outfile);
        int[]seq = new int[keyLength];
        Arrays.fill(seq,0);
        int[]res = sub(seq,key);
        int iterator = 1;
        System.out.println("Key[" + iterator++ + "]: " + Arrays.toString(res));
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
            System.out.println("Key[" + iterator++ + "]: " + Arrays.toString(res));
            decrypter.setKey(res);
            decrypter.decrypt();
        }
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
