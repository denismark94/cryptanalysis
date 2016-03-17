package kasiski;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Denis on 13.03.2016.
 */
public class KasiskiTest {
    public String ct = "";

    public KasiskiTest(String ct) {
        this.ct = ct;
    }

    public int getKeyLength() throws FileNotFoundException {
        ArrayList<Integer> distances = getDistances(3, 9);
        return getMostFrequentGCD(distances);
    }

    public ArrayList<Integer> getDistances(int minBlock, int maxBlock) {
        String buf;
        int pos,dist;
        ArrayList<Integer> distances = new ArrayList<>();
        ArrayList<String> checkedDigrams = new ArrayList<>();
        //iterartion of size of digrams
        for (int buflen = minBlock; buflen < Math.min(maxBlock + 1, ct.length()); buflen++) {
            //shifting buffer
            for (int j = 0; j < ct.length() - 2 * buflen + 1; j++) {
                buf = ct.substring(j, j + buflen);
                //skip checked blocks
                if (!checkedDigrams.contains(buf)) {
                    checkedDigrams.add(buf);
                    pos = j;
                    do {
                        //calculating the distance between matching blocks
                        dist = pos;
                        pos = ct.indexOf(buf, pos + buflen);
                        dist = pos - dist;
                        if (dist > 0)
                            distances.add(dist);
                    }
                    while (pos != -1);
                }
            }
        }
        return distances;
    }

    public static int getMostFrequentGCD(ArrayList<Integer> avgDist) {
        Map<Integer, Integer> frequences = new HashMap<>();
        int gcd;
        for (int i = 0; i < avgDist.size(); i++) {
            for (int j = i; j < avgDist.size(); j++) {
                gcd = gcd(avgDist.get(i), avgDist.get(j));
                if (gcd < 3)
                    continue;
                if (frequences.containsKey(gcd))
                    frequences.put(gcd, frequences.get(gcd) + 1);
                else
                    frequences.put(gcd, 1);
            }
        }
        int answer = 0, max = 0;
        for (int i :
                frequences.keySet()) {
            if (max < frequences.get(i)) {
                answer = i;
                max = frequences.get(i);
            }
        }
        return answer;
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
