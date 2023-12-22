package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Day11 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day11/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day11 day = new Day11();
        System.out.println("Part 1: " + day.run(list, 2));
        System.out.println("Part 2: " + day.run(list, 1_000_000));
    }

    private long run(ArrayList<String> list, int dis) {
        int len = list.size();
        long sum = 0;
        HashSet<String> weights = new HashSet<>();
        HashSet<Integer> xSet = new HashSet<>();
        HashSet<Integer> ySet = new HashSet<>();
        long[][] results = new long[500][500];
        for (int y = 0; y < len; y++) {
            int xFlag = 1, yFlag = 1;
            for (int x = 0; x < len; x++) {
                xFlag = list.get(y).charAt(x) == '#' ? 0 : xFlag;
                yFlag = list.get(x).charAt(y) == '#' ? 0 : yFlag;
            }
            if (xFlag > 0) {
                ySet.add(y);
            }
            if (yFlag > 0) {
                xSet.add(y);
            }
        }

        for (int y = 0; y < len; y++) {
            for (int x = 0; x < len; x++) {
                if (xSet.contains(x - 1)) {
                    weights.add((y * len + x) + " " + (y * len + x - 1));
                }
                if (xSet.contains(x) && x + 1 < len) {
                    weights.add((y * len + x) + " " + (y * len + x + 1));
                }
                if (ySet.contains(y - 1)) {
                    weights.add((y * len + x) + " " + ((y - 1) * len + x));
                }
                if (ySet.contains(y) && y + 1 < len) {
                    weights.add((y * len + x) + " " + ((y + 1) * len + x));
                }
            }
        }
        
        ArrayList<Integer> nodes = new ArrayList<>();
        int[] map = new int[len * len];
        for (int y = 0; y < len; y++) {
            for (int x = 0; x < len; x++) {
                map[y * len + x] = list.get(y).charAt(x) == '#' ? 1 : 0;
                if (list.get(y).charAt(x) == '#') {
                    nodes.add(y * len + x);
                }
            }
        }
        for (int a = 0; a < nodes.size(); a++) {
            int node = nodes.get(a);
            int[] visited = new int[len * len];
            long[] distances = new long[len * len];
            Arrays.fill(distances, -1);
            PriorityQueue<Integer> queue = new PriorityQueue<Integer>((n1, n2) -> {
                return distances[n1] < distances[n2] ? -1 : distances[n1] > distances[n2] ? 1 : 0;
            });
            queue.add(node);
            distances[node] = 0;
            while (!queue.isEmpty()) {
                int parent = queue.remove();
                for (int[] dir : new int[][] {{1,0},{0,1},{-1,0},{0,-1}}) {
                    int nx = parent % len + dir[0];
                    int ny = parent / len + dir[1];
                    if (nx >= 0 && nx < len && ny >= 0 && ny < len && visited[ny * len + nx] == 0) {
                        long dist = distances[parent] + (weights.contains(parent + " " + (ny * len + nx)) ? dis : 1);
                        distances[ny * len + nx] = distances[ny * len + nx] == -1 ? dist : Math.min(dist, distances[ny * len + nx]);
                        queue.add(ny * len + nx);
                        visited[ny * len + nx] = 1;
                    }
                }
                visited[parent] = 1;
            }
            for (int b = 0; b < nodes.size(); b++) {
                if (a != b && results[a][b] == 0 && results[b][a] == 0) {
                    results[a][b] = distances[nodes.get(b)];
                    results[b][a] = distances[nodes.get(b)];
                    sum += distances[nodes.get(b)];
                }
            }
            
        }
        return sum;
    }
}
