package adventofcode2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day19 {

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("input/day19/input.txt"));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        Day19 day = new Day19();
        System.out.println("Part 1: " + day.part1(list));
        System.out.println("Part 2: " + day.part2(list));
    }

    HashMap<String, Workflow> workflows = new HashMap<>();
    HashMap<String, String> parents = new HashMap<>();
    HashMap<String, Integer> parentsIndex = new HashMap<>();
    private int part1(ArrayList<String> list) {
        int total = 0;
        boolean flag = false;
        for (String str : list) {
            if (str.length() == 0) {
                flag = true;
                continue;
            }
            if (!flag) {
                String[] parts = str.split("\\{");
                workflows.put(parts[0], new Workflow(parts[0], parts[1].substring(0, parts[1].length() - 1)));
            } else {
                int[] values = new int[256];
                str = str.substring(1, str.length() - 1);
                String[] parts = str.split(",");
                int sum  = 0;
                for (String part : parts) {
                    values[part.charAt(0)] = Integer.valueOf(part.substring(2, part.length()));
                    sum += values[part.charAt(0)];
                }
                
                String key = "in";
                out: while (true) {
                   Workflow wf = workflows.get(key);
                   for (Rule rule : wf.rules) {
                       if ((rule.comparator == 1 && values[rule.category] > rule.value)
                               || (rule.comparator == -1 && values[rule.category] < rule.value)
                               || rule.comparator == 0) {
                           if (rule.workflow.charAt(0) == 'A') {
                               total += sum;
                               break out;
                           } else if (rule.workflow.charAt(0) == 'R') {
                               break out;
                           } else {
                               key = rule.workflow;
                               break;
                           }  
                       }
                   }
                }
            }
        }
        return total;
    }
    
    class Workflow {
        ArrayList<Rule> rules = new ArrayList<>();
        ArrayList<Integer> A = new ArrayList<>();
        String name;
        Workflow(String name, String str) {
            String parts[] = str.split(",");
            this.name = name;
            int index = 0;
            for (String part : parts) {
                Rule rule = new Rule();
                if (part.indexOf(':') >= 0) {
                    String[] parts2 = part.split(":");
                    rule.comparator = part.charAt(1) == '>' ? 1 : -1; 
                    rule.category = part.charAt(0);
                    rule.workflow = parts2[1];
                    rule.value = Integer.valueOf(parts2[0].substring(2, parts2[0].length()));
                } else {
                    rule.workflow = part;
                }
                rules.add(rule);
                if (rule.workflow.charAt(0) == 'A') {
                    A.add(index);
                }
                parents.put(rule.workflow, name);
                parentsIndex.put(rule.workflow, index);
                index++;
            }
        }
    }
    
    class Rule {
        String workflow;
        char category;
        int value;
        int comparator;
    }

    
    private long part2(ArrayList<String> list) {
        long res = 0;
        for (String key : workflows.keySet()) {
            Workflow workflow =  workflows.get(key);
            if (workflow.A.size() > 0) {
                System.out.println("workflow: " + workflow.name);
                for (int index : workflow.A) {
                    
                    long[] min = new long[256];
                    long[] max = new long[256];
                    Arrays.fill(min, -1);
                    Arrays.fill(max, -1);
                    
                    Rule rule = workflow.rules.get(index);
                    
                    if (rule.comparator != 0) {
                        System.out.println(rule.category + (rule.comparator == 1 ? ">" : "<") + rule.value);
                        if (rule.comparator == 1) {
                            max[rule.category] = max[rule.category] == -1 ? rule.value : Math.max(rule.value, max[rule.category]);
                        } else {
                            min[rule.category] = min[rule.category] == -1 ? rule.value - 1 : Math.min(rule.value - 1, min[rule.category]);
                        }
                    }
                    for (int a = index - 1; a >= 0; a--) {
                        rule = workflow.rules.get(a);
                        System.out.println(rule.category + (rule.comparator == 1 ? "<=" : ">=") + rule.value);
                        if (rule.comparator == -1) {
                            max[rule.category] = max[rule.category] == -1 ? rule.value - 1 : Math.max(rule.value - 1, max[rule.category]);
                        } else {
                            min[rule.category] = min[rule.category] == -1 ? rule.value : Math.min(rule.value, min[rule.category]);
                        }
                    }
                    String keyTmp = key;
                    String next = parents.get(keyTmp);
                    while (next != null) {
                        
                        Workflow innerWorkflow =  workflows.get(next); 
                        rule = innerWorkflow.rules.get(parentsIndex.get(keyTmp));
                        if (rule.comparator != 0) {
                            System.out.println(rule.category + (rule.comparator == 1 ? ">" : "<") + rule.value);
                            if (rule.comparator == 1) {
                                max[rule.category] = max[rule.category] == -1 ? rule.value : Math.max(rule.value, max[rule.category]);
                            } else {
                                min[rule.category] = min[rule.category] == -1 ? rule.value - 1 : Math.min(rule.value - 1, min[rule.category]);
                            }
                        }
                        for (int a = parentsIndex.get(keyTmp) - 1; a >= 0; a--) {
                            rule = innerWorkflow.rules.get(a);
                            System.out.println(rule.category + (rule.comparator == 1 ? "<=" : ">=") + rule.value);
                            if (rule.comparator == -1) {
                                max[rule.category] = max[rule.category] == -1 ? rule.value - 1 : Math.max(rule.value - 1, max[rule.category]);
                            } else {
                                min[rule.category] = min[rule.category] == -1 ? rule.value : Math.min(rule.value, min[rule.category]);
                            }
                        }
                        keyTmp = next;
                        next = parents.getOrDefault(innerWorkflow.name, null);
                    }
                    long value = 1;
                    for (char cat : new char[] {'x', 'm', 'a', 's'}) {
                        long minV = min[cat] == -1 ? 4000 : min[cat];
                        long maxV = max[cat] == -1 ? 0 : max[cat];
                        value *= minV - maxV;
                    }
                    res += value;
                    System.out.println(value);
                    System.out.println("");
                    System.out.println("");
                }
            }
        }
        return res;
    }
}
