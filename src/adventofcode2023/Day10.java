package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Day10 {

    public static void main(String[] args) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day10/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day10 day = new Day10();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));

    }

    int[] visited;
    int width, height;
    
    private int part1(ArrayList<String> list) {
        int start = 0, res = 0;
        width = list.get(0).length();
        height = list.size();
        visited = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (list.get(y).charAt(x) == 'S') {
                    start = y * width + x;
                }
            }
        }
        
        String[] dirs = new String[] {
                "S7 SJ S- F7 FJ F- L7 LJ L- -7 -J --",
                "SF S7 S| JF J7 J| LF L7 L| |F |7 ||",
                "SF SL S- 7F 7L 7- JF JL J- -F -L --",
                "SJ SL S| FJ FL F| 7J 7L 7| |J |L ||"
        };
        Queue<Integer> queue = new LinkedList<>();
        int[] distances = new int[width * height];
        visited[start] = 1;
        queue.add(start);
        while (!queue.isEmpty()) {
            int node = queue.remove();
            int index = 0;
            for (int[] v : new int[][] {{1,0}, {0,-1}, {-1,0}, {0,1}}) {
                int nx = node % width + v[0];
                int ny = node / width + v[1];
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    String path = list.get(node / width).charAt(node % width) + "" + list.get(ny).charAt(nx);
                    if (dirs[index].indexOf(path) >= 0 && visited[ny * width + nx] == 0) {
                        visited[ny * width + nx] = 1;
                        queue.add(ny * width + nx);
                        distances[ny * width + nx] = distances[node] + 1;
                        res = Math.max(res, distances[ny * width + nx]);
                    }
                }
                index++;
            }
        }
        
        return res;
    }

    private int calculate(ArrayList<String> list) {
        width = list.get(0).length();
        height = list.size();
        int[] visited2 = new int[width * height];
        int[] flags = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (list.get(y).charAt(x) == '.' && visited2[y * width + x] == 0) {
                    int start = y * width + x;
                    Queue<Integer> queue = new LinkedList<>();
                    visited2[start] = 1;
                    queue.add(start);
                    int out = 0;
                    int[] visited3 = new int[width * height];
                    visited3[start] = 1;
                    while (!queue.isEmpty()) {
                        int node = queue.remove();
                        for (int[] v : new int[][] {{1,0}, {0,-1}, {-1,0}, {0,1}}) {
                            int nx = node % width + v[0];
                            int ny = node / width + v[1];
                            if (nx >= 0 && nx < width && ny >= 0 && ny < height && visited2[ny * width + nx] == 0 && list.get(ny).charAt(nx) == '.') {
                                visited2[ny * width + nx] = 1;
                                visited3[ny * width + nx] = 1;
                                queue.add(ny * width + nx);
                            }
                            if (nx < 0 || nx >= width || ny < 0 || ny >= height) {
                                out = 1;
                            }
                        }
                    }
                    if (out == 1) {
                        for (int a = 0; a < visited3.length; a++) {
                            if (visited3[a] == 1) {
                                flags[a] = 1;
                            }
                        }
                    }
                }
            }
        }
        int count = 0;
        height = list.size();
        width = list.get(0).length();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String str = flags[y*width+x] == 1 ? " " : list.get(y).charAt(x) + "";
                System.out.print(str);
                if (x % 2 == 0 && y % 2 == 0 && str.charAt(0) == '.') {
                    count++;
                }
            }
            System.out.println("");
        }
        return count;
    }
    
    private int part2(ArrayList<String> list) {
        String[] xreplace = ". . . - . - . . . . . . - - . . - - - . - . . . . - - . -".split(" ");
        String[] yreplace = ". | | | . . | | . | . . . . . . | . . . | | | . . . . . |".split(" ");
        HashMap<String, Integer> xmap = new HashMap<>();
        HashMap<String, Integer> ymap = new HashMap<>();
        int index = 0;
        for (String tmp : ".. || JL FJ |F FS .| |L 7. .F SL .L -- L- J. 7| F- -7 L7 7F F7 J| |. 7L JF -J LJ .S S-".split(" ")) {
            xmap.put(tmp, index++);
        }
        index = 0;
        for (String tmp : ".. || FJ FL .7 JS |J |L .F SL -- -. L- L. J- J. 7| -7 L7 J7 7J 7L F| -F LF JF .- .S S|".split(" ")) {
            ymap.put(tmp, index++);
        }
        
        ArrayList<String> newList = new ArrayList<String>();
        for (int y = 0; y < height; y++) {
            String newLine = "";
            for (int x = 0; x < width - 1; x++) {
                String a = visited[y * width + x] == 0 ? "." : list.get(y).charAt(x)  + "";
                String b = visited[y * width + x + 1] == 0 ? "." : list.get(y).charAt(x + 1) + "";
                String hash = a + "" + b;
                String rep = xreplace[xmap.get(hash)];
                newLine += a + rep;
                if (x == width - 2) {
                    newLine += b;
                }
            }
            newList.add(newLine);
        }
        
        ArrayList<String> newList2 = new ArrayList<String>();
        height = newList.size();
        width = newList.get(0).length();
        for (int y = 0; y < height - 1; y++) {
            String newLine = "";
            for (int x = 0; x < width; x++) {
                String a = newList.get(y).charAt(x)  + "";
                String b = newList.get(y + 1).charAt(x) + "";
                String hash = a + "" + b;
                String rep = yreplace[ymap.get(hash)];
                newLine += rep;
            }
            newList2.add(newList.get(y));
            newList2.add(newLine);
            if (y == height - 2) {
                newList2.add(newList.get(y + 1));
            }
        }
        
        return calculate(newList2);
    }
}
