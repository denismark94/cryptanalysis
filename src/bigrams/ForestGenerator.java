package bigrams;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Denis on 21.04.2016.
 */
public class ForestGenerator {
    boolean[][] rt;
    boolean[] used;
    ArrayList<String> tree = new ArrayList<>();

    public ForestGenerator(boolean[][] rt) {
        this.rt = rt;
    }

    public ArrayList<String>[] buildForest () {
        ArrayList<String> forest[];
        ArrayList<Integer> vorts = new ArrayList<>();
        int count = 0, min = rt.length;
        for (int i = 0; i < rt.length; i++) {
            count = 0;
            for (int j = 0; j < rt[i].length; j++) {
                if (!rt[j][i])
                    count++;
            }
            if (count < min)
                min = count;
        }
        for (int i = 0; i < rt.length; i++) {
            count = 0;
            for (int j = 0; j < rt[i].length; j++) {
                if (!rt[j][i])
                    count++;
            }
            if (count == min)
                vorts.add(i);
        }
        forest = new ArrayList[vorts.size()];
        for (int i = 0; i < vorts.size(); i++) {
            tree.clear();
            buildTree(vorts.get(i));
            ArrayList<String> copy = (ArrayList<String>) tree.clone();
            forest[i] = copy;
        }
        return forest;
    }

    public void buildTree(int vertex) {
        used = new boolean[rt.length];
        depthSearch(vertex, ""+vertex, 0);
    }

    public void depthSearch(int v, String path, int depth) {
        depth++;
        if (depth > 1)
            path += "," + v;
        if (depth == rt.length) {
            tree.add(path);
            path = "4";
            return;
        }
        int counter = 0;
        for (int i = 0; i < rt.length; i++) {
            if (!rt[v][i] && !path.contains("" + i)) {
                depthSearch(i, path, depth);
                counter++;
            }
        }
        if (counter == 0)
            tree.add(path);
    }
}
