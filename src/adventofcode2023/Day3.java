package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day3 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day3/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day3 day = new Day3();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int height = list.size();
        int width = list.get(0).length();
        int n = 0, sum = 0;
        boolean flag = false;
        for (int y = 0; y < height; y++) {
            String line = list.get(y);
            for (int x = 0; x < width; x++) {
                char ch = line.charAt(x);
                if (ch >= '0' && ch <= '9') {
                    for (int[] vector : new int[][] {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}}) {
                        int tx = x + vector[0];
                        int ty = y + vector[1];
                        if (tx >= 0 && tx < width && ty >= 0 && ty < height) {
                            char tmp = list.get(ty).charAt(tx);
                            if ((tmp >= '0' && tmp <= '9' || tmp == '.') == false) {
                                flag = true;
                            }
                        }
                    }
                    n *= 10;
                    n += ch - 48;
                } else {
                    sum += flag ? n : 0;
                    n = 0;
                    flag = false;
                }
            }
            sum += flag ? n : 0;
            n = 0;
            flag = false;
        }
        return sum;
    }
    
    private int part2(ArrayList<String> list) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int height = list.size();
        int width = list.get(0).length();
        int n = 0, sum = 0;
        int pos = 0;
        for (int y = 0; y < height; y++) {
            String line = list.get(y);
            for (int x = 0; x < width; x++) {
                char ch = line.charAt(x);
                if (ch >= '0' && ch <= '9') {
                    for (int[] vector : new int[][] {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}}) {
                        int tx = x + vector[0];
                        int ty = y + vector[1];
                        if (tx >= 0 && tx < width && ty >= 0 && ty < height) {
                            char tmp = list.get(ty).charAt(tx);
                            if ((tmp >= '0' && tmp <= '9' || tmp == '.') == false) {
                                pos = tmp == '*' ? ty * width + tx : pos;
                            }
                        }
                    }
                    n *= 10;
                    n += ch - 48;
                } else {
                    
                    if (pos > 0) {
                        map.put(pos, map.containsKey(pos) ? map.get(pos) : new ArrayList<Integer>());
                        map.get(pos).add(n);
                    }
                    n = pos = 0;
                }
            }
            
            if (pos > 0) {
                map.put(pos, map.containsKey(pos) ? map.get(pos) : new ArrayList<Integer>());
                map.get(pos).add(n);
            }
            n = pos = 0;
        }
        for (int key : map.keySet()) {
            if (map.get(key).size() == 2) {
                sum += map.get(key).get(0) * map.get(key).get(1);
            }
        }
        return sum;
    }
}
