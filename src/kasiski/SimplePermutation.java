package kasiski;

import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class SimplePermutation {

    int block_length;
    String input = "pt.txt";
    String output = "ct.txt";
    private int[] key;
    boolean rewrite_output = true;

    public SimplePermutation() {
    }

    public SimplePermutation(String inputFile, String outputFile) {
        this.input = inputFile;
        this.output = outputFile;
    }

    public int[] getKey() {
        return key;
    }

    public void setKey(int[] key) {
        this.block_length = key.length;
        this.key = key;
    }

    public void encrypt() throws FileNotFoundException {
        if (key == null) {
            System.err.println("Error: The key wasn't generated");
            return;
        }
        Scanner scan = new Scanner(new File(input));
        String pt = "", ct = "";
        while (scan.hasNext())
            pt += scan.nextLine();
        char[] ptchars = pt.toCharArray();
        char[] ptBlock = new char[block_length];
        char[] ctBlock;
        for (int i = 0; i < ptchars.length; i++) {
            if (i % block_length == 0)
                Arrays.fill(ptBlock, ' ');
            ptBlock[i % block_length] = ptchars[i];
            if (i % block_length == (block_length - 1)) {
                ctBlock = encryptBlock(key, ptBlock);
                for (int j = 0; j < block_length; j++)
                    ct += ctBlock[j];
            }
        }
        if (ptchars.length % block_length != 0) {
            ctBlock = encryptBlock(key, ptBlock);
            for (int j = 0; j < block_length; j++)
                ct += ctBlock[j];
        }
        System.out.println("Encryption Successfull");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output, !rewrite_output)));
        if (rewrite_output)
            writer.write(ct);
        else
            writer.println("\n"+ct);
        writer.flush();
        writer.close();
    }

    public void decrypt() throws FileNotFoundException, UnsupportedEncodingException {
        if (key == null) {
            System.err.println("Error: The key is null, enter key first");
            return;
        }
        Scanner scan = new Scanner(new File(input));
        String ct = "", pt = "";
        while (scan.hasNext())
            ct += scan.nextLine();
        char[] ctchars = ct.toCharArray();
        char[] ctBlock = new char[block_length];
        char[] ptBlock;
        for (int i = 0; i < ctchars.length; i++) {
            if (i % block_length == 0)
                Arrays.fill(ctBlock, ' ');
            ctBlock[i % block_length] = ctchars[i];
            if (i % block_length == (block_length - 1)) {
                ptBlock = decryptBlock(key, ctBlock);
                for (int j = 0; j < block_length; j++)
                    pt += ptBlock[j];
            }
        }
        if (ctchars.length % block_length != 0) {
            ptBlock = decryptBlock(key, ctBlock);
            for (int j = 0; j < block_length; j++)
                pt += ptBlock[j];
        }
        System.out.println("Decryption Successfull");
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(output, !rewrite_output), "UTF-8"));
        if (rewrite_output)
            writer.write(pt);
        else
            writer.write("\n"+pt);
        writer.flush();
        writer.close();
    }

    public void generateKey(int block_length) {
        this.block_length = block_length;
        key = new int[block_length];
        SecureRandom rand = new SecureRandom();
        int temp;
        for (int i = 0; i < block_length; i++) {
            do {
                temp = rand.nextInt(block_length) + 1;
            } while (!isUnique(temp, key));
            key[i] = temp;
        }
        for (int i = 0; i < key.length; i++)
            key[i]--;
    }

    public char[] encryptBlock(int[] key, char[] ptBlock) {
        char[] ctBlock = new char[ptBlock.length];
        for (int i = 0; i < key.length; i++)
            ctBlock[i] = ptBlock[key[i]];
        return ctBlock;
    }

    public char[] decryptBlock(int[] key, char[] ptBlock) {
        char[] ctBlock = new char[ptBlock.length];
        for (int i = 0; i < key.length; i++)
            ctBlock[key[i]] = ptBlock[i];
        return ctBlock;
    }

    public boolean isUnique(int x, int[] array) {
        for (int i = 0; i < array.length; i++)
            if (x == array[i])
                return false;
        return true;
    }
}
