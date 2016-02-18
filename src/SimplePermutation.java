import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;

public class SimplePermutation {

    int block_length;
    String plaintext = "pt.txt";
    String ciphertext = "ct.txt";
    String encrypted = "encrypted.txt";
    int[] key;

    public SimplePermutation(int block_length) {
        this.block_length = block_length;
        generateKey(block_length);
    }

    public SimplePermutation(int block_length, String inputFile, String outputFile) {
        this.block_length = block_length;
        this.plaintext = inputFile;
        this.ciphertext = outputFile;
        generateKey(block_length);
    }

    public void encrypt() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(plaintext));
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
        System.out.println("Encryption Succeed");
        PrintWriter writer = new PrintWriter(ciphertext);
        writer.write(ct);
        writer.flush();
        writer.close();
    }

    public void decrypt() throws FileNotFoundException {
        Scanner scan = new Scanner(new File(ciphertext));
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
        System.out.println("Decryption Succeed");
        PrintWriter writer = new PrintWriter(encrypted);
        writer.write(pt);
        writer.flush();
        writer.close();
    }

    public void generateKey(int block_length) {
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
