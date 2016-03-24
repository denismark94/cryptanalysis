package kasiski;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class MainClass {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        System.out.print("Enter command to execute command | \"stop\" to stop execution\n>");
        Scanner scan = new Scanner(System.in);
        String command, input, output, key, request = scan.nextLine();
        String[] parsed;
        int key_length;
        while (!request.equals("stop")) {
            parsed = request.split(" ");
            command = parsed[0];
            switch (command) {
                case "gen_key":
                    key_length = Integer.parseInt(parsed[1]);
                    key = "iofiles//" + parsed[2];
                    genKey(key_length, key);
                    break;
                case "crypt":
                    key = "iofiles//" + parsed[1];
                    input = "iofiles//" + parsed[2];
                    output = "iofiles//" + parsed[3];
                    encrypt(input, output, key);
                    break;
                case "decrypt":
                    key = "iofiles//" + parsed[1];
                    input = "iofiles//" + parsed[2];
                    output = "iofiles//" + parsed[3];
                    decrypt(input, output, key);
                    break;
                case "kasiski":
                    input = "iofiles//" + parsed[1];
                    getKeyLength(input);
                    break;
                case "brute":
                    key_length = Integer.parseInt(parsed[1]);
                    input = "iofiles//" + parsed[2];
                    output = "iofiles//" + parsed[3];
                    key = "iofiles//" + parsed[4];
                    bruteforce(key_length, input, output, key);
            }
            request = scan.nextLine();
        }
/*
        String pt = "iofiles/pt.txt",
                ct = "iofiles/ct.txt",
                decrypted = "iofiles/decrypted.txt",
                brute_results = "iofiles/hacked.txt",
                keyFile = "iofiles/key.txt";
        encrypt(6, pt, ct, keyFile);

//        decrypt(ct,decrypted,key);
        int outKeyLength = getKeyLength(ct);
//        bruteforce(outKeyLength, ct, brute_results);
*/


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
        System.out.print("Key generation: sucess\n>");
    }

    public static void encrypt(String infile, String outfile, String keyFile) throws FileNotFoundException {
        kasiski.SimplePermutation encrypter = new kasiski.SimplePermutation(infile, outfile);
        int[] key = readKey(keyFile);
        System.out.println("Key : " + Arrays.toString(key));
        encrypter.setKey(key);
        encrypter.encrypt();
        System.out.print("Encryption: success\n>");
    }

    public static void decrypt(String infile, String outfile, String keyFile) throws FileNotFoundException, UnsupportedEncodingException {
        kasiski.SimplePermutation decrypter = new kasiski.SimplePermutation(infile, outfile);
        int[] key = readKey(keyFile);
        System.out.println("Key : " + Arrays.toString(key));
        decrypter.setKey(key);
        decrypter.decrypt();
        System.out.print("Decryption: success\n>");
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
        System.out.print("Kasiski test: success\n>");
    }

    public static void bruteforce(int keyLength, String ctfile, String output, String gkeys) throws FileNotFoundException, UnsupportedEncodingException {
        KeyBruteforce brute = new KeyBruteforce(keyLength, ctfile, output, gkeys);
        brute.brute();
        System.out.print("Bruteforce: success\n>");
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
