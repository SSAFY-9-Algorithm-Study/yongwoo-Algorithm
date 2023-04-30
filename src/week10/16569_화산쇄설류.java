import java.io.*;
import java.util.*;

class Main {

    static int M; // 섬의 행
    static int N; // 열
    static int V; // 화산 개수
    static int X; // 시작 위치 행
    static int Y; // 열

    static int[][] height; // 높이
    static Node[] volcano; // 화산 정보
    static boolean[][] visited;
    
    static int maxHeight; // 도달 가능한 최고 높이
    static int maxTime; // 최고 높이까지의 시간

    static int[] dx = { 1, 0, -1, 0 };
    static int[] dy = { 0, 1, 0, -1 };
    
    static void bfs(int x, int y) {
        Deque<Node> queue = new ArrayDeque<>();
        queue.add(new Node(x, y, 0));
        
        while (!queue.isEmpty()) {
            Node node = queue.poll();

            for (int j = 0; j < 4; j++) {
                int nx = node.x + dx[j];
                int ny = node.y + dy[j];
                int ntime = node.time + 1;

                if (isWall(nx, ny) || visited[nx][ny]) continue;
                
                // 화산별로 확인하여 위치에 화산이 닿을 수 있는지
                int i = 0;
                for (; i < volcano.length; i++) {
                    Node n = volcano[i];
                    if (n.x == nx && n.y == ny) break; // 화산 위 지나기

                    int distFromVol = Math.abs(n.x - nx) + Math.abs(n.y - ny);
                    if (ntime >= distFromVol + n.time) break;
                }

                if (i == volcano.length) {
                    if (maxHeight < height[nx][ny]) {
                        maxHeight = height[nx][ny];
                        maxTime = ntime;
                    }

                    queue.add(new Node(nx, ny, ntime));
                }
                
                visited[nx][ny] = true;
            }
        }
    }
    
    static boolean isWall(int x, int y) {
        if (x < 0 || x >= M || y < 0 || y >= N) return true;
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        X = Integer.parseInt(st.nextToken()) - 1;
        Y = Integer.parseInt(st.nextToken()) - 1;

        height = new int[M][N];
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                height[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        volcano = new Node[V];
        for (int i = 0; i < V; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int t = Integer.parseInt(st.nextToken());

            volcano[i] = new Node(x, y, t);
        }

        maxHeight = height[X][Y];
        maxTime = 0;
        visited = new boolean[M][N];

        bfs(X, Y);
         
        sb.append(maxHeight).append(" ").append(maxTime);
        System.out.println(sb);
    }
}

class Node {
    int x;
    int y;
    int time;
    Node (int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
