package kasiski;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class MainClass {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String pt = "iofiles/pt.txt",
                ct = "iofiles/ct.txt",
                decrypted = "iofiles/decrypted.txt",
                brute_results = "iofiles/hacked.txt",
                keyFile = "iofiles/key.txt";
        encrypt(5, pt, ct, keyFile);

//        decrypt(ct,decrypted,key);
        int outKeyLength = getKeyLength(ct);
        bruteforce(outKeyLength, ct, brute_results);


    }

    public static void encrypt(int keyLength, String infile, String outfile, String keyFile) throws FileNotFoundException {
        kasiski.SimplePermutation encrypter = new kasiski.SimplePermutation(infile, outfile);
        encrypter.generateKey(keyLength);
        int[] key = encrypter.getKey();
        System.out.println("Key : " + Arrays.toString(key));
        PrintWriter pw = new PrintWriter(new File(keyFile));
        pw.write(Arrays.toString(key));
        pw.close();
        encrypter.encrypt();
    }

    public static void decrypt(String infile, String outfile, int[] key) throws FileNotFoundException, UnsupportedEncodingException {
        kasiski.SimplePermutation decrypter = new kasiski.SimplePermutation(infile, outfile);
        decrypter.setKey(key);
        decrypter.decrypt();
    }

    public static int getKeyLength(String ctfile) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(ctfile));
        String ct = "";
        while (scan.hasNext())
            ct += scan.nextLine();
        KasiskiTest test = new KasiskiTest(ct);
        int keyLength = test.getKeyLength();
        System.out.println("Key Length = " + keyLength);
        return keyLength;
    }

    public static void bruteforce(int keyLength, String ctfile, String output) throws FileNotFoundException, UnsupportedEncodingException {
        KeyBruteforce brute = new KeyBruteforce(keyLength, ctfile, output);
        brute.brute();
    }

}
