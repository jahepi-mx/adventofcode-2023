package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Day17 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day17/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day17 day = new Day17();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int res = Integer.MAX_VALUE;
        int[][] dirs = new int[][] {{1,0},{0,1},{-1,0},{0,-1}};
        int[] map = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y * width + x] = list.get(y).charAt(x) - '0';
            }
        }
        
        HashSet<String> visited = new HashSet<>();
        HashMap<String, Integer> distances = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<Node>((n1, n2) -> {
            int dist1 = distances.containsKey(n1.key()) ? distances.get(n1.key()) : -1;
            int dist2 = distances.containsKey(n2.key()) ? distances.get(n2.key()) : -1;
            return dist1 < dist2 ? -1 : dist1 > dist2 ? 1 : 0;
        });
        Node node = new Node(0, 1, 0, 0);
        queue.add(node);
        distances.put(node.key(), 0);
        
        while (!queue.isEmpty()) {
            Node parent = queue.remove();
            
            int[] moves = parent.dir % 2 == 0 ? new int[] {1, 3} : new int[] {0, 2};
            for (int dir : moves) {
                int tcost = 0;
                int tx = parent.pos % width;
                int ty = parent.pos / width;
                for (int step = 1; step <= 3; step++) {
                    tx += dirs[dir][0];
                    ty += dirs[dir][1];
                    if (tx >= 0 && tx < width && ty >= 0 && ty < height) {
                        tcost += map[ty * width + tx];
                        Node tmpNode = new Node(ty * width + tx, dir, step, tcost);
                        if (!visited.contains(tmpNode.key())) {
                            int dist = distances.get(parent.key()) + tcost;
                            int dist2 = !distances.containsKey(tmpNode.key()) ? dist : Math.min(dist, distances.get(tmpNode.key()));
                            distances.put(tmpNode.key(), dist2);
                            if (ty == height - 1 && tx == width - 1) {
                                res = Math.min(dist2, res);
                            }
                            queue.add(tmpNode);
                            visited.add(tmpNode.key());
                        }
                    }
                }
            }
            visited.add(parent.key());
        }
        return res;
    }
    
    class Node {
        int pos, dir, step, cost;
        Node(int pos, int dir, int step, int cost) {
            this.pos = pos;
            this.dir = dir;
            this.step = step;
            this.cost = cost;
        }
        String key() {
            return pos + " " + dir + " " + step;
        }
    }
    
    private int part2(ArrayList<String> list) {
        int width = list.get(0).length();
        int height = list.size();
        int res = Integer.MAX_VALUE;
        int[][] dirs = new int[][] {{1,0},{0,1},{-1,0},{0,-1}};
        int[] map = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y * width + x] = list.get(y).charAt(x) - '0';
            }
        }
        
        HashSet<String> visited = new HashSet<>();
        HashMap<String, Integer> distances = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<Node>((n1, n2) -> {
            int dist1 = distances.containsKey(n1.key()) ? distances.get(n1.key()) : -1;
            int dist2 = distances.containsKey(n2.key()) ? distances.get(n2.key()) : -1;
            return dist1 < dist2 ? -1 : dist1 > dist2 ? 1 : 0;
        });
        Node node = new Node(0, 1, 0, 0);
        queue.add(node);
        distances.put(node.key(), 0);
        boolean flag = true;
        while (!queue.isEmpty()) {
            Node parent = queue.remove();
            int[] moves = parent.dir % 2 == 0 ? new int[] {1, 3} : new int[] {0, 2};
            for (int dir : moves) {
                int tcost = 0;
                int tx = parent.pos % width;
                int ty = parent.pos / width;
                if (flag || parent.step >= 4) {
                    flag = false;
                    for (int step = 1; step <= 10; step++) {
                        tx += dirs[dir][0];
                        ty += dirs[dir][1];
                        if (tx >= 0 && tx < width && ty >= 0 && ty < height) {
                            tcost += map[ty * width + tx];
                            Node tmpNode = new Node(ty * width + tx, dir, step, tcost);
                            if (!visited.contains(tmpNode.key())) {
                                int dist = distances.get(parent.key()) + tcost;
                                int dist2 = !distances.containsKey(tmpNode.key()) ? dist : Math.min(dist, distances.get(tmpNode.key()));
                                distances.put(tmpNode.key(), dist2);
                                if (ty == height - 1 && tx == width - 1) {
                                    res = Math.min(dist2, res);
                                }
                                queue.add(tmpNode);
                                visited.add(tmpNode.key());
                            }
                        }
                    }
                }
            }
            visited.add(parent.key());
        }
        return res;
    }
}
