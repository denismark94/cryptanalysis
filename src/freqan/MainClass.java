package freqan;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainClass {
    public static String key_file = "iofiles/key.txt";
    public static String en_alph_file = "iofiles/en_alph.txt";
    public static String ru_alph_file = "iofiles/ru_alph.txt";
    public static String mixed_alph_file = "iofiles/mixed_alph.txt";
    public static String pt_file = "iofiles/pt.txt";
    public static String ct_file = "iofiles/ct.txt";
    public static String result_table_1 = "iofiles/res_2.1.txt";
    public static String result_table_2 = "iofiles/res_2.2.txt";
    public static String result_table_3 = "iofiles/res_2.3.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of subtask");
        FileWriter fw;
        String[] request;
        String x = "", y = "";
        StringGenerator generator;
        int subtask = scanner.nextInt();
        double[][] indexes;
        String[] langs = {"english", "russian", "mixed"};
        String[] alphs = {en_alph_file, ru_alph_file, mixed_alph_file};
        switch (subtask) {
            case 1:
                fw = new FileWriter(result_table_1);
                fw.write(String.format("%20s %20s %20s\r\n", "english", "russian", "mixed"));
                indexes = new double[3][4];
                for (int i = 0; i < langs.length; i++) {
                    System.out.println("Generating " + langs[i] + " examples");
                    System.out.println("Enter \"type\" for manual typing or \"gen\" for generating strings");
                    generator = new StringGenerator(alphs[i]);
                    scanner.nextLine();
                    String command = scanner.nextLine();
                    switch (command) {
                        case "type":
                            for (int j = 0; j < 4; j++) {
                                System.out.print("x[" + j + "] = ");
                                x = scanner.nextLine();
                                System.out.print("y[" + j + "] = ");
                                y = scanner.nextLine();
                                indexes[i][j] = IndexCounter.getMatchIndex(x, y);
                            }
                            break;
                        case "gen":
                            int length;
                            for (int j = 0; j < 4; j++) {
                                System.out.print("String length = ");
                                length = scanner.nextInt();
                                x = generator.generate(length);
                                y = generator.generate(length);
                                indexes[i][j] = IndexCounter.getMatchIndex(x, y);
                            }
                            break;
                    }
                }
                for (int i = 0; i < 4; i++)
                    fw.write(String.format("%20.2f %20.2f %20.2f\r\n", indexes[0][i], indexes[1][i], indexes[2][i]));
                fw.flush();
                fw.close();
                break;
            case 2:
                fw = new FileWriter(result_table_2);
                fw.write(String.format("%20s %20s %20s\r\n", "english", "russian", "mixed"));
                indexes = new double[3][4];
                for (int i = 0; i < langs.length; i++) {
                    System.out.println("Generating " + langs[i] + " examples");
                    System.out.println("Enter \"type\" for manual typing or \"gen\" for generating strings");
                    generator = new StringGenerator(alphs[i]);
                    scanner.nextLine();
                    String command = scanner.nextLine();
                    switch (command) {
                        case "type":
                            for (int j = 0; j < 4; j++) {
                                System.out.print("x[" + j + "] = ");
                                x = scanner.nextLine();
                                System.out.print("y[" + j + "] = ");
                                y = scanner.nextLine();
                                indexes[i][j] = IndexCounter.getAvgMatchIndex(x, y);
                            }
                            break;
                        case "gen":
                            int length;
                            for (int j = 0; j < 4; j++) {
                                System.out.print("String length = ");
                                length = scanner.nextInt();
                                x = generator.generate(length);
                                y = generator.generate(length);
                                indexes[i][j] = IndexCounter.getAvgMatchIndex(x, y);
                            }
                            break;
                    }
                }
                for (int i = 0; i < 4; i++)
                    fw.write(String.format("%20.2f %20.2f %20.2f\r\n", indexes[0][i], indexes[1][i], indexes[2][i]));
                fw.flush();
                fw.close();
                break;

            case 3:
                fw = new FileWriter(result_table_3);
                Vigenere cipher = new Vigenere(en_alph_file,ct_file);
                System.out.println("Choose alphabet: ru|en|mixed");
                scanner.nextLine();
                String lang = scanner.nextLine();
                switch (lang) {
                    case "ru":
                        cipher = new Vigenere(ru_alph_file, ct_file);
                        break;
                    case "mixed":
                        cipher = new Vigenere(mixed_alph_file, ct_file);
                        break;
                }
                System.out.print("Encryption...\nKey(5):");
                String key = scanner.nextLine();
                String ct5 = cipher.crypt(pt_file, key);
                System.out.println("Encryption: success");

                System.out.print("Encryption...\nKey(7):");
                key = scanner.nextLine();
                String ct7 = cipher.crypt(pt_file, key);
                System.out.println("Encryption: success");
                String pt = Vigenere.readFromFile(pt_file);
                indexes = new double[3][15];
                for (int i = 0; i < 15; i++) {
                    indexes[0][i] = IndexCounter.getMatchIndex(pt, shift(pt, i + 1));
                    indexes[1][i] = IndexCounter.getMatchIndex(ct5, shift(ct5, i + 1));
                    indexes[2][i] = IndexCounter.getMatchIndex(ct7, shift(ct7, i + 1));
                }

                fw.write(String.format("%20s %20s %20s\r\n", "plain", "key = 5", "key = 7"));
                for (int i = 0; i < 15; i++)
                    fw.write(String.format("%20.2f %20.2f %20.2f\r\n", indexes[0][i], indexes[1][i], indexes[2][i]));
                fw.flush();
                fw.close();
        }
        System.out.println("Index count: sucess");
    }

//                System.out.print("Enter command:\n>");
//                request = scanner.nextLine().split(" ");
//                while (!request[0].equals("stop")) {
//                    switch (request[0]) {
//                        case "":
//                            int x_length = Integer.parseInt(request[1]);
//                            int y_length = Integer.parseInt(request[2]);
//                            generator =
//                            x = generator.generate(x_length);
//                            System.out.println("x: " + x);
//                            y = generator.generate(y_length);
//                            System.out.print("y: " + y + "\n>");
//                            break;
//                        case "chalph":
//                            if (request[1].contains(".txt"))
//                                generator = new StringGenerator("iofiles/" + request[1]);
//                            else
//                                generator = new StringGenerator(request[1].replaceAll(",", "").toCharArray());
//                            System.out.print(">");
//                            break;
//                        case "count":
//                            if (request.length != 1) {
//                                x = request[1];
//                                y = request[2];
//                                IndexCounter.getAlph(x, y);
//                            }
//                            if (x == "") {
//                                System.err.println("Generate strings or type manually first\n>");
//                                break;
//                            }
//                            System.out.print("Match Index = " + IndexCounter.getMatchIndex(x, y) + "\n>");
//                            fw.write();
//                            break;
//                    }
//                    request = scanner.nextLine().split(" ");
//                }
//        }

        /*System.out.print("Encryption...\nKey(5):");
        String key = scanner.nextLine();
        Vigenere cipher = new Vigenere(alph_file, ct_file);
        String ct5 = cipher.crypt(pt_file, key);
        System.out.println("Encryption succeed");

        System.out.print("Encryption...\nKey(7):");
        key = scanner.nextLine();
        String ct7 = cipher.crypt(pt_file, key);
        System.out.println("Encryption succeed");
        String pt = Vigenere.readFromFile(pt_file);
        int[][] indexes = new int[3][15];
        for (int i = 0; i < 15; i++) {
            indexes[0][i] = IndexCounter.getMatchIndex(pt, shift(pt, i + 1));
            indexes[1][i] = IndexCounter.getMatchIndex(ct5, shift(ct5, i + 1));
            indexes[2][i] = IndexCounter.getMatchIndex(ct7, shift(ct7, i + 1));
        }

        FileWriter archivo = new FileWriter(result_table);
        archivo.write(String.format("%20s %20s %20s\r\n", "plain", "key = 5", "key = 7"));
        for (int i = 0; i < 15; i++)
            archivo.write(String.format("%20d %20d %20d\r\n", indexes[0][i], indexes[1][i], indexes[2][i]));
        archivo.flush();
        archivo.close();
    }*/

    public static String shift(String input, int l) throws FileNotFoundException {
        String output = "";
        for (int i = 0; i < input.length(); i++)
            output += input.charAt((i + l) % input.length());
        return output;
    }
}
