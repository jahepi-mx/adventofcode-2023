package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day18 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day18/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day18 day = new Day18();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }
    
    long minX = Integer.MAX_VALUE;
    long maxX = 0;
    long minY = Integer.MAX_VALUE;
    long maxY = 0;
    long width = 17_000_000; // 14047423 16119177
    int[][] dirs = new int[][] {{1,0},{0,1},{-1,0},{0,-1}};
    
    private long part1(ArrayList<String> list) {
        int[] indexes = new int[256];
        HashSet<Long> visited = new HashSet<>();
        HashSet<Long> filled = new HashSet<>();
        
        indexes['R'] = 0;
        indexes['D'] = 1;
        indexes['L'] = 2;
        indexes['U'] = 3;
        
        long offsetX = 5_000_000, offsetY = 15_000_000;
        long x = offsetX, y = offsetY;
        visited.add(y * width + x);
        for (String str : list) {
            String[] parts = str.split(" ");
            char dir = parts[0].charAt(0);
            int n = Integer.valueOf(parts[1]);
            int index = indexes[dir];
            for (int a = 0; a < n; a++) {
                x += dirs[index][0];
                y += dirs[index][1];
                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
                visited.add(y * width + x);
            }
        }
        
        for (long ty = minY; ty <= maxY; ty++) {
           find(visited, filled, minX, ty);
        }
        for (long ty = minY; ty <= maxY; ty++) {
            find(visited, filled, maxX, ty);
        }
        for (long tx = minX; tx <= maxX; tx++) {
            find(visited, filled, tx, minY);
        }
        for (long tx = minX; tx <= maxX; tx++) {
            find(visited, filled, tx, maxY);
        }
        
        long area = (maxX - minX + 1) * (maxY - minY + 1);
        long res = area - filled.size();
        
        return res;
    }
    
    private void find(HashSet<Long> visited, HashSet<Long> filled, long tx, long ty) {
        if (!visited.contains(ty * width + tx) && !filled.contains(ty * width + tx)) {
            Queue<Long> queue = new LinkedList<>();
            queue.add(ty * width + tx);
            filled.add(ty * width + tx);
            while (!queue.isEmpty()) {
                long node = queue.remove();
                for (int[] dir : dirs) {
                    long xx = node % width + dir[0];
                    long yy = node / width + dir[1];
                    if (xx >= minX && xx <= maxX && yy >= minY && yy <= maxY 
                            && !visited.contains(yy * width + xx) && !filled.contains(yy * width + xx)) {
                        queue.add(yy * width + xx);
                        filled.add(yy * width + xx);
                    }
                }
            }
        }
    }
    
    private long part2(ArrayList<String> list) {
        ArrayList<Long> vertices = new ArrayList<>();
        long offsetX = 5_000_000, offsetY = 15_000_000;
        long x = offsetX, y = offsetY;
        vertices.add(y * width + x);
        int perimeter = 0;
        for (String str : list) {
            String[] parts = str.split(" ");
            int n = Integer.parseInt(parts[2].substring(2, parts[2].length() - 2), 16);
            int index = parts[2].charAt(7) - '0';
            x += n * dirs[index][0];
            y += n * dirs[index][1];
            vertices.add(y * width + x);
            perimeter += n;
        }
        long res = 0;
        for (int a = 0; a < vertices.size() - 1; a++) {
            long v1 = vertices.get(a), v2 = vertices.get(a + 1);
            res += (v1 % width) * (v2 / width) - (v1 / width) * (v2 % width);
            
        }
        return res / 2 + perimeter / 2 + 1;
    }
}
