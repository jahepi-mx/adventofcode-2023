package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day15 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day15/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day15 day = new Day15();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    private int part1(ArrayList<String> list) {
        String[] ins = list.get(0).split(",");
        int total = 0;
        for (String str : ins) {
            total += this.calculateHash(str);
        }
        return total;
    }
    
    private int calculateHash(String str) {
        int res = 0;
        for (int ch : str.toCharArray()) {
            res += ch;
            res *= 17;
            res %= 256;
        }
        return res;
    }
    
    private int part2(ArrayList<String> list) {
        Box[] boxes = new Box[256];
        for (int a = 0; a < boxes.length; a++) {
            boxes[a] = new Box();
        }
        
        String[] ins = list.get(0).split(",");
        for (String str : ins) {
            
            if (str.indexOf("=") >= 0) {
                String[] parts = str.split("=");
                String label = parts[0];
                int len = Integer.parseInt(parts[1]);
                int hash = this.calculateHash(label);
                boxes[hash].add(label, len);
            } else {
                String label = str.substring(0, str.length() - 1);
                int hash = this.calculateHash(label);
                boxes[hash].remove(label);
            }
            
        }
        int res = 0;
        for (int a = 0; a < boxes.length; a++) {
            for (int b = 0; b < boxes[a].list.size(); b++) {
                res += (a + 1) * (b + 1) * boxes[a].list.get(b).len;
            }
        }
        return res;
    }
    
    class Item {
        String label;
        int len;
        Item(String label, int len) {
            this.label = label;
            this.len = len;
        }
    }
    class Box {
        String label;
        HashSet<String> labels = new HashSet<String>();
        ArrayList<Item> list = new ArrayList<>();
        
        void add(String label, int len) {
            if (!labels.contains(label)) {
                list.add(new Item(label, len));
                labels.add(label);
            } else {
                for (Item item : list) {
                    if (item.label.equals(label)) {
                        item.len = len;
                        break;
                    }
                }
            }
        }
        
        void remove(String label) {
            if (labels.contains(label)) {
                int index = 0;
                for (Item item : list) {
                    if (item.label.equals(label)) {
                        break;
                    }
                    index++;
                }
                list.remove(index);
                labels.remove(label);
            }
        }
    }
}
