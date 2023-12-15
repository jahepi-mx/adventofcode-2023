package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day6 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day6/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day6 day = new Day6();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private long part1(ArrayList<String> list) {
        String[] times = list.get(0).split(":\\s+")[1].split("\\s+");
        String[] distances = list.get(1).split(":\\s+")[1].split("\\s+");
        long res = 1;
        for (int a = 0; a < times.length; a++) {
            double time = Double.parseDouble(times[a]);
            double distance = Double.parseDouble(distances[a]);
            double v1 = Math.floor(-Math.sqrt(time * time / 4 - distance) + time / 2);
            double v2 = Math.ceil(Math.sqrt(time * time / 4 - distance) + time / 2);
            res *= (long) v2 - (long) v1 - 1;
        }
        return res;
    }
    
    private long part2(ArrayList<String> list) {
        String[] times = list.get(0).split(":\\s+")[1].split("\\s+");
        String[] distances = list.get(1).split(":\\s+")[1].split("\\s+");
        long res = 1;
        String timeStr = "", distanceStr = "";
        for (int a = 0; a < times.length; a++) {
            timeStr += times[a];
            distanceStr += distances[a];
        }
        double time = Double.parseDouble(timeStr);
        double distance = Double.parseDouble(distanceStr);
        double v1 = Math.floor(-Math.sqrt(time * time / 4 - distance) + time / 2);
        double v2 = Math.ceil(Math.sqrt(time * time / 4 - distance) + time / 2);
        res *= (long) v2 - (long) v1 - 1;
        return res;
    }
}
