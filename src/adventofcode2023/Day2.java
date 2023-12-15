package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day2/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day2 day = new Day2();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int id = 1, sum = 0;
        for (String str : list) {
            String[] parts = str.split(" ");
            boolean flag = false;
            for (int a = 2; a < parts.length; a += 2) {
                int value = Integer.parseInt(parts[a]);
                if (parts[a + 1].indexOf("green") >= 0 && value > 13) {
                    flag = true;
                } else if (parts[a + 1].indexOf("red") >= 0 && value > 12) {
                    flag = true;
                } else if (value > 14) {
                    flag = true;
                }
            }
            sum += !flag ? id : 0;
            id++;
        }
        return sum;
    }
    
    private int part2(ArrayList<String> list) {
        int sum = 0;
        for (String str : list) {
            String[] parts = str.split(" ");
            int green = 0, red = 0, blue = 0;
            for (int a = 2; a < parts.length; a += 2) {
                int value = Integer.parseInt(parts[a]);
                if (parts[a + 1].indexOf("green") >= 0) {
                    green = Math.max(value, green);
                } else if (parts[a + 1].indexOf("red") >=0) {
                    red = Math.max(value, red);
                } else {
                    blue = Math.max(value, blue);
                }
            }
            sum += green * blue * red;
        }
        return sum;
    }
}
