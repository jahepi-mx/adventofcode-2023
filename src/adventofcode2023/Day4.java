package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Day4 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day4/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day4 day = new Day4();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int sum = 0;
        for (String line : list) {
            HashSet<Integer> set = new HashSet<>();
            String[] parts = line.split("\\s+\\|\\s+");
            String[] left = parts[0].split("\\s+");
            String[] right = parts[1].split("\\s+");
            for (int a = 2; a < left.length; a++) {
                set.add(Integer.parseInt(left[a]));
            }
            int n = 0;
            for (String str : right) {
                if (set.contains(Integer.valueOf(str))) {
                    n = n == 0 ? 1 : n * 2;
                }
            }
            sum += n;
        }
        return sum;
    }
    
    private int part2(ArrayList<String> list) {
        int sum = 0, id = 1;
        int[] hash = new int[list.size() + 1];
        for (String line : list) {
            HashSet<Integer> set = new HashSet<>();
            String[] parts = line.split("\\s+\\|\\s+");
            String[] left = parts[0].split("\\s+");
            String[] right = parts[1].split("\\s+");
            for (int a = 2; a < left.length; a++) {
                set.add(Integer.parseInt(left[a]));
            }
            for (String str : right) {
                if (set.contains(Integer.valueOf(str))) {
                    hash[id]++;
                }
            }
            id++;
        }
        int[] count = new int[list.size() + 1];
        Arrays.fill(count, 1);
        for (int a = 1; a < list.size() + 1; a++) {
            int n = hash[a];
            for (int b = a + 1; b <= a + n; b++) {
                count[b] += count[a];
            }
            sum += count[a];
        }
        return sum;
    }
}
