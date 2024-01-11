package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day24 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day24/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day24 day = new Day24();
        //System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }
    
    private int part1(ArrayList<String> list) {
        int res = 0;
        Position[] positions = new Position[list.size()];
        for (int a = 0; a < list.size(); a++) {
            String[] parts = list.get(a).split(" @ ");
            String[] pos = parts[0].split(", ");
            String[] vel = parts[1].split(", ");
            Position position = new Position(Double.valueOf(pos[0]), Double.valueOf(pos[1]), Double.valueOf(pos[2]),
                    Double.valueOf(vel[0]), Double.valueOf(vel[1]), Double.valueOf(vel[2]));
            positions[a] = position;
        }
        double lowBound = 200000000000000d;
        double highBound = 400000000000000d;
        for (int a = 0; a < positions.length; a++) {
            for (int b = a + 1; b < positions.length; b++) {
                
                Position p1 = positions[a];
                Position p2 = positions[b];
                
                p1.slope = p1.vy / p1.vx;
                p1.slope2 = p1.vy / p1.vz;
                p2.slope = p2.vy / p2.vx;
                
                // (y-a) = s(x-b)
                // y = sx-sb+a
                // y-sx = -sb+a
                // -y+sx = +sb-a
                
                // (y-a) = s(z-b)
                // y = sz-sb+a
                // (y+sb-a)/s = z
                
                double eq1 = p1.slope * p1.px  * -1 + p1.py;
                double eq2 = p2.slope * p2.px + p2.py * -1;
                double x = (eq1 + eq2) / (p1.slope * -1 + p2.slope);
                double y = p1.slope * x - p1.slope * p1.px + p1.py;
                double z = (y + p1.slope2 * p1.pz - p1.py) / p1.slope2;
                
                /*
                 * p + vt = x
                   vt = x-p
                   t = (x-p)/v
                 */
                double t1 = (x - p1.px) / p1.vx;
                double t2 = (y - p1.py) / p1.vy;
                double t3 = (z - p1.pz) / p1.vz;
                double t4 = (x - p2.px) / p2.vx;
                double t5 = (y - p2.py) / p2.vy;
                double t6 = (z - p2.pz) / p2.vz;
                
                //System.out.println(x + ", " + y + ", " + z + " - " + t1 + "," + t2 + "," + t3 + "," + t4);
                
                if (x >= lowBound && x <= highBound &&
                    y >= lowBound && y <= highBound &&
                    t1 >= 0 && t2 >= 0 && t3 >= 0 && t4 >= 0
                ) {
                    res++;
                } 
            }
        }
        return res;
    }
    
    class Position {
        double px, py, pz, vx, vy, vz, slope, slope2;

        public Position(double px, double py, double pz, double vx, double vy, double vz) {
            super();
            this.px = px;
            this.py = py;
            this.pz = pz;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
        }
        
    }
    
    private int part2(ArrayList<String> list) {
        int res = 0;
        double lowBound = 7;
        double highBound = 27;
        Position[] positions = new Position[list.size()];
        for (int a = 0; a < list.size(); a++) {
            String[] parts = list.get(a).split(" @ ");
            String[] pos = parts[0].split(", ");
            String[] vel = parts[1].split(", ");
            Position position = new Position(Double.valueOf(pos[0]), Double.valueOf(pos[1]), Double.valueOf(pos[2]),
                    Double.valueOf(vel[0]), Double.valueOf(vel[1]), Double.valueOf(vel[2]));
            positions[a] = position;
        }
        int low = 1;
        int high = 800;
        for (int vx = low; vx <= high; vx++) {
            System.out.println("vx: " + vx);
            for (int vy = low; vy <= high; vy++) {
                for (int vz = low; vz <= high; vz++) {
                    if (vx == -3 && vy == 1 && vz == 2) {
                        //System.out.println("ok");
                    }
                    
                    here: for (Position pos : positions) {
                        
                        Position p1 = new Position(pos.px + pos.vx + vx * -1, pos.py + pos.vy + vy * -1, pos.pz + pos.vz + vz * -1, vx, vy, vz);
                        double x = 0, y = 0, z = 0, t1 = 0, t2 = 0;
                        for (Position p2 : positions) {
                            
                            p1.slope = p1.vy / p1.vx;
                            p1.slope2 = p1.vy / p1.vz;
                            p2.slope = p2.vy / p2.vx;
                            
                            double eq1 = p1.slope * p1.px  * -1 + p1.py;
                            double eq2 = p2.slope * p2.px + p2.py * -1;
                            x = (eq1 + eq2) / (p1.slope * -1 + p2.slope);
                            y = p1.slope * x - p1.slope * p1.px + p1.py;
                            z = (y + p1.slope2 * p1.pz - p1.py) / p1.slope2;
                            
                            t1 = (x - p1.px) / p1.vx;
                            t2 = (y - p1.py) / p1.vy;
                            double t3 = (z - p1.pz) / p1.vz;
                            double t4 = (x - p2.px) / p2.vx;
                            double t5 = (y - p2.py) / p2.vy;
                            double t6 = (z - p2.pz) / p2.vz;
                            
                            if (Double.isNaN(x) || Double.isNaN(y)  || Double.isNaN(z)) {
                                continue here;
                            }

                            if (/*x < lowBound || x > highBound ||
                                y < lowBound || y > highBound ||
                                z < lowBound || z > highBound ||*/
                                t1 < 0 || t2 < 0 || t3 < 0 || t4 < 0 || t5 < 0|| t6 < 0) {
                                continue here;
                            }
                            //System.out.println(x + ", " + y + ", " + z + " - " + t1 + "," + t2 + "," + t3 + "," + t4 + "," + t5 + "," + t6);
                        }
                        
                        System.out.println(p1.px + ", " + p1.py + ", " + p1.pz + " " + x  + " " + y + " " + z + " " + t1 + " " + t2);
                    }
                }
            }
        }
        
        
        Position p1 = new Position(18, 19, 22, -1, -1, -2);
        Position p2 = new Position(24, 13, 10, -3, 1, 2);
        p1.slope = p1.vy / p1.vx;
        p1.slope2 = p1.vy / p1.vz;
        p2.slope = p2.vy / p2.vx;
        
        double eq1 = p1.slope * p1.px  * -1 + p1.py;
        double eq2 = p2.slope * p2.px + p2.py * -1;
        double x = (eq1 + eq2) / (p1.slope * -1 + p2.slope);
        double y = p1.slope * x - p1.slope * p1.px + p1.py;
        double z = (y + p1.slope2 * p1.pz - p1.py) / p1.slope2;
        
        double t1 = (x - p1.px) / p1.vx;
        double t2 = (y - p1.py) / p1.vy;
        double t3 = (z - p1.pz) / p1.vz;
        double t4 = (x - p2.px) / p2.vx;
        double t5 = (y - p2.py) / p2.vy;
        double t6 = (z - p2.pz) / p2.vz;
        
        System.out.println(x + ", " + y + ", " + z + " - " + t1 + "," + t2 + "," + t3 + "," + t4 + "," + t5 + "," + t6);
        
        
        return res;
    }
}
