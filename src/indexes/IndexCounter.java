package indexes;

import java.util.ArrayList;

/**
 * Created by Denis on 14.03.2016.
 */
public class IndexCounter {
    static char[] alph;

    public static double getAvgMatchIndex(String x, String y) {
        getAlph(x,y);
        String temp = "";
        double px, py, result = 0;
        for (int i = 0; i < alph.length; i++) {
            temp = "[" + x + "]";
            px = ((double) temp.split(alph[i] + "").length - 1) / (double) x.length();
            temp = "[" + y + "]";
            py = ((double) temp.split(alph[i] + "").length - 1) / (double) y.length();
            result += px * py;
        }
        return (result * 100);
    }

    public static double getMatchIndex(String x, String y) {
        int len = Math.min(x.length(),y.length());
        double result = 0;
        for (int i = 0; i < len; i++)
            if (x.charAt(i) == y.charAt(i))
                result++;
        return  ((result / x.length()) * 100);
    }

    public static void getAlph(String x, String y) {
        String temp = x + y;
        ArrayList<Character> abc = new ArrayList<>();
        for (int i = 0; i < temp.length(); i++) {
            if (!abc.contains(temp.charAt(i)))
                abc.add(temp.charAt(i));
        }
        alph = new char[abc.size()];
        for (int i = 0; i < abc.size(); i++)
            alph[i] = abc.get(i);
    }


}
