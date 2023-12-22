package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day22 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day22/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day22 day = new Day22();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2());
    }
    
    ArrayList<Integer> newBricks = new ArrayList<>();
    ArrayList<Brick> bricks = new ArrayList<>();
    
    private int part1(ArrayList<String> list) {
        for (String str : list) {
            String[] parts = str.split("~");
            String[] pos1 = parts[0].split(",");
            String[] pos2 = parts[1].split(",");
            
            Brick brick = new Brick(
                Double.valueOf(pos1[0]), 
                Double.valueOf(pos1[1]), 
                Double.valueOf(pos1[2]),
                Double.valueOf(pos2[0]), 
                Double.valueOf(pos2[1]), 
                Double.valueOf(pos2[2])
            );
            
            bricks.add(brick);
        }
        
        bricks.sort((a, b) -> { return a.z2 > b.z2 ? -1 : a.z2 < b.z2 ? 1 : 0; });
    
        boolean running = true;
        while (running) {
            running = false;
            out: for (int a = 0; a < bricks.size(); a++) {
                Brick brick = bricks.get(a);
                Brick downBrick = brick.moveDown();
                if (downBrick.z >= 1) {
                    for (int b = a + 1; b < bricks.size(); b++) {
                        Brick childBrick = bricks.get(b);
                        if (downBrick.collide(childBrick)) {
                            continue out;
                        }
                    }
                    brick.down();
                    running = true;
                }
            }
        }
        
        int res = 0;
        for (int c = 0; c < bricks.size(); c++) {
            int count = 1;
            out: for (int a = 0; a < bricks.size(); a++) {
                if (a != c) {
                    Brick brick = bricks.get(a);
                    Brick downBrick = brick.moveDown();
                    if (downBrick.z >= 1) {
                        for (int b = a + 1; b < bricks.size(); b++) {
                            if (b != c) {
                                Brick childBrick = bricks.get(b);
                                if (downBrick.collide(childBrick)) {
                                    continue out;
                                }
                            }
                        }
                        count = 0;
                    }
                }
            }
            if (count == 0) {
                newBricks.add(c);
            }
            res += count;
        }
        return res;
    }
    
    private int part2() {
        int res = 0;
        for (int c : newBricks) {
            ArrayList<Brick> bricksCopy = new ArrayList<>();
            for (Brick br : bricks) {
                bricksCopy.add(br.copy());
            }
            HashSet<Integer> set = new HashSet<>();
            boolean running = true;
            while (running) {
                running = false;
                out: for (int a = 0; a < bricksCopy.size(); a++) {
                    if (a != c) {
                        Brick brick = bricksCopy.get(a);
                        Brick downBrick = brick.moveDown();
                        if (downBrick.z >= 1) {
                            for (int b = a + 1; b < bricksCopy.size(); b++) {
                                if (b != c) {
                                    Brick childBrick = bricksCopy.get(b);
                                    if (downBrick.collide(childBrick)) {
                                        continue out;
                                    }
                                }
                            }
                            set.add(a);
                            brick.down();
                            running = true;
                        }
                    }
                }
            }
            res += set.size();
        }
        return res;
    }
    
    class Brick {
        
        double x, y, z;
        double x2, y2, z2;
        double w, h, l;
        double midX, midY, midZ;
        
        Brick(double x, double y, double z, double x2, double y2, double z2) {
            setState(x, y, z, x2, y2, z2);
        }
        
        void setState(double x, double y, double z, double x2, double y2, double z2) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
            this.w = this.x2 - this.x + 1;
            this.h = this.y2 - this.y + 1;
            this.l = this.z2 - this.z + 1;
            this.midX = this.x + (this.x2 - this.x + 1) * 0.5;
            this.midY = this.y + (this.y2 - this.y + 1) * 0.5;
            this.midZ = this.z + (this.z2 - this.z + 1) * 0.5;
        }
        
        boolean collide(Brick brick) {
            double diffX = Math.abs(this.midX - brick.midX);
            double diffY = Math.abs(this.midY - brick.midY);
            double diffZ = Math.abs(this.midZ - brick.midZ);
            double diffW = (this.w + brick.w) * 0.5;
            double diffH = (this.h + brick.h) * 0.5;
            double diffL = (this.l + brick.l) * 0.5;
            return diffX < diffW && diffY < diffH && diffZ < diffL;
        }
        
        void down() {
            setState(x, y, z - 1, x2, y2, z2 - 1);
        }
        
        Brick moveDown() {
            return new Brick(x, y, z - 1, x2, y2, z2 - 1);
        }
        
        Brick copy() {
            return new Brick(x, y, z, x2, y2, z2);
        }
        
    }
}
