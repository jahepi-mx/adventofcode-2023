package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Day1 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day1/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day1 day = new Day1();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int sum = 0;
        for (String str : list) {
            int n = 0;
            for (char ch : str.toCharArray()) {
                if (ch >= '0' && ch <= '9') {
                    n = n == 0 ? (ch - 48) * 10 + ch - 48 : n / 10 * 10 + ch - 48;
                }
            }
            sum += n;
        }
        return sum;
    }
    
    private int part2(ArrayList<String> list) {
        String[] nums = new String[] {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int sum = 0;
        for (String str : list) {
            int min = 1000, max = 0, ten = 0, one = 0;
            for (int a = 1; a < nums.length; a++) {
                int v1 = str.indexOf(nums[a]);
                if (v1 <= min && v1 >= 0) {
                    ten = (a > 9 ? a % 10 + 1 : a)  * 10;
                    min = v1;
                }
                v1 = str.lastIndexOf(nums[a]);
                if (v1 >= max && v1 >= 0) {
                    one = a > 9 ? a % 10 + 1 : a;
                    max = v1;
                }
            }
            sum += ten + one;
        }
        return sum;
    }

}
