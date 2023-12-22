package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day21 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day21/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day21 day = new Day21();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int node = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < height; x++) {
                if (list.get(y).charAt(x) == 'S') {
                    node = y * width + x;
                }
            }
        }
        int steps = 6;
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(node);
        int res = 0;
        for (int step = 0; step < steps; step++) {
            int count = 0;
            int[] visited = new int[width * height];
            int size = queue.size();
            for (int a = 0; a < size; a++) {
                int tmpNode = queue.remove();
                visited[tmpNode] = 1;
                for (int[] dir : new int[][] {{1,0},{0,1},{-1,0},{0,-1}}) {
                    int x = tmpNode % width + dir[0];
                    int y = tmpNode / width + dir[1];
                    if (x >= 0 && x < width && y >= 0 && y < height 
                            && list.get(y).charAt(x) == '.' && visited[y * width + x] == 0) {
                        queue.add(y * width + x);
                        count++;
                        visited[y * width + x] = 1;
                    }
                }
            }
            res = count;
        }
        return res + 1;
    }
    
    private int part2(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int size = 21;
        int realWidth = width * size;
        int realHeight = height * size;
        int[] map = new int[width * size * height * size];
        int node = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {  
                for (int yy = 0; yy < height; yy++) {
                    for (int xx = 0; xx < width; xx++) {
                        if (list.get(yy).charAt(xx) == 'S') {
                            node = yy * width + xx;
                        }
                        if (list.get(yy).charAt(xx) == '#') {
                            int tx = xx + x * width;
                            int ty = yy + y * height;
                            map[ty * realWidth + tx] = 1;
                        }
                    }
                }
                
            }
        }
        
        int tx = node % width + (size / 2 * width);
        int ty = node / width + (size / 2 * height);
        node = ty * realWidth + tx;
        
        /*
        for (int y = 0; y < realHeight; y++) {
            for (int x = 0; x < realWidth; x++) {
                int n = y * realWidth + x;
                System.out.print(n == node ? 'S' : (map[n] == 1 ? '#' : '.'));
            }
            System.out.println("");
        }
        */
        
        HashSet<String> states = new HashSet<String>();
        int steps = 100;
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(node);
        int res = 0;
        for (int step = 0; step < steps; step++) {
            int count = 0;
            int[] visited = new int[realWidth * realHeight];
            int qsize = queue.size();
            for (int a = 0; a < qsize; a++) {
                int tmpNode = queue.remove();
                visited[tmpNode] = 1;
                for (int[] dir : new int[][] {{1,0},{0,1},{-1,0},{0,-1}}) {
                    int x = tmpNode % realWidth + dir[0];
                    int y = tmpNode / realWidth + dir[1];
                    if (x >= 0 && x < realWidth && y >= 0 && y < realHeight 
                            && map[y * realWidth + x] == 0 && visited[y * realWidth + x] == 0) {
                        queue.add(y * realWidth + x);
                        count++;
                        visited[y * realWidth + x] = 1;
                    }
                }
            }
            
            
            // Copy state
            String state = "";
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tx = x + (size / 2 * width);
                    ty = y + (size / 2 * height);
                    int n = ty * realWidth + tx;
                    if (visited[n] == 1) {
                        state += (y * width + x)  + " ";
                    }
                    //System.out.print(n == node ? 'S' : (map[n] == 1 ? '#' : '.'));
                }
                //System.out.println("");
            }
            if (states.contains(state)) {
                System.out.println("Step: " + step + " " + count);
            }
            states.add(state);
            
            
            // Copy state
            state = "";
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tx = x + (size / 2 * width);
                    ty = y + ((size / 2 - 1) * height);
                    int n = ty * realWidth + tx;
                    if (visited[n] == 1) {
                        state += (y * width + x)  + " ";
                    }
                    //System.out.print(n == node ? 'S' : (map[n] == 1 ? '#' : '.'));
                }
                //System.out.println("");
            }
            if (states.contains(state)) {
                System.out.println("Step UP: " + step + " " + count);
            }
            states.add(state);
            
            // Copy state
            state = "";
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    tx = x + ((size / 2 - 1) * width);
                    ty = y + ((size / 2) * height);
                    int n = ty * realWidth + tx;
                    if (visited[n] == 1) {
                        state += (y * width + x)  + " ";
                    }
                    //System.out.print(n == node ? 'S' : (map[n] == 1 ? '#' : '.'));
                }
                //System.out.println("");
            }
            if (states.contains(state)) {
                System.out.println("Step LEFT: " + step + " " + count);
            }
            states.add(state);
            
         
            
            res = count;
        }
        return res;
    }
}
