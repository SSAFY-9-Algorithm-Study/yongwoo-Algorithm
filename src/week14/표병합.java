import java.util.*;

class Solution {
    String[][] graph = new String[51][51]; // 표
    int[][][] merged = new int[51][51][2]; // 병합된 셀
    List<String> answer = new ArrayList<>();
    
    int[] getParent(int r, int c) {
        if (merged[r][c][0] != 0) {
            int[] parent = getParent(merged[r][c][0], merged[r][c][1]);
            
            merged[r][c][0] = parent[0];
            merged[r][c][1] = parent[1];
            
            return parent;
        }
        
        return new int[] { r, c };
    }
    
    void updateByValue(int r, int c, String value) {
        int[] parent = getParent(r, c);
        r = parent[0];
        c = parent[1];
        
        graph[r][c] = value;
    }
    
    void updateByCell(String value1, String value2) {
        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                if (graph[i][j] != null && graph[i][j].equals(value1)) graph[i][j] = value2;
            }
        }
    }
    
    void merge(int r1, int c1, int r2, int c2) {
        if (r1 == r2 && c1 == c2) return;
        
        int[] p1 = getParent(r1, c1);
        int[] p2 = getParent(r2, c2);
        if (p1[0] == p2[0] && p1[1] == p2[1]) return;
        
        if (graph[p1[0]][p1[1]] == null) graph[p1[0]][p1[1]] = graph[p2[0]][p2[1]];
        
        merged[p2[0]][p2[1]][0] = p1[0];
        merged[p2[0]][p2[1]][1] = p1[1];
    }
    
    void unmerge(int r, int c) {
        int[] parent = getParent(r, c);
        int pr = parent[0];
        int pc = parent[1];
        String val = graph[pr][pc];
        
        List<int[]> unmergeList = new ArrayList<>();
        unmergeList.add(new int[] { r, c });
        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                int[] parent2 = new int[] { i, j };
                if (merged[i][j][0] != 0) parent2 = getParent(i, j);
                
                if (pr == parent2[0] && pc == parent2[1]) {
                    unmergeList.add(new int[] { i, j });
                }
            }
        }
        
        for (int[] unmergeCell: unmergeList) {
            int i = unmergeCell[0];
            int j = unmergeCell[1];
            
            graph[i][j] = null;
            merged[i][j][0] = 0;
            merged[i][j][1] = 0;
        }
        
        graph[r][c] = val;
    }
    
    void print(int r, int c) {
        int[] parent = getParent(r, c);
        r = parent[0];
        c = parent[1];
        
        if (graph[r][c] == null) answer.add("EMPTY");
        else answer.add(graph[r][c]);
    }
    
    public String[] solution(String[] commands) {
        for (String command: commands) {
            String[] input = command.split(" ");
            
            if (input[0].equals("UPDATE")) {
                if (input.length == 4) 
                    updateByValue(Integer.parseInt(input[1]), Integer.parseInt(input[2]), input[3]);
                else
                    updateByCell(input[1], input[2]);
            }
            else if (input[0].equals("MERGE")) {
                merge(Integer.parseInt(input[1]), Integer.parseInt(input[2]), 
                     Integer.parseInt(input[3]), Integer.parseInt(input[4]));
            }
            else if (input[0].equals("UNMERGE")) {
                unmerge(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
            }
            else if (input[0].equals("PRINT")) {
                print(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
            }
        }
        
        return answer.toArray(new String[answer.size()]);
    }
}
