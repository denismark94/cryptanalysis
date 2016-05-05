package kasiski;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class Task_1 {
    public static String key_file = "iofiles/task_1/key5.txt";
    public static String pt_file = "iofiles/task_1/pt.txt";
    public static String ct_file = "iofiles/task_1/ct.txt";
    public static String output_file = "iofiles/task_1/output.txt";
    public static String gen_keys_file = "iofiles/task_1/gkeys.txt";
    public static int keyLength = 5;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        genKey(keyLength,key_file);
        encrypt(pt_file,ct_file,key_file);
        decrypt(ct_file,pt_file,key_file);
        getKeyLength(ct_file);
        bruteforce(keyLength,ct_file,output_file,gen_keys_file);
    }

    public static void genKey(int keyLength, String keyFile) throws FileNotFoundException {
        kasiski.SimplePermutation encrypter = new kasiski.SimplePermutation();
        encrypter.generateKey(keyLength);
        int[] key = encrypter.getKey();
        System.out.println("Key : " + Arrays.toString(key));
        PrintWriter pw = new PrintWriter(new File(keyFile));
        pw.write(Arrays.toString(key));
        pw.flush();
        pw.close();
        System.out.print("Key generation: sucess\n");
    }

    public static void encrypt(String infile, String outfile, String keyFile) throws FileNotFoundException {
        kasiski.SimplePermutation encrypter = new kasiski.SimplePermutation(infile, outfile);
        int[] key = readKey(keyFile);
        System.out.println("Key : " + Arrays.toString(key));
        encrypter.setKey(key);
        encrypter.encrypt();
        System.out.print("Encryption: success\n");
    }

    public static void decrypt(String infile, String outfile, String keyFile) throws FileNotFoundException, UnsupportedEncodingException {
        kasiski.SimplePermutation decrypter = new kasiski.SimplePermutation(infile, outfile);
        int[] key = readKey(keyFile);
        System.out.println("Key : " + Arrays.toString(key));
        decrypter.setKey(key);
        decrypter.decrypt();
        System.out.print("Decryption: success\n");
    }

    public static void getKeyLength(String ctfile) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(ctfile));
        String ct = "";
        while (scan.hasNext())
            ct += scan.nextLine();
        KasiskiTest test = new KasiskiTest(ct);
        System.out.println("Test started");
        int keyLength = test.getKeyLength();
        System.out.println("Key Length = " + keyLength);
        System.out.print("Kasiski test: success\n");
    }

    public static void bruteforce(int keyLength, String ctfile, String output, String gkeys) throws FileNotFoundException, UnsupportedEncodingException {
        KeyBruteforce brute = new KeyBruteforce(keyLength, ctfile, output, gkeys);
        brute.brute();
        System.out.print("Bruteforce: success\n");
    }

    public static int[] readKey(String keyFile) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(keyFile));
        String[] raw = scan.nextLine().replaceAll("[\\]\\[]", "").split(", ");
        int[] key = new int[raw.length];
        for (int i = 0; i < raw.length; i++)
            key[i] = Integer.parseInt(raw[i]);
        return key;
    }

}
