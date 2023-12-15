package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day8 {

    public static void main(String[] args) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day8/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day8 day = new Day8();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));

    }

    private int part1(ArrayList<String> list) {

        String dirs = list.get(0);
        int len = dirs.length();
        int[] left = new int[909090 + 1];
        int[] right = new int[909090 + 1];
        for (int a = 2; a < list.size(); a++) {
            String[] parts = list.get(a).split(" = ");
            String[] parts2 = parts[1].split(",");
            String leftNode = parts2[0].substring(1);
            String rightNode = parts2[1].substring(1, parts2[1].length() - 1);
            int node = parts[0].charAt(0) * 10000 + parts[0].charAt(1) * 100 + parts[0].charAt(2);
            left[node] = leftNode.charAt(0) * 10000 + leftNode.charAt(1) * 100 + leftNode.charAt(2);
            right[node] = rightNode.charAt(0) * 10000 + rightNode.charAt(1) * 100 + rightNode.charAt(2);
        }

        int i = 0, count = 0;
        int start = 656565;
        while (start != 909090) {
            char dir = dirs.charAt(i++);
            start = dir == 'L' ? left[start] : right[start];
            count++;
            i %= len;
        }
        return count;
    }

    private long part2(ArrayList<String> list) {

        String dirs = list.get(0);
        int len = dirs.length();
        int[] left = new int[909090 + 1];
        int[] right = new int[909090 + 1];
        int[] nodes = new int[6];
        int index = 0;
        for (int a = 2; a < list.size(); a++) {
            String[] parts = list.get(a).split(" = ");
            String[] parts2 = parts[1].split(",");
            String leftNode = parts2[0].substring(1);
            String rightNode = parts2[1].substring(1, parts2[1].length() - 1);
            int node = parts[0].charAt(0) * 10000 + parts[0].charAt(1) * 100 + parts[0].charAt(2);
            left[node] = leftNode.charAt(0) * 10000 + leftNode.charAt(1) * 100 + leftNode.charAt(2);
            right[node] = rightNode.charAt(0) * 10000 + rightNode.charAt(1) * 100 + rightNode.charAt(2);
            if (parts[0].charAt(2) == 'A') {
                nodes[index++] = node;
            }
        }
        long res = 1;
        for (int node : nodes) {
            int i = 0;
            long count = 0;
            int start = node / 10 % 10 * 10 + node % 10;
            while (start != 90) {
                char dir = dirs.charAt(i++);
                node = dir == 'L' ? left[node] : right[node];
                count++;
                i %= len;
                start = node / 10 % 10 * 10 + node % 10;
            }
            res = res * count / gcd(res, count);
        }
        return res;
    }
    
    private long gcd(long a, long b) {
        return a == 0 ? b : b == 0 ? a : gcd(b, a % b);
    }
}
