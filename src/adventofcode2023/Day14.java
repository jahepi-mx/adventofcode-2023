package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day14 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day14/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day14 day = new Day14();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int res = 0;
        int width = list.get(0).length();
        int height = list.size();
        int[] map = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (list.get(y).charAt(x) == 'O') {
                    int ty = y, prev = 0;
                    while (true) {
                        prev = ty--;
                        if (ty < 0) {
                            ty = 0;
                            break;
                        }
                        if (map[ty * width + x] == 1 || map[ty * width + x] == 2) {
                            ty = prev;
                            break;
                        }
                    }
                    map[ty * width + x] = 2;
                    res += height - ty;
                } else {
                    map[y * width + x] = list.get(y).charAt(x) == '#' ? 1 : 0;
                }
            }
        }
        
        return res;
    }
    
    private int part2(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int res = 0;
        int[] map = new int[width * height];
        int[] mainMap = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mainMap[y * width + x] = list.get(y).charAt(x) == 'O' ? 2 : list.get(y).charAt(x) == '#' ? 1 : 0;
            }        
        }
        HashMap<String, Integer> hash = new HashMap<>();
        HashMap<Integer, Integer> values = new HashMap<>();
        for (int a = 0; a < 1000000000; a++) {
            for (int[] dir : new int[][] {{0,-1},{-1,0}}) {
                int sum = 0;
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        if (mainMap[y * width + x] == 2) {
                            int ty = y, tx = x, prevy = 0, prevx = 0;
                            while (true) {
                                prevx = tx;
                                prevy = ty;
                                tx += dir[0];
                                ty += dir[1];
                                if (ty < 0) {
                                    ty = 0;
                                    break;
                                }
                                if (tx < 0) {
                                    tx = 0;
                                    break;
                                }
                                if (map[ty * width + tx] == 1 || map[ty * width + tx] == 2) {
                                    ty = prevy;
                                    tx = prevx;
                                    break;
                                }
                            }
                            map[ty * width + tx] = 2;
                            sum += height - ty;
                        } else {
                            map[y * width + x] = mainMap[y * width + x];
                        }
                    }
                }
                mainMap = map;
                map = new int[width * height];
                res = sum;
                
            }
            for (int[] dir : new int[][] {{0,1},{1,0}}) {
                int sum = 0;
                for (int y = height - 1; y >= 0; y--) {
                    for (int x = width - 1; x >= 0; x--) {
                        if (mainMap[y * width + x] == 2) {
                            int ty = y, tx = x, prevy = 0, prevx = 0;
                            while (true) {
                                prevx = tx;
                                prevy = ty;
                                tx += dir[0];
                                ty += dir[1];
                                if (tx >= width) {
                                    tx = width - 1;
                                    break;
                                }
                                if (ty >= height) {
                                    ty = height - 1;
                                    break;
                                }
                                if (map[ty * width + tx] == 1 || map[ty * width + tx] == 2) {
                                    ty = prevy;
                                    tx = prevx;
                                    break;
                                }
                            }
                            map[ty * width + tx] = 2;
                            sum += height - ty;
                        } else {
                            map[y * width + x] = mainMap[y * width + x];
                        }
                    }
                }
                mainMap = map;
                map = new int[width * height];
                res = sum;
            }
            
            String key = "";
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    key += mainMap[y * width + x];
                }
            }
            if (hash.containsKey(key)) {
                int index = (1_000_000_000 - 1 - a) % (hash.get(key) - a);
                return values.get(index + hash.get(key));
            } else {
                hash.put(key, a);
                values.put(a, res);
            }
        }
        return 0;
    }
}
