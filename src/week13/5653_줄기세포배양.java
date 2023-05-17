import java.io.*;
import java.util.*;

class Solution {

    static int[] dx = { 0, 1, 0, -1 };
    static int[] dy = { 1, 0, -1, 0 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            // 입력
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()); // 세로
            int M = Integer.parseInt(st.nextToken()); // 가로
            int K = Integer.parseInt(st.nextToken()); // 배양 시간
            
            PriorityQueue<Node> pq = new PriorityQueue<>(); // 비활성상태에서 활성상태 변할 세포들
            Set<Node> set = new HashSet<>(); // 위치별로 동일한 세포 저장을 피하기 위한 set

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    int k = Integer.parseInt(st.nextToken());
                    if (k > 0) {
                        Node node = new Node(i, j, k, k);
                        pq.add(node);
                        set.add(node);
                    }
                }
            }

            int res = 0; // 비활성+활성상태 세포 수

            // 큐가 비거나(모든 세포가 퍼짐) 배양 시간이 넘어가면 반복 종료 
            while (!pq.isEmpty()) {
                Node node = pq.poll();
                
                // 해당 세포의 활성 상태 전환 시간이 배양시간을 넘기면 반복 종료
                if (node.time >= K) {
                    res++;
                    // while (!pq.isEmpty()) {
                    //     // 비활성 세포 더하기
                    //     node = pq.poll();
                    //     if (node.time + node.k > K) {
                    //         res++;
                    //     }
                    // }
                    break;
                }

                // 배양 시간에 활성상태일 경우
                if (node.time + node.k > K) {
                    res++;
                }
                
                for (int i = 0; i < 4; i++) {
                    int nx = node.x + dx[i];
                    int ny = node.y + dy[i];
                    int k = node.k;
                    int time = node.k + node.time + 1;

                    Node newNode = new Node(nx, ny, k, time);
                    if (!set.contains(newNode)) {
                        set.add(newNode);
                        pq.add(newNode);
                    }
                }
            }

            // 비활성 세포 더하기
            res += pq.size();
            
            sb.append("#")
                .append(t)
                .append(" ")
                .append(res)
                .append("\n");
        }

        System.out.println(sb);
    }
}

class Node implements Comparable<Node> {
    int x;
    int y;
    int k; // 생명력
    int time; // 해당 시간에 비활성 상태에서 활성상태로 전환
    Node (int x, int y, int k, int time) {
        this.x = x;
        this.y = y;
        this.k = k;
        this.time = time;
    }

    @Override
    public int compareTo(Node o) {
        if (this.time == o.time) return o.k - this.k;
        return this.time - o.time;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Node) {
            Node node = (Node) o;
            if (node.x == this.x && node.y == this.y) return true;
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y, x+y);
    }
}
