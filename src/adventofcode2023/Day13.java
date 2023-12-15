package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day13 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day13/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day13 day = new Day13();
        System.out.println("Part 1: " + day.execute(list, true));
        System.out.println("Part 2: " + day.execute(list, false));
    }

    private int execute(ArrayList<String> list, boolean part1) {
        int res = 0;
        ArrayList<ArrayList<String>> maps = new ArrayList<ArrayList<String>>();
        ArrayList<String> map = new ArrayList<>();
        for (String line : list) {
            if (line.length() == 0) {
                maps.add(map);
                map = new ArrayList<String>();
            } else {
                map.add(line);
            }
        }
        maps.add(map);
        for (ArrayList<String> tmpMap : maps) {
            int height = tmpMap.size();
            int width = tmpMap.get(0).length();
            int[] intMap = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    intMap[y * width + x] = tmpMap.get(y).charAt(x) == '#' ? 1 : 0;
                }
            }
            res += part1 ? part1(intMap, width, height) : part2(intMap, width, height);   
        }
        return res;
    }
    
    private int part1(int[] intMap, int width, int height) {
        return checkMap(intMap, width, height, -1);
    }
    
    private int part2(int[] intMap, int width, int height) {
        int res = 0;
        for (int e = 0; e < width * height && res == 0; e++) {
            intMap[e] = intMap[e] ^ 1;
            res = checkMap(intMap, width, height, e);
            intMap[e] = intMap[e] ^ 1;
        }
        return res;
    }
    
    private int checkMap(int[] intMap, int width, int height, int smuged) {
        out: for (int x = 0; x < width - 1; x++) {
            HashSet<Integer> hash = new HashSet<>();
            for (int y = 0; y < height; y++) {
                int xa = x;
                int xb = x + 1;
                for (int c = 0; c <= Math.min(x, width - x - 2); c++) {
                    hash.add(y * width + xa);
                    hash.add(y * width + xb);
                    if (intMap[y * width + xa] != intMap[y * width + xb]) {
                        continue out;
                    }
                    xa--;
                    xb++;
                }
            }
            if (smuged == -1 || hash.contains(smuged)) {
                return x + 1;
            }
        }
        
        out: for (int y = 0; y < height - 1; y++) {
            HashSet<Integer> hash = new HashSet<>();
            for (int x = 0; x < width; x++) {
                int ya = y;
                int yb = y + 1;
                for (int c = 0; c <= Math.min(y, height - y - 2); c++) {
                    hash.add(ya * width + x);
                    hash.add(yb * width + x);
                    if (intMap[ya * width + x] != intMap[yb * width + x]) {
                        continue out;
                    }
                    ya--;
                    yb++;
                }
            }
            if (smuged == -1 || hash.contains(smuged)) {
                return (y + 1) * 100;
            }
        }
        return 0;
    }
}
