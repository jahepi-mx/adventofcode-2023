package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day12 {

    public static void main(String[] args) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day12/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day12 day = new Day12();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));

    }

    private long part1(ArrayList<String> list) {
        long n = 0;
        for (String s : list) {
            String[] parts = s.split(" ");
            String str = parts[0];
            String[] counts = parts[1].split(",");
            int[] nums = new int[counts.length];
            for (int a = 0; a < counts.length; a++) {
                nums[a] = Integer.parseInt(counts[a]);
            }
            int size = 100;
            mem = new long[size][size][size];
            for (int a = 0; a < size; a++) {
                for (int b = 0; b < size; b++) {
                    for (int c = 0; c < size; c++) {
                        mem[a][b][c] = -1;
                    }
                }
            }
            n += perms(str.toCharArray(), nums, 0, 0, 0);
        }
        return n;
    }
    
    private long part2(ArrayList<String> list) {
        long n = 0;
        for (String s : list) {
            String[] parts = s.split(" ");
            String newStr = "";
            String newNums = "";
            for (int a = 0; a < 5; a++) {
                newStr += parts[0] + "?";
                newNums += parts[1] + ",";
            }
            newStr = newStr.substring(0, newStr.length() - 1);
            newNums = newNums.substring(0, newNums.length() - 1);
            String[] counts = newNums.split(",");
            int[] nums = new int[counts.length];
            for (int a = 0; a < counts.length; a++) {
                nums[a] = Integer.parseInt(counts[a]);
            }
            int size = 150;
            mem = new long[size][size][size];
            for (int a = 0; a < size; a++) {
                for (int b = 0; b < size; b++) {
                    for (int c = 0; c < size; c++) {
                        mem[a][b][c] = -1;
                    }
                }
            }
            n += perms(newStr.toCharArray(), nums, 0, 0, 0);
        }
        
        return n;
    }
    
    long[][][] mem;
    private long perms(char[] chars, int[] groups, int index, int group, int nGroup) {
        
        if (index == chars.length) {
            
            if (group > 0) {
                if (nGroup >= groups.length) {
                    return 0;
                }
                if (groups[nGroup] != group) {
                    return 0;
                }
                return nGroup == groups.length - 1 ? 1 : 0;
            }
            
            return nGroup == groups.length ? 1 : 0;
        }
        
        if (mem[index][group][nGroup] != -1) {
            return mem[index][group][nGroup];
        }
        
        long res = 0;
        if (chars[index] == '?') {
            
            res += perms(chars, groups, index + 1, group + 1, nGroup);
            
            if (group > 0) {
                
                if (nGroup < groups.length && groups[nGroup] == group) {
                    res += perms(chars, groups, index + 1, 0, nGroup + 1);
                }
                
            } else {
                res += perms(chars, groups, index + 1, 0, nGroup);
            }
            
            
        } else {
            
            if (chars[index] == '#') {
                
                res += perms(chars, groups, index + 1, group + 1, nGroup);
                
            } else {
                
                if (group > 0) {
                    
                    if (nGroup < groups.length && groups[nGroup] == group) {
                        res += perms(chars, groups, index + 1, 0, nGroup + 1);
                    }
                    
                } else {
                    res += perms(chars, groups, index + 1, 0, nGroup);
                }
                
            }
            
        }
        mem[index][group][nGroup] = res;
        return res;
    }
}
