package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Day7 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day7/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day7 day = new Day7();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        ArrayList<Data> dataList = new ArrayList<>();
        int[] ranksA = new int[12000], ranksB = new int[200];
        int i = 0;
        for (char ch : "AKQJT98765432".toCharArray()) {
            ranksB[ch] = ++i;
        }
        for (int x : new int[] {5,14,23,113,122,1112,11111}) {
            ranksA[x] = x;
        }
        for (String line : list) {
            String str = line.split("\\s+")[0];
            int value = Integer.valueOf(line.split("\\s+")[1]);
            int[] hash = new int[100];
            for (char ch : str.toCharArray()) {
                hash[ch]++;
            }
            ArrayList<Integer> counts = new ArrayList<>();
            for (char ch : "AKQJT98765432".toCharArray()) {
                counts.add(hash[ch]);
            }
            Collections.sort(counts);
            int n = 0;
            for (int v : counts) {
                n = n * 10 + v;
            }
            dataList.add(new Data(str, value, ranksA[n]));
        }
        
        Collections.sort(dataList, (Data a, Data b) -> {
            if (a.rank == b.rank) {
                for (int e = 0; e < a.str.length(); e++) {
                    if (ranksB[a.str.charAt(e)] != ranksB[b.str.charAt(e)]) {
                        return ranksB[a.str.charAt(e)] < ranksB[b.str.charAt(e)] ? 1 : -1;
                    }
                }
            }
            return a.rank < b.rank ? 1 : -1;
        });
        int factor = 1, res = 0;
        for (Data data : dataList) {
            res += factor++ * data.value;
        }
        return res;
    }
    
    class Data {
        String str;
        int value;
        int rank;
        Data(String str, int value, int rank) {
            this.str = str;
            this.value = value;
            this.rank = rank;
        }
    }
    
    private long part2(ArrayList<String> list) {
        ArrayList<Data> dataList = new ArrayList<>();
        int[] ranksA = new int[12000], ranksB = new int[200];
        int i = 0;
        for (char ch : "AKQT98765432J".toCharArray()) {
            ranksB[ch] = ++i;
        }
        for (int x : new int[] {5,14,23,113,122,1112,11111}) {
            ranksA[x] = x;
        }
        for (String line : list) {
            String str = line.split("\\s+")[0];
            int value = Integer.valueOf(line.split("\\s+")[1]);
            int[] hash = new int[100];
            for (char ch : str.toCharArray()) {
                hash[ch]++;
            }
            ArrayList<Integer> counts = new ArrayList<>();
            for (char ch : (hash['J'] > 0 ? "AKQT98765432" : "AKQJT98765432").toCharArray()) {
                counts.add(hash[ch]);
            }
            Collections.sort(counts);
            counts.set(counts.size() - 1, counts.get(counts.size() - 1) + hash['J']);
            int n = 0;
            for (int v : counts) {
                n = n * 10 + v;
            }
            dataList.add(new Data(str, value, ranksA[n]));
        }
        
        Collections.sort(dataList, (Data a, Data b) -> {
            if (a.rank == b.rank) {
                for (int e = 0; e < a.str.length(); e++) {
                    if (ranksB[a.str.charAt(e)] != ranksB[b.str.charAt(e)]) {
                        return ranksB[a.str.charAt(e)] < ranksB[b.str.charAt(e)] ? 1 : -1;
                    }
                }
            }
            return a.rank < b.rank ? 1 : -1;
        });
        int factor = 1, res = 0;
        for (Data data : dataList) {
            res += factor++ * data.value;
        }
        return res;
    }
}
