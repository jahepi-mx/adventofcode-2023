package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day5 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day5/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day5 day = new Day5();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private long part1(ArrayList<String> list) {
        ArrayList<Data>[] data = new ArrayList[7];
        for (int a = 0; a < data.length; a++) {
            data[a] = new ArrayList<Data>();
        }
        String[] seedsArr = list.get(0).split(":\\s+")[1].split("\\s+");
        long[] seeds = new long[seedsArr.length];
        for (int a = 0; a < seeds.length; a++) {
            seeds[a] = Long.parseLong(seedsArr[a]);
        }
        int id = 0;
        for (int a = 3; a < list.size(); a++) {
            if (list.get(a).length() > 0 && list.get(a).charAt(0) >= 'a') {
                id++;
            } else if (list.get(a).length() > 0 && list.get(a).charAt(0) >= '0' && list.get(a).charAt(0) <= '9') {
                String[] line = list.get(a).split("\\s+");
                Data obj = new Data();
                obj.a = Long.parseLong(line[0]);
                obj.b = Long.parseLong(line[1]);
                obj.c = Long.parseLong(line[2]);
                data[id].add(obj);
            }
        }
        long min = Long.MAX_VALUE;
        for (long seed : seeds) {
            for (int a = 0; a < data.length; a++) {
                for (Data obj : data[a]) {
                    long from = obj.b;
                    long to = obj.b + obj.c - 1;
                    if (seed >= from && seed <= to) {
                        long diff = seed - from;
                        seed = obj.a + diff;
                        break;
                    }
                }
                //System.out.println(seed);
            }
            min = Math.min(seed, min);
        }
        return min;
    }
    
    class Data {
        long a, b, c;
    }
    
    private long part2(ArrayList<String> list) {
        ArrayList<Data>[] data = new ArrayList[7];
        for (int a = 0; a < data.length; a++) {
            data[a] = new ArrayList<Data>();
        }
        String[] seedsArr = list.get(0).split(":\\s+")[1].split("\\s+");
        long[] seeds = new long[seedsArr.length];
        for (int a = 0; a < seeds.length; a++) {
            seeds[a] = Long.parseLong(seedsArr[a]);
        }
        int id = 0;
        for (int a = 3; a < list.size(); a++) {
            if (list.get(a).length() > 0 && list.get(a).charAt(0) >= 'a') {
                id++;
            } else if (list.get(a).length() > 0 && list.get(a).charAt(0) >= '0' && list.get(a).charAt(0) <= '9') {
                String[] line = list.get(a).split("\\s+");
                Data obj = new Data();
                obj.a = Long.parseLong(line[0]);
                obj.b = Long.parseLong(line[1]);
                obj.c = Long.parseLong(line[2]);
                data[id].add(obj);
            }
        }
        long min = Long.MAX_VALUE;
        int counter = 0;
        for (int d = 0; d < seeds.length; d += 2) {
            System.out.println("counter: " + counter++);
            long from = seeds[d];
            long to = seeds[d] + seeds[d + 1] - 1;
            for (long seed = from; seed <= to; seed++) {
                long seedTmp = seed;
                for (int a = 0; a < data.length; a++) {
                    for (Data obj : data[a]) {
                        long fromB = obj.b;
                        long toB = obj.b + obj.c - 1;
                        if (seedTmp >= fromB && seedTmp <= toB) {
                            long diff = seedTmp - fromB;
                            seedTmp = obj.a + diff;
                            break;
                        }
                    }
                }
                min = Math.min(seedTmp, min);
            }
        }
        return min;
    }
}
