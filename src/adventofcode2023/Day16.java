package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day16 {

    public static void main(String[] args) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day16/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day16 day = new Day16();
        System.out.println("Part 1: " + day.part1(list, 0, 0, 0));
        System.out.println("Part 2: " + day.part2(list));

    }

    private int part1(ArrayList<String> list, int x, int y, int dir) {
        int width = list.get(0).length();
        int height = list.size();
        int[][] dirs = new int[][] {{1,0},{0,1},{-1,0},{0,-1}};
        HashMap<Integer, Integer> map = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        HashSet<Integer> visitedNodes = new HashSet<>();
        
        map.put('/'*10+0, 3); // >/
        map.put('/'*10+2, 1); // /<
        map.put('/'*10+1, 2); // v/
        map.put('/'*10+3, 0); // /^
        
        map.put('\\'*10+0, 1); // >\
        map.put('\\'*10+2, 3); // \<
        map.put('\\'*10+1, 0); // \v
        map.put('\\'*10+3, 2); // ^\
        
        map.put('|'*10+0, 13); // >/
        map.put('|'*10+2, 13); // /<
        map.put('-'*10+1, 20); // v/
        map.put('-'*10+3, 20); // /^
        
        Queue<Beam> queue = new LinkedList<>();
        queue.add(new Beam(x, y, dir));
        
        while (!queue.isEmpty()) {
            
            int size = queue.size();
            for (int a = 0; a < size; a++) {
                Beam beam = queue.remove();
                if (beam.x >= 0 && beam.x < width && beam.y >= 0 && beam.y < height && !visited.contains(beam.x + " " + beam.y + " " + beam.dir)) {
                    visitedNodes.add(beam.y * width + beam.x);
                    char value = list.get(beam.y).charAt(beam.x);
                    if (map.containsKey(value*10 + beam.dir)) {
                        
                        int dirsNum = map.get(value*10 + beam.dir);
                        if (dirsNum > 10) {
                            
                            beam.dir = dirsNum % 10;
                            Beam newBeam = new Beam(beam.x, beam.y,  dirsNum / 10 % 10);
                            
                            beam.x += dirs[beam.dir][0];
                            beam.y += dirs[beam.dir][1];
                            newBeam.x += dirs[newBeam.dir][0];
                            newBeam.y += dirs[newBeam.dir][1];
                            queue.add(newBeam);
                            
                        } else {
                            beam.dir = dirsNum;
                            beam.x += dirs[beam.dir][0];
                            beam.y += dirs[beam.dir][1];
                        }
                        queue.add(beam);
                        
                    } else {
                        visited.add(beam.x + " " + beam.y + " " + beam.dir);
                        beam.x += dirs[beam.dir][0];
                        beam.y += dirs[beam.dir][1];
                        queue.add(beam);
                    }
                }
            }
        }
        
        return visitedNodes.size();
    }
    
    class Beam {
        int dir, x, y;
        Beam(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    private int part2(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int max = 0;
        for (int y = 0; y < height; y++) {
            int res = this.part1(list, 0, y, 0);
            max = Math.max(max, res);
        }
        for (int y = 0; y < height; y++) {
            int res = this.part1(list, width - 1, y, 2);
            max = Math.max(max, res);
        }
        for (int x = 0; x < width; x++) {
            int res = this.part1(list, x, 0, 1);
            max = Math.max(max, res);
        }
        for (int x = 0; x < width; x++) {
            int res = this.part1(list, x, height - 1, 3);
            max = Math.max(max, res);
        }
        return max;
    }
}

