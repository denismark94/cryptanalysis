package freqan;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Denis on 14.03.2016.
 */
public class IndexCounter {
    static Character[] alph = {'a', 'b', 'c'};

public static void main(String[] args) throws FileNotFoundException {
    System.out.print(">");
    Scanner scan = new Scanner(System.in);
    String[] request = scan.nextLine().split(" ");
    StringGenerator generator = new StringGenerator("iofiles//alphabet.txt");
    String x = "",y = "";
    while(!request[0].equals("stop")) {
        switch (request[0]){
            case "gen":
                int x_length = Integer.parseInt(request[1]);
                int y_length = Integer.parseInt(request[2]);
                x = generator.generate(x_length);
                System.out.println("x: " + x);
                y = generator.generate(y_length);
                System.out.print("y: " + y + "\n>");
                break;
            case "alph":
                if (request[1].contains(".txt"))
                    generator=new StringGenerator(request[1]);
                else
                    generator = new StringGenerator(request[1].replaceAll(",","").toCharArray());
                System.out.print(">");
                break;
            case "ami":
                if (request.length != 1) {
                    x = request[1];
                    y = request[2];
                    getAlph(x,y);
                }
                if (x == "") {
                    System.err.println("Generate strings or type manually first\n>");
                    break;
                }
                System.out.print("Average Match Index = " + getAvgMatchIndex(x, y) + "\n>");
                System.out.print("Average Match Index = "+ );
        }

        request = scan.nextLine().split(" ");
    }

}
}

    public static int getAvgMatchIndex(String x, String y) {
        String temp = "";
        double px, py, result = 0;
        for (int i = 0; i < alph.length; i++) {
            temp = "[" + x + "]";
            px = ((double) temp.split(alph[i] + "").length - 1) / (double) x.length();
            temp = "[" + y + "]";
            py = ((double) temp.split(alph[i] + "").length - 1) / (double) y.length();
            result += px * py;
        }
        return (int) (result * 100);
    }

    public static int getMatchIndex(String x, String y) {
        if (x.length() != y.length()) {
            System.err.println("Length of strings must be equal");
            return -1;
        }
        double result = 0;
        for (int i = 0; i < x.length(); i++)
            if (x.charAt(i) == y.charAt(i))
                result++;
        return (int) ((result / x.length()) * 100);
    }

    public static void getAlph(String x, String y) {
        String temp = x + y;
        ArrayList<Character> abc = new ArrayList<>();
        for (int i = 0; i < temp.length(); i++) {
            if (!abc.contains(temp.charAt(i)))
                abc.add(temp.charAt(i));
        }
        alph = new Character[abc.size()];
        abc.toArray(alph);
        System.out.println(Arrays.toString(alph));
    }


}
