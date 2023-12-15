package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day9 {

    public static void main(String[] args) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day9/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day9 day = new Day9();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));

    }

    private long part1(ArrayList<String> list) {
        long res = 0;
        for (String line : list) {
            String[] parts = line.split(" ");
            int[] nums = new int[parts.length];
            for (int a = 0; a < nums.length; a++) {
                nums[a] = Integer.parseInt(parts[a]);
            }
            int index = nums.length - 1;
            int flag = 1;
            while (flag != 0) {
                long last = 0;
                flag = 0;
                for (int a = 0; a < index; a++) {
                    last = nums[a + 1];
                    nums[a] = nums[a + 1] - nums[a];
                    flag |= nums[a];
                }
                res += last;
                index--;
            }
        }
        return res;
    }

    private long part2(ArrayList<String> list) {
        long res = 0;
        for (String line : list) {
            String[] parts = line.split(" ");
            int[] nums = new int[parts.length];
            for (int a = 0; a < nums.length; a++) {
                nums[nums.length - a  - 1] = Integer.parseInt(parts[a]);
            }
            int index = nums.length - 1;
            int flag = 1;
            while (flag != 0) {
                long last = 0;
                flag = 0;
                for (int a = 0; a < index; a++) {
                    last = nums[a + 1];
                    nums[a] = nums[a + 1] - nums[a];
                    flag |= nums[a];
                }
                res += last;
                index--;
            }
        }
        return res;
    }
}
